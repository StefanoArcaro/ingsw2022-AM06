package it.polimi.ingsw.network.server;

import java.io.*;
import java.net.Socket;

public class SocketClientHandler implements ClientHandler, Runnable {

    private final SocketServer socketServer;
    private final Socket client;
    private boolean connected;
    private BufferedReader reader;
    private BufferedWriter writer;

    private final Object inputLock;
    private final Object outputLock;

    public SocketClientHandler(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        try {
            this.reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        inputLock = new Object();
        outputLock = new Object();
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.err.println("Client " + client.getInetAddress() + ": connection dropped.");
            disconnect();
        }
    }

    private void handleClientConnection() throws IOException {
        System.out.println("Client connected from " + client.getInetAddress());

        while(!Thread.currentThread().isInterrupted()) {
            String message;
            while ((message = reader.readLine()) != null) {
                writer.write(message);
                writer.newLine();
                writer.flush();
                System.out.println("Client: " + message);
            }
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void disconnect() {
        if(connected) {
            try {
                if(!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            connected = false;
            Thread.currentThread().interrupt();
            // socketServer.onDisconnect(this) TODO
        }
    }

    @Override
    public void sendMessage(String message) {

    }
}
