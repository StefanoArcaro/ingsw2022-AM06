package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

public class DisconnectionRequestMessage extends Message {

    public DisconnectionRequestMessage(String nickname) {
        super(nickname, MessageType.DISCONNECTION_MESSAGE);
    }
}
