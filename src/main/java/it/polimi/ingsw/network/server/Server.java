package it.polimi.ingsw.network.server;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main server class
 */
public class Server {

    private final SocketServer socketServer;

    // TODO
    public Server(int port) {
        this.socketServer = new SocketServer(port, this);
        new Thread(this::quit).start(); // Asynchronously listening to server stdin for quitting
    }

    private void quit() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            if(scanner.nextLine().equalsIgnoreCase("QUIT")) {
                // TODO disconnect clients
                System.out.println("Server quitting!");
                System.exit(0);
            }
        }
    }

    public void onDisconnect(SocketClientHandler clientHandler) {
        // TODO remove player
        System.out.println("Client disconnected!");
    }

    // TODO
    public static void main(String[] args) {
        System.out.println("Eriantys Server | Welcome!");

        // Port choice
        Scanner scanner = new Scanner(System.in);
        System.out.println("> Insert the port which the server will be listening on.");
        System.out.print("> ");
        int port = 0;
        try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }

        if(port < 1024) {
            System.err.println("Error: accepted ports start from 1024! Please insert a new value.");
            main(null);
        } else {
            System.out.println("Instantiating server class...");
            Server server = new Server(port);

            System.out.println("Starting socket server...");
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.submit(server.socketServer);
        }
    }
}
