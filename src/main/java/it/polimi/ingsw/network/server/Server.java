package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.message.serverToclient.DisconnectionMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main server class
 */
public class Server {

    private final SocketServer socketServer;
    private Map<Integer, ClientHandler> idToConnection;
    private int clientID;

    // TODO
    public Server(int port) {
        this.socketServer = new SocketServer(port, this);
        this.idToConnection = new HashMap<>();
        this.clientID = -1;
        new Thread(this::quit).start(); // Asynchronously listening to server stdin for quitting
    }


    public void addClient(ClientHandler clientHandler) {
        int clientID = getNextClientID();

        idToConnection.put(clientID, clientHandler);
    }

    public int getNextClientID() {
        clientID += 1;
        return clientID;
    }


    private void quit() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            if(scanner.nextLine().equalsIgnoreCase("QUIT")) {
                // TODO disconnect clients
                broadcastMessage(new DisconnectionMessage("da fare", "disconnected"));

                System.out.println("Server quitting!");
                System.exit(0);
            }
        }
    }

    public void onDisconnect(ClientHandler clientHandler) {
        removeClient(clientHandler);
        broadcastMessage(new GenericMessage("nickname da mettere disconnected"));
        System.out.println("Client disconnected!");
        //TODO REMOVE EVERY PLAYERS
    }


    private void removeClient(ClientHandler clientHandler) {
        idToConnection.remove(clientHandler);
        //TODO SISTEMARE ALTRE MAPPE
    }

    public void broadcastMessage(Answer answer) {
        for(ClientHandler clientHandler : idToConnection.values()) {
            clientHandler.sendMessage(answer);
        }
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
