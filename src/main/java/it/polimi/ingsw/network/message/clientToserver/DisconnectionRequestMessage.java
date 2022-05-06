package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

public class DisconnectionRequestMessage extends Message {

    public DisconnectionRequestMessage() {
        super(MessageType.DISCONNECTION_REQUEST_MESSAGE);
    }

}
