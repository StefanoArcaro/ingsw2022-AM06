package it.polimi.ingsw.network.server;

import com.google.gson.Gson;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.message.serverToclient.DisconnectionMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;
import it.polimi.ingsw.network.message.serverToclient.PongMessage;

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
            synchronized (inputLock) {
                Gson gson = new Gson();
                String message;

                while ((message = reader.readLine()) != null) {
                    Message msg = gson.fromJson(message, Message.class);

                    if(msg.getMessageType() == MessageType.PING_MESSAGE) {
                        sendMessage(new PongMessage());
                    } else if(msg.getMessageType() == MessageType.DISCONNECTION_MESSAGE) {
                        // TODO change
                        sendMessage(new DisconnectionMessage("server", "disconnected"));
                        disconnect();
                    } else {
                        // TODO change
                        // socketServer.onMessageReceived();
                        sendMessage(new GenericMessage("SERVER echo: " + message));
                    }

                    System.out.println("Client: " + message);
                }
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
            socketServer.onDisconnect(this); // TODO update every other player
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
