package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message sent from client to server to check on the connection.
 */
public class PingMessage extends Message {

    public PingMessage() {
        super(null, MessageType.PING_MESSAGE);
    }
}
