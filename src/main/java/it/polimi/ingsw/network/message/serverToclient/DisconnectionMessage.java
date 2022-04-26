package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to notify a disconnection to the other players.
 */
public class DisconnectionMessage extends Answer {

    private final String nicknameDisconnected;
    private final String messageDisconnection;

    public DisconnectionMessage(String nicknameDisconnected, String messageDisconnection) {
        super(MessageType.DISCONNECTION_MESSAGE);
        this.nicknameDisconnected = nicknameDisconnected;
        this.messageDisconnection = messageDisconnection;
    }

    @Override
    public String toString() {
        return "DisconnectionMessage{" +
                "nickname=" + getNickname() +
                ", nicknameDisconnected='" + nicknameDisconnected + '\'' +
                ", messageDisconnection='" + messageDisconnection + '\'' +
                '}';
    }

    public String getNicknameDisconnected() {
        return nicknameDisconnected;
    }

    public String getMessageDisconnection() {
        return messageDisconnection;
    }
}
