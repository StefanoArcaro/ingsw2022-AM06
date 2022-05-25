package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to notify that the game is starting.
 */
public class GameStartedMessage extends Answer {

    public GameStartedMessage() {
        super(MessageType.GAME_STARTED_MESSAGE);
    }

}
