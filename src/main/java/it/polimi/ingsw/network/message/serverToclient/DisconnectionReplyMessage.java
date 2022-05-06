package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to notify a disconnection to the other players.
 */
public class DisconnectionReplyMessage extends Answer {

    private final String nicknameDisconnected;

    public DisconnectionReplyMessage(String nicknameDisconnected) {
        super(MessageType.DISCONNECTION_REPLY_MESSAGE);
        this.nicknameDisconnected = nicknameDisconnected;
    }

    public String getNicknameDisconnected() {
        return nicknameDisconnected;
    }
}
