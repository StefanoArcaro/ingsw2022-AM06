package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Abstract message class which must be extended by message sent by the client.
 * The client communicates with the server using this generic type of message.
 */

public class Message {
    private int clientID;

    private final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
        this.clientID = -1;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
