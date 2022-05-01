package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to notify a disconnection to the other players.
 */
public class DisconnectionMessage extends Answer {

    private final String nicknameDisconnected;

    public DisconnectionMessage(String nicknameDisconnected) {
        super(MessageType.DISCONNECTION_MESSAGE);
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
