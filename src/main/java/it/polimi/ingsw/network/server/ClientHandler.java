package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.serverToclient.Answer;

public interface ClientHandler {

    boolean isConnected();

    void disconnect();

    void sendMessage(Answer message);
}
