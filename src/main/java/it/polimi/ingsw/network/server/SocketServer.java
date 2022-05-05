package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {

    private final int port;
    private final Server server;

    /**
     * Default constructor.
     * @param port the port that the server will be listening on.
     * @param server reference to the Server that instantiated this.
     */
    public SocketServer(int port, Server server) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port + ".\n");
        } catch (IOException e) {
            System.err.println("Server was unable to start!");
            return;
        }

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                SocketClientHandler clientHandler = new SocketClientHandler(this, client);
                server.addClient(clientHandler);
                Thread thread = new Thread(clientHandler, "client_handler " + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.err.println("Connection dropped");
            }
        }
    }

    /**
     * Forwards to the server the client handler that received a message
     * from its client, and the message.
     * @param clientHandler that received the message.
     * @param message that was received.
     */
    public void onMessageReceived(ClientHandler clientHandler, String message) {
        server.onMessageReceived(clientHandler, message);
    }

    /**
     * Forwards to the server the reference to the clientHandler whose connection
     * with its client dropped.
     * @param clientHandler the lost connection with its client.
     */
    public void onConnectionDropped(ClientHandler clientHandler) {
        server.onConnectionDropped(clientHandler);
    }
}
