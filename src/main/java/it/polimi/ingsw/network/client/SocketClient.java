package it.polimi.ingsw.network.client;

import com.google.gson.Gson;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.clientToserver.PingMessage;
import it.polimi.ingsw.network.message.serverToclient.Answer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient extends Client {

    private static final int PING_INITIAL_DELAY = 0;
    private static final int PING_PERIOD = 15000;

    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final ExecutorService keyboardQueue;
    private final ExecutorService readQueue;
    private final ScheduledExecutorService pinger;
    private final MessageParser messageParser;

    public SocketClient(Socket socket) {
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("A problem with I/O streams instantiation occurred.");
            disconnect();
        }

        this.keyboardQueue = Executors.newSingleThreadExecutor();
        this.readQueue = Executors.newSingleThreadExecutor();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
        this.messageParser = new MessageParser(this);
    }

    @Override
    public void listenToKeyboard() {
        keyboardQueue.execute(() -> {
            while(!keyboardQueue.isShutdown()) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                // TODO parser should be added as a listener in CLI
                messageParser.parseInput(message);
                // TODO add parsing for disconnecting: done but to modify, see MessageParser 41
            }
        });
    }

    // From server
    @Override
    public void readMessage() {
        readQueue.execute(() -> {
            while(!readQueue.isShutdown()) {
                try {
                    Gson gson = new Gson();
                    Answer message = gson.fromJson(reader.readLine(), Answer.class);

                    if(message.getMessageType() != MessageType.PONG_MESSAGE) {
                        System.out.println(message);
                    }

                    if(message.getMessageType() == MessageType.DISCONNECTION_MESSAGE) {
                        disconnect();
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    // To server
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

    @Override
    public void disconnect() {
        closeEverything();
    }

    @Override
    public void enablePinger(boolean enabled) {
        if(enabled) {
            pinger.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), PING_INITIAL_DELAY, PING_PERIOD, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }

    private void closeEverything() {
        try {
            enablePinger(false);
            keyboardQueue.shutdownNow();
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
        } finally {
            System.exit(0);
        }
    }
}
