package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message sent from server to client to check on the connection.
 */
public class PongMessage extends Answer {

    public PongMessage() {
        super(MessageType.PONG_MESSAGE);
    }
}
