package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Abstract answer class which must be extended by message sent by the server.
 * The server communicates with the client using this generic type of message.
 */
public class Answer {

    private final MessageType messageType;

    public Answer(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return "Server to client";
    }
}
