package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to notify a disconnection to the other players.
 */
public class DisconnectionReplyMessage extends Answer {

    public DisconnectionReplyMessage() {
        super(MessageType.DISCONNECTION_REPLY_MESSAGE);
    }

}
