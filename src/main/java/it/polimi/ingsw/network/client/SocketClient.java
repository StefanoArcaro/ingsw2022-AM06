package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient extends Client {

    private static final int PING_INITIAL_DELAY = 0;
    private static final int PING_PERIOD = 5000;

    private final Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final ExecutorService readQueue;
    private final ScheduledExecutorService pinger;

    public SocketClient(Socket socket) {
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("A problem with I/O streams instantiation occurred.");
            closeEverything(socket, reader, writer);
        }

        this.readQueue = Executors.newSingleThreadExecutor();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
    }

    private void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer) {
        try {
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
        }
    }

    @Override
    public void readMessage() {
        readQueue.execute(() -> {
            while(!readQueue.isShutdown()) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                sendMessage(message);
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        // TODO change it to use Message class
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
            System.out.println("echo: " + reader.readLine());
        } catch (IOException e) {
            closeEverything(socket, reader, writer);
        }
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void enablePinger(boolean enabled) {
        if(enabled) {
            pinger.scheduleAtFixedRate(() -> sendMessage("Ping"), PING_INITIAL_DELAY, PING_PERIOD, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }

    // TODO MODIFY
    public static void main(String[] args) {
        System.out.println("Eriantys client | Welcome!");

        // TODO
        // askServerInfo() -> to connect to the server, so IP and port

        try {
            Socket socket = new Socket("localhost", 1234);
            SocketClient client = new SocketClient(socket);
            client.readMessage(); // Asynchronous read from stdin
            client.enablePinger(false);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
