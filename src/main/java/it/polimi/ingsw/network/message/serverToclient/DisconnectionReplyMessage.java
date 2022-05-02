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

    @Override
    public String toString() {
        return nicknameDisconnected + " has disconnected.";
    }

    public String getNicknameDisconnected() {
        return nicknameDisconnected;
    }
}
