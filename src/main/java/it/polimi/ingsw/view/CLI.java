package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.network.client.SocketClient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CLI {

    private SocketClient socketClient;
    private final ExecutorService keyboardQueue;
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);

    public CLI() {
        this.keyboardQueue = Executors.newSingleThreadExecutor();
    }

    public SocketClient getSocketClient() {
        return this.socketClient;
    }

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public void addListener(String propertyName, PropertyChangeListener listener) {
        this.listener.addPropertyChangeListener(propertyName, listener);
    }

    public void listenToKeyboard() {
        keyboardQueue.execute(() -> {
            while(!keyboardQueue.isShutdown()) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                // TODO parser should be added as a listener in CLI
                listener.firePropertyChange("MessageParser", null, message);
                // TODO add parsing for disconnecting: done but to modify, see MessageParser 41
            }
        });
    }

    public static void main(String[] args) {
        System.out.println("Eriantys client | Welcome!");

        CLI cli = new CLI();

        // TODO
        // askServerInfo() -> to connect to the server, so IP and port

        try {
            Socket socket = new Socket("localhost", 1234);
            cli.setSocketClient(new SocketClient(socket));
            SocketClient client = cli.getSocketClient();
            cli.addListener("MessageParser", new MessageParser(client));
            cli.listenToKeyboard(); // Asynchronous read (keyboard input)

            client.readMessage(); // Asynchronous read (messages from server)
            client.enablePinger(true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
