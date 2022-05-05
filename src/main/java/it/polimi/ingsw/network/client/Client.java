package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.clientToserver.Message;

public abstract class Client {

    /**
     * Reads a message from the server.
     */
    public abstract void readMessage();

    /**
     * Sends a message to the server.
     * @param message the message to send.
     */
    public abstract void sendMessage(Message message);

    /**
     * Disconnects from the server.
     */
    public abstract void disconnect();

    /**
     * Activates the periodic sending of a ping message to keep the connection alive.
     * @param enabled used to either enable or disable the ping functionality.
     */
    public abstract void enablePinger(boolean enabled);
}
