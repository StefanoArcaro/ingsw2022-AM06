package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.message.serverToclient.Answer;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
            client.setSoTimeout(10000);
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
            synchronized (inputLock) {
                //client.setSoTimeout(10000);
                String message = reader.readLine();
                socketServer.onMessageReceived(this, message);
            }
        }
    }

    @Override
    public void sendMessage(Answer message) {
        Gson gson = new Gson();
        String msg = gson.toJson(message);

        try {
            synchronized (outputLock) {
                writer.write(msg);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
