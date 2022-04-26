package it.polimi.ingsw.network.server;

public interface ClientHandler {

    boolean isConnected();

    void disconnect();

    // void sendMessage(Message message);
    void sendMessage(String message);
}
