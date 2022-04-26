package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message sent from server to client to check on the connection.
 */
public class PongMessage extends Message {

    public PongMessage() {
        super(null, MessageType.PONG_MESSAGE);
    }
}
