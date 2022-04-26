package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

public interface ClientHandler {

    boolean isConnected();

    void disconnect();

    // void sendMessage(Message message);
    void sendMessage(String message);
}
