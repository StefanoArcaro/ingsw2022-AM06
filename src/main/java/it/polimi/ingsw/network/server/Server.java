package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.network.message.clientToserver.LoginRequestMessage;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main server class
 */
public class Server {

    private final SocketServer socketServer;
    private final ArrayList<GameManager> gameManagers;
    private final Map<Integer, ClientHandler> idToConnection;
    private final Map<Integer, String> idToNickname;
    private final Map<Integer, GameManager> idToGameManager;
    private int nextClientID;

    public Server(int port) {
        this.gameManagers = new ArrayList<>();
        this.socketServer = new SocketServer(port, this);
        this.idToConnection = new HashMap<>();
        this.idToNickname = new HashMap<>();
        this.idToGameManager = new HashMap<>();
        this.nextClientID = -1;

        new Thread(this::quit).start(); // Asynchronously listening to server stdin for quitting
    }


    public void addClient(ClientHandler clientHandler) {
        int clientID = getNextClientID();
        idToConnection.put(clientID, clientHandler);
    }

    public int getNextClientID() {
        nextClientID += 1;
        return nextClientID;
    }

    private void quit() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            if(scanner.nextLine().equalsIgnoreCase("QUIT")) {
                broadcastMessage(new ServerQuitMessage());
                System.out.println("Server quitting!");
                System.exit(0);
            }
        }
    }

    // Find clientID, find GameHandler from id, gamehandler calls controller to switch on messageType
    public void onMessageReceived(ClientHandler clientHandler, String msg) {
        Gson gson = new Gson();
        Message message = gson.fromJson(msg, Message.class);

        // If msg is null, it means the client's connection has fallen
        if(msg == null) {
            disconnectionHandler(clientHandler);
            return;
        }

        switch(message.getMessageType()) {
            case PING_MESSAGE -> pingHandler(clientHandler);
            case DISCONNECTION_REQUEST_MESSAGE -> quitHandler(clientHandler);
            case LOGIN_REQUEST_MESSAGE -> loginHandler(clientHandler, msg);

            default -> {
                message.setClientID(getClientIDFromClientHandler(clientHandler));

                GameManager gameManager = idToGameManager.get(getClientIDFromClientHandler(clientHandler));
                if(gameManager != null) {
                    Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(msg, message);
                    gameManager.onMessageReceived(pair); // TODO NICEEEEEE
                } else {
                    // TODO SBAGLIATO MA DIGLIELO (da cambiare)
                    clientHandler.sendMessage(new ErrorMessage("Send a login message AOOOOO"));
                }
            }
        }
    }

    private void pingHandler(ClientHandler clientHandler) {
        clientHandler.sendMessage(new PongMessage());
    }

    private void quitHandler(ClientHandler clientHandler) {
        // Disconnect clientHandler
        clientHandler.sendMessage(new DisconnectionReplyMessage("You"));

        disconnectionHandler(clientHandler);
    }

    private void disconnectionHandler(ClientHandler clientHandler) {
        int clientID = getClientIDFromClientHandler(clientHandler);

        // Update other players in the same game as disconnected clientHandler
        String nicknameDisconnected = idToNickname.get(clientID);
        GameManager gameManager = idToGameManager.get(clientID);

        System.out.print("ClientID: " + clientID + " - Nickname: " + nicknameDisconnected);

        // TODO maybe before if
        // Remove clientHandler from maps
        removeClient(clientHandler);

        if(gameManager != null) {
            System.out.println(nicknameDisconnected + " has disconnected.");
            // TODO remove player from gameManager
            gameManager.sendAllExcept(new GenericMessage(nicknameDisconnected + " has disconnected."), nicknameDisconnected);
        } else {
            System.out.println("Client disconnected!");
        }


        // TODO: countdown to disconnect other players

        // TODO remove other players and end game

        clientHandler.disconnect();
    }

    private void loginHandler(ClientHandler clientHandler, String message) {
        Gson gson = new Gson();

        LoginRequestMessage loginMessage = gson.fromJson(message, LoginRequestMessage.class);

        // Get login nickname and preferences
        String nickname = loginMessage.getNickname();
        NumberOfPlayers numberOfPlayers = loginMessage.getNumberOfPlayers();
        GameMode gameMode = loginMessage.getGameMode();

        // Check if the nickname is unique
        if(checkUniqueNickname(nickname)) {
            // Check if already logged in
            boolean alreadyLogged = idToNickname.containsKey(getClientIDFromClientHandler(clientHandler));

            if(!alreadyLogged) {
                int clientID = getClientIDFromClientHandler(clientHandler);

                // TODO change: NO toUpperCase
                idToNickname.put(clientID, nickname.toUpperCase());

                GameManager gameManager = checkGamePreferences(clientID, numberOfPlayers, gameMode);
                gameManager.addClient(clientID, clientHandler, nickname);
                idToGameManager.put(clientID, gameManager);
                gameManager.getGame().getCurrentPhase().setPlayerNickname(nickname);

                try {
                    gameManager.getGame().getCurrentPhase().play();

                    clientHandler.sendMessage(new LoginReplyMessage("You logged in!"));

                    // TODO test with 3 players
                    gameManager.sendAllExcept(new GenericMessage(nickname + " joined the game!"), nickname);

                    //TODO listeners
                    gameManager.sendAll(new GenericMessage("Current phase is: "+ gameManager.getGame().getCurrentPhase().getPhaseName()));

                    System.out.println(nickname + " logged in!");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                clientHandler.sendMessage(new ErrorMessage("You are already logged in!"));
            }
        } else {
            clientHandler.sendMessage(new ErrorMessage("This nickname has already been taken, please select another one."));
        }
    }

    private boolean checkUniqueNickname(String nickname) {
        return !idToNickname.containsValue(nickname.toUpperCase());
    }

    private GameManager checkGamePreferences(int clientID, NumberOfPlayers numberOfPlayers, GameMode gameMode) {
        // TODO eventually remove list and use maps
        for(GameManager gameManager : gameManagers) {
            Game game = gameManager.getGame();

            if(numberOfPlayers == game.getNumberOfPlayers() && gameMode.equals(game.getGameMode())) {
                if(game.getGameState().equals(GameState.LOBBY_PHASE)) {
                    return gameManager;
                }
            }
        }

        // Create new GameManager
        GameManager gameManager = new GameManager();

        // Set preferences
        gameManager.setGamePreferences(numberOfPlayers, gameMode);

        // Add to gameManager list
        gameManagers.add(gameManager);
        idToGameManager.put(clientID, gameManager);
        return gameManager;
    }

    private void removeClient(ClientHandler clientHandler) {
        int idToRemove = getClientIDFromClientHandler(clientHandler);

        idToConnection.remove(idToRemove);
        idToNickname.remove(idToRemove);
        idToGameManager.remove(idToRemove);
    }

    private int getClientIDFromClientHandler(ClientHandler clientHandler) {
        for(int key : idToConnection.keySet()) {
            if(idToConnection.get(key).equals(clientHandler)) {
                return key;
            }
        }
        return -1;
    }

    public void broadcastMessage(Answer answer) {
        for(ClientHandler clientHandler : idToConnection.values()) {
            clientHandler.sendMessage(answer);
        }
    }

    // TODO
    public static void main(String[] args) {
        System.out.println("Eriantys Server | Welcome!");

        // Port choice
        Scanner scanner = new Scanner(System.in);
        System.out.println("> Insert the port which the server will be listening on.");
        System.out.print("> ");
        int port = 0;
        try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }

        if(port < 1024) {
            System.err.println("Error: accepted ports start from 1024! Please insert a new value.");
            main(null);
        } else {
            System.out.println("Instantiating server class...");
            Server server = new Server(port);

            System.out.println("Starting socket server...");
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.submit(server.socketServer);
        }
    }
}