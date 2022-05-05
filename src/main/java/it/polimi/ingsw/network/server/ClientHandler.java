package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.serverToclient.Answer;

public interface ClientHandler {

    /**
     * Sends a message to the connected client.
     * @param message the message to send.
     */
    void sendMessage(Answer message);

    /**
     * Closes the connection with the client.
     */
    void disconnect();
}
