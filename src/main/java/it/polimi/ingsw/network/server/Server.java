package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.network.message.clientToserver.LoginRequestMessage;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;

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

    /**
     * Default constructor.
     * @param port the port that the server will be listening on.
     */
    public Server(int port) {
        this.gameManagers = new ArrayList<>();
        this.socketServer = new SocketServer(port, this);
        this.idToConnection = new HashMap<>();
        this.idToNickname = new HashMap<>();
        this.idToGameManager = new HashMap<>();
        this.nextClientID = -1;

        new Thread(this::quit).start(); // Asynchronously listen to server's stdin stream for quitting
    }

    /**
     * Increases the clientID variable in order to assign a unique ID to
     * every connected client.
     * @return the next client ID.
     */
    public int getNextClientID() {
        nextClientID += 1;
        return nextClientID;
    }

    /**
     * Assigns a unique ID to the client that just connected, then puts
     * the ID and the relative client handler in a map that keeps track
     * of every currently connected client.
     * Also welcomes the client by sending them a message containing the
     * actions they are allowed to take after connecting (login / quit).
     * @param clientHandler handler of the newly connected client.
     */
    public void addClient(ClientHandler clientHandler) {
        int clientID = getNextClientID();
        idToConnection.put(clientID, clientHandler);

        clientHandler.sendMessage(new GenericMessage(Constants.getPhaseInstructions(GameState.LOBBY_PHASE, GameMode.EASY)));
    }

    /**
     * Removes the client whose connection dropped from the maps.
     * @param clientHandler handler of said client.
     */
    public void onConnectionDropped(ClientHandler clientHandler) {
        if(idToConnection.containsValue(clientHandler)) {
            disconnectionHandler(clientHandler);
        }
    }

    /**
     * Handles the message received by a client.
     * @param clientHandler handler of the client that sent a message.
     * @param msg the message sent by the client.
     */
    public void onMessageReceived(ClientHandler clientHandler, String msg) {
        Gson gson = new Gson();
        Message message = null;

        try {
            message = gson.fromJson(msg, Message.class);
        } catch (Exception e) {
            GameManager gameManager = idToGameManager.get(getClientIDFromClientHandler(clientHandler));
            if(gameManager != null) {
                gameManager.sendAll(new ErrorMessage("There's been a connection error."));
                closeGame(gameManager);
            } else {
                clientHandler.sendMessage(new ErrorMessage("There's been a connection error."));
                removeClient(clientHandler);
                clientHandler.disconnect();
            }
        }

        // If msg is null, it means the client's connection has fallen
        if(msg == null) {
            if(idToConnection.containsValue(clientHandler)) {
                disconnectionHandler(clientHandler);
            }
            return;
        }

        if(message != null) {
            switch (message.getMessageType()) {
                case PING_MESSAGE -> pingHandler(clientHandler);
                case DISCONNECTION_REQUEST_MESSAGE -> quitHandler(clientHandler);
                case LOGIN_REQUEST_MESSAGE -> loginHandler(clientHandler, msg);
                default -> {
                    message.setClientID(getClientIDFromClientHandler(clientHandler));
                    GameManager gameManager = idToGameManager.get(getClientIDFromClientHandler(clientHandler));
                    if (gameManager != null) {
                        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(msg, message);
                        gameManager.onMessageReceived(pair);
                    } else {
                        clientHandler.sendMessage(new ErrorMessage("To begin playing, you need to login."));
                    }
                }
            }
        }
    }

    /**
     * Closes the game handled by the specified game manager.
     * @param gameManager that handles the game to close.
     */
    private void closeGame(GameManager gameManager) {
        for(ClientHandler client : gameManager.getClients().values()) {
            String nickname = idToNickname.get(getClientIDFromClientHandler(client));

            System.out.println(nickname + " has disconnected.");
            removeClient(client);
            client.disconnect();
        }

        gameManager.emptyClients();

        gameManagers.remove(gameManager);
    }

    /**
     * Handles the reception of a Ping message by sending a
     * Pong message in response.
     * @param clientHandler handler of the client who sent the ping message.
     */
    private void pingHandler(ClientHandler clientHandler) {
        clientHandler.sendMessage(new PongMessage());
    }

    /**
     * Handles the reception of a message notifying the client quitting the application.
     * @param clientHandler handler of the client who quit.
     */
    private void quitHandler(ClientHandler clientHandler) {
        clientHandler.sendMessage(new DisconnectionReplyMessage());

        disconnectionHandler(clientHandler);
    }

    /**
     * Handles the disconnection of a client, then notifies every other player in the
     * game the client was in (if the game was already instantiated) and ends the game.
     * @param clientHandler handler of the client who is to be disconnected.
     */
    private void disconnectionHandler(ClientHandler clientHandler) {
        int clientID = getClientIDFromClientHandler(clientHandler);

        String nicknameDisconnected = idToNickname.get(clientID);
        GameManager gameManager = idToGameManager.get(clientID);

        // Remove clientHandler from server maps and game manager
        removeClient(clientHandler);

        // Update other players in the same game as disconnected clientHandler
        if(gameManager != null) {
            System.out.println(nicknameDisconnected + " has disconnected.");
            gameManager.sendAllExcept(new GenericMessage("\n" + nicknameDisconnected + " has disconnected, the game will end."), nicknameDisconnected);

            gameManager.removeClient(clientID);

            gameManager.sendAllExcept(new DisconnectionReplyMessage(), nicknameDisconnected);

            closeGame(gameManager);
        } else {
            System.out.println("Client disconnected!");
        }

        clientHandler.disconnect();
    }

    /**
     * Handles the login of a client.
     * If a game with the client's preferences (number of players and mode) that has not
     * yet started exists, the client is automatically added to said game.
     * Otherwise, a new game is created and the client is added to that game as
     * the first player.
     * @param clientHandler handler of the client who sent the login request message.
     * @param message sent by the client.
     */
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

                idToNickname.put(clientID, nickname);

                GameManager gameManager = checkGamePreferences(clientID, numberOfPlayers, gameMode);
                Game game = gameManager.getGame();
                gameManager.addClient(clientID, clientHandler, nickname);
                idToGameManager.put(clientID, gameManager);
                game.getCurrentPhase().setPlayerNickname(nickname);

                try {
                    clientHandler.sendMessage(new LoginReplyMessage("You logged in!", nickname, numberOfPlayers, gameMode));
                    gameManager.sendAllExcept(new GenericMessage(nickname + " joined the game!"), nickname);
                    game.getCurrentPhase().play();

                    //TODO check if ok
                    if(game.getPlayers().size() == game.getNumberOfPlayers().getNum()){
                        gameManager.sendAll(new GameStartedMessage());
                    }

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

    /**
     * Handles the end of the game by disconnecting every player in that game.
     * @param gameManager the manager of the game that has ended.
     */
    public void endGameHandler(GameManager gameManager) {
        System.out.println("Game " + gameManager.getGame() + " has ended, disconnecting all players...");

        for(ClientHandler client : gameManager.getClients().values()) {
            String nickname = idToNickname.get(getClientIDFromClientHandler(client));

            System.out.println(nickname + " has disconnected.");

            client.sendMessage(new GameEndedMessage());

            removeClient(client);
        }

        gameManager.emptyClients();

        gameManagers.remove(gameManager);
    }

    /**
     * Checks if the specified nickname is unique in the server, meaning that there
     * is no other nickname like it (the check is case-insensitive).
     * @param nickname the nickname to check.
     * @return whether the nickname is unique.
     */
    private boolean checkUniqueNickname(String nickname) {
        for(String name : idToNickname.values()) {
            if(name.equalsIgnoreCase(nickname)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a game manager with the specified preferences that has not yet begun exists.
     * If that is the case, it returns said game manager.
     * Otherwise, a new game manager with those preferences is created and returned.
     * @param clientID ID of the client to add to the game manager's list of players.
     * @param numberOfPlayers the preferred number of players.
     * @param gameMode the preferred game mode.
     * @return either an existing or a new game manager.
     */
    private GameManager checkGamePreferences(int clientID, NumberOfPlayers numberOfPlayers, GameMode gameMode) {
        for(GameManager gameManager : gameManagers) {
            Game game = gameManager.getGame();

            if(numberOfPlayers == game.getNumberOfPlayers() && gameMode.equals(game.getGameMode())) {
                if(game.getGameState().equals(GameState.LOBBY_PHASE)) {
                    return gameManager;
                }
            }
        }

        // Create new GameManager
        GameManager gameManager = new GameManager(this);

        // Set preferences
        gameManager.setGamePreferences(numberOfPlayers, gameMode);

        // Add to gameManager list
        gameManagers.add(gameManager);
        idToGameManager.put(clientID, gameManager);
        return gameManager;
    }

    /**
     * Removes a client from the various server maps.
     * If the client is already logged into a game, they are also
     * removed from that game.
     * @param clientHandler handler of the client to remove.
     */
    private void removeClient(ClientHandler clientHandler) {
        int idToRemove = getClientIDFromClientHandler(clientHandler);

        idToConnection.remove(idToRemove);
        idToNickname.remove(idToRemove);
        idToGameManager.remove(idToRemove);
    }

    /**
     * Gets the ID corresponding to the specified client handler.
     * @param clientHandler handler of the client whose ID is returned.
     * @return the client's ID if the client exists, -1 otherwise.
     */
    private int getClientIDFromClientHandler(ClientHandler clientHandler) {
        for(int key : idToConnection.keySet()) {
            if(idToConnection.get(key).equals(clientHandler)) {
                return key;
            }
        }
        return -1;
    }

    /**
     * Broadcasts the specified message to every client currently connected to the server.
     * @param answer the message to broadcast.
     */
    public void broadcastMessage(Answer answer) {
        for(ClientHandler clientHandler : idToConnection.values()) {
            clientHandler.sendMessage(answer);
        }
    }

    /**
     * Listens for a keyboard input.
     * If such input is "quit" then the server's execution is stopped and every client
     * that is currently connected is notified.
     */
    private void quit() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            if(scanner.nextLine().equalsIgnoreCase(Constants.QUIT_FORMAT)) {
                broadcastMessage(new ServerQuitMessage());
                System.out.println("Server quitting!");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Eriantys Server | Welcome!");

        // Port choice
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the port which the server will be listening on.");
        System.out.print(Constants.PROMPT);
        int port;
        try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            port = Constants.DEFAULT_PORT;
            System.err.println("Numeric format requested, the server will be listening on the default port: " + port + ".");
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