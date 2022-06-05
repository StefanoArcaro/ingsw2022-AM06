package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.clientToserver.PingMessage;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.View;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient extends Client {

    private final View view;
    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final ExecutorService readQueue;
    private final ScheduledExecutorService pinger;

    /**
     * Default constructor.
     * @param socket the server socket.
     */
    public SocketClient(View view, Socket socket) {
        this.view = view;
        this.socket = socket;

        try {
            socket.setSoTimeout(Constants.SOCKET_TIMEOUT);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("A problem with I/O streams instantiation occurred.");
            disconnect();
        }

        this.readQueue = Executors.newSingleThreadExecutor();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * @return the View.
     */
    public View getView() {
        return view;
    }

    /**
     * Asynchronously read messages from the server.
     */
    @Override
    public void readMessage() {
        readQueue.execute(() -> {
            while(!readQueue.isShutdown()) {
                try {
                    String message = reader.readLine();

                    // If message is null, it means the server's connection has fallen
                    if(message != null) {
                        handleAnswer(message);
                    } else {
                        System.err.println("\nThe connection to the server was severed, closing the application...");
                        disconnect();
                    }
                } catch (Exception e) {
                    System.err.println("\nA problem with the connection occurred, closing the application...");
                    disconnect();
                }
            }
        });
    }

    /**
     * Sends a message to the server by serializing it using a JSON format.
     * @param message the message to send.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            Gson gson = new Gson();
            String msg = gson.toJson(message);

            writer.write(msg);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Deserializes the message received from the server and handles it based
     * on the type of said message.
     * @param message the (yet to be deserialized) message.
     */
    public void handleAnswer(String message) {
        Gson gson = new Gson();

        try {
            Answer answer = gson.fromJson(message, Answer.class);

            switch (answer.getMessageType()) {
                case LOGIN_REPLY_MESSAGE -> {
                    LoginReplyMessage msg = gson.fromJson(message, LoginReplyMessage.class);
                    view.loginReplyHandler(msg);
                }
                case GAME_STARTED_MESSAGE -> view.gameStartingHandler();
                case GAME_READY_MESSAGE -> view.gameReadyHandler();
                case WIZARDS_AVAILABLE_MESSAGE -> {
                    WizardsAvailableMessage msg = gson.fromJson(message, WizardsAvailableMessage.class);
                    view.wizardsHandler(msg);
                }
                case FX_WIZARD_CHOICE_MESSAGE -> {
                    FXWizardChoiceMessage msg = gson.fromJson(message, FXWizardChoiceMessage.class);
                    view.fxWizardsHandler(msg);
                }
                case ASSISTANTS_MESSAGE -> {
                    AssistantsMessage msg = gson.fromJson(message, AssistantsMessage.class);
                    view.assistantsHandler(msg);
                }
                case ACTIVE_PLAYERS_MESSAGE -> {
                    ActivePlayersMessage msg = gson.fromJson(message, ActivePlayersMessage.class);
                    view.activePlayersHandler(msg);
                }
                case BOARD_MESSAGE -> {
                    BoardMessage msg = gson.fromJson(message, BoardMessage.class);
                    view.boardHandler(msg);
                }
                case ISLAND_GROUPS_MESSAGE -> {
                    IslandGroupsMessage msg = gson.fromJson(message, IslandGroupsMessage.class);
                    view.islandGroupsHandler(msg);
                }
                case ISLAND_MESSAGE -> {
                    IslandMessage msg = gson.fromJson(message, IslandMessage.class);
                    view.islandHandler(msg);
                }
                case CLOUDS_AVAILABLE_MESSAGE -> {
                    CloudsAvailableMessage msg = gson.fromJson(message, CloudsAvailableMessage.class);
                    view.cloudsAvailableHandler(msg);
                }
                case CLOUD_CHOSEN_MESSAGE -> {
                    CloudChosenMessage msg = gson.fromJson(message, CloudChosenMessage.class);
                    view.cloudChosenHandler(msg);
                }
                case FX_CLOUD_MESSAGE -> {
                    FXCloudMessage msg = gson.fromJson(message, FXCloudMessage.class);
                    view.fxCloudsHandler(msg);
                }
                case COIN_MESSAGE -> {
                    CoinMessage msg = gson.fromJson(message, CoinMessage.class);
                    view.coinsHandler(msg);
                }
                case CURRENT_PLAYER_MESSAGE -> {
                    CurrentPlayerMessage msg = gson.fromJson(message, CurrentPlayerMessage.class);
                    view.currentPlayerHandler(msg);
                }
                case CURRENT_PHASE_MESSAGE -> {
                    CurrentPhaseMessage msg = gson.fromJson(message, CurrentPhaseMessage.class);
                    view.currentPhaseHandler(msg);
                }
                case CHARACTER_DRAWN_MESSAGE -> {
                    CharacterDrawnMessage msg = gson.fromJson(message, CharacterDrawnMessage.class);
                    view.charactersDrawnHandler(msg);
                }
                case CHARACTER_INFO_MESSAGE -> {
                    CharacterInfoMessage msg = gson.fromJson(message, CharacterInfoMessage.class);
                    view.characterInfoHandler(msg);
                }
                case CHARACTER_PLAYED_MESSAGE -> {
                    CharacterPlayedMessage msg = gson.fromJson(message, CharacterPlayedMessage.class);
                    view.characterPlayedHandler(msg);
                }
                case WINNER_MESSAGE -> {
                    WinnerMessage msg = gson.fromJson(message, WinnerMessage.class);
                    view.winnerHandler(msg);
                }
                case LOSER_MESSAGE -> {
                    LoserMessage msg = gson.fromJson(message, LoserMessage.class);
                    view.loserHandler(msg);
                }
                case GAME_ENDED_MESSAGE -> view.gameEndedHandler();

                case GENERIC_MESSAGE -> {
                    GenericMessage msg = gson.fromJson(message, GenericMessage.class);
                    view.genericMessageHandler(msg);
                }
                case ERROR_MESSAGE -> {
                    ErrorMessage msg = gson.fromJson(message, ErrorMessage.class);
                    view.errorMessageHandler(msg);
                }
                case DISCONNECTION_REPLY_MESSAGE -> {
                    System.out.print("\nClosing the application");
                    Constants.countdown(400);
                    disconnect();
                }
                case SERVER_QUIT_MESSAGE -> {
                    System.err.println("\nThe server was quit. Closing the application...");
                    disconnect();
                }
                case PONG_MESSAGE -> {
                }

                default -> System.out.println(answer.getMessageType()); //should never reach this condition
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("There's been a connection error, application will now close");
            Constants.countdown(400);
            disconnect();
        }
    }

    /**
     * Disconnects from the server.
     */
    @Override
    public void disconnect() {
        closeEverything();
    }

    /**
     * Shuts down the threads used for asynchronous actions, then
     * closes the socket's input and output streams, then the socket itself.
     * Finally, closes the application.
     */
    private void closeEverything() {
        try {
            enablePinger(false);
            readQueue.shutdownNow();

            if(reader != null) {
                reader.close();
            }
            if(writer != null) {
                writer.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        System.exit(0);
    }

    /**
     * Activates the periodic sending of a ping message to keep the connection alive.
     * @param enabled used to either enable or disable the ping functionality.
     */
    @Override
    public void enablePinger(boolean enabled) {
        if(enabled) {
            pinger.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), Constants.PING_INITIAL_DELAY, Constants.PING_PERIOD, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }
}
