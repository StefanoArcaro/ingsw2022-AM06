package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class for the Command Line Interface client.
 * This is executed when the client chooses the CLI option at the beginning of the game.
 */
public class CLI {

    private SocketClient socketClient;
    private final ExecutorService keyboardQueue;
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);
    private final ModelView modelView;

    /**
     * Default constructor.
     */
    public CLI() {
        this.keyboardQueue = Executors.newSingleThreadExecutor();
        this.modelView = new ModelView();
    }

    /**
     * @return the socket attribute of this client.
     */
    public SocketClient getSocketClient() {
        return this.socketClient;
    }

    /**
     * Sets the socket attribute of this client to the specified one.
     * @param socketClient socket to set.
     */
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    /**
     * Adds a listener to the CLI.
     * @param propertyName name of the observed property of the CLI.
     * @param listener listener added to the CLI.
     */
    public void addListener(String propertyName, PropertyChangeListener listener) {
        this.listener.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Listen asynchronously for keyboard input from the client.
     * When input is received, this method passes it to the parser so that it
     * can be sent as a message to the server, if the format is correct.
     */
    public void listenToKeyboard() {
        keyboardQueue.execute(() -> {
            while(!keyboardQueue.isShutdown()) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                listener.firePropertyChange("MessageParser", null, message);
            }
        });
    }




    public void activePlayersHandler(ActivePlayersMessage msg) {
        modelView.setPlayers(msg.getActivePlayers());
    }

    public void boardHandler(BoardMessage msg) {
        modelView.setBoard(msg);
    }

    public void islandGroupsHandler(IslandGroupsMessage msg) {
        modelView.setIslandGroups(msg.getIslandGroup());
    }

    public void islandHandler(IslandMessage msg) {
        modelView.setIsland(msg.getIsland());
    }

    public void cloudsHandler(CloudsMessage msg) {
        modelView.setClouds(msg.getClouds()); //todo
    }

    public void coinsHandler(CoinMessage msg) {
        modelView.setCoins(msg);
    }

    public void currentPlayerHandler(CurrentPlayerMessage msg) {
        modelView.setCurrentPlayer(msg.getCurrentPlayer());
    }

    //todo
    public void currentPhaseHandler(CurrentPhaseMessage msg) {
        modelView.setCurrentPhase(msg.getCurrentPhase());
    }

    public void charactersDrawnHandler(CharacterDrawnMessage msg) {
        modelView.setDrawnCharacter(msg);
    }

    public void characterPlayedHandler(CharacterPlayedMessage msg) {
        modelView.setPlayedCharacter(msg);
    }


    /**
     * Asks for the IP address and port of the server in order to
     * establish a connection with it.
     * @return the Socket instantiated for the connection with the server.
     */
    private static Socket askServerInfo() {
        Scanner scanner = new Scanner(System.in);
        String IPAddress;
        int port;

        System.out.println("> Please insert the IP address of the server (format: x.y.z.w).");
        System.out.print("> ");

        IPAddress = setIPAddress(scanner.nextLine());

        System.out.println("> Please insert the port that the server is listening on.");
        System.out.print("> ");

        try {
            port = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            port = Constants.DEFAULT_PORT;
            System.out.println("Invalid port, the server will be listening on the default port: " + port + ".");
        }

        try {
            return new Socket(IPAddress, port);
        } catch (IOException e) {
            System.err.println("A problem occurred while trying to instantiate the connection.");
            System.err.println("The application will now close...");
            System.exit(0);
        }

        return null;
    }

    /**
     * Parses the IP address inputted by the client.
     * If the parsing fails, it returns the default IP address - localhost.
     * @param input the keyboard input.
     * @return the IP address parsed from the input; if invalid, the return value is
     *         the one defined in the Constants class.
     */
    private static String setIPAddress(String input) {
        if(input.matches(Constants.IPV4_PATTERN)) {
            return input;
        }
        System.out.println("Invalid IP address, setting it to the default: " + Constants.DEFAULT_IP_ADDRESS + ".\n");
        return Constants.DEFAULT_IP_ADDRESS;
    }










    public static void main(String[] args) {
        CLI cli = new CLI();
        Socket socket = askServerInfo();
        cli.setSocketClient(new SocketClient(cli, socket));
        SocketClient client = cli.getSocketClient();

        System.out.println(Constants.ERIANTYS + "\n");

        cli.addListener("MessageParser", new MessageParser(client));
        cli.listenToKeyboard(); // Asynchronous read (keyboard input)

        client.readMessage(); // Asynchronous read (messages from server)
        client.enablePinger(true);
    }
}