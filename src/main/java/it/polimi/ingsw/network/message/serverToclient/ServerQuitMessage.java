package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

public class ServerQuitMessage extends Answer {

    public ServerQuitMessage() {
        super(MessageType.SERVER_QUIT_MESSAGE);
    }
}
