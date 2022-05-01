package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {

    private final int port;
    private final Server server;

    public SocketServer(int port, Server server) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port + ".");
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

    public void onMessageReceived(ClientHandler clientHandler, String message) {
        server.onMessageReceived(clientHandler, message);
    }
}
