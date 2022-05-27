package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to notify that every player chose the wizard.
 */
public class GameReadyMessage extends Answer {

    public GameReadyMessage() {
        super(MessageType.GAME_READY_MESSAGE);
    }

}
