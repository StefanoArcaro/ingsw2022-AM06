package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.SocketClient;

import java.io.IOException;
import java.net.Socket;

public class CLI {

    public CLI() {}

    public static void main(String[] args) {
        System.out.println("Eriantys client | Welcome!");

        // TODO
        // askServerInfo() -> to connect to the server, so IP and port

        try {
            Socket socket = new Socket("localhost", 1234);
            SocketClient client = new SocketClient(socket);
            client.listenToKeyboard(); // Asynchronous read (keyboard input)
            client.readMessage(); // Asynchronous read (messages from server)
            client.enablePinger(true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
