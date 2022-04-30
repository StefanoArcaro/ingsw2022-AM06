package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message sent from server to client to check on the connection.
 */
// TODO check (extends Answer instead of Message)
public class PongMessage extends Answer {

    public PongMessage() {
        super(MessageType.PONG_MESSAGE);
    }
}
