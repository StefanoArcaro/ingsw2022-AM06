package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message from server to client that signals the end of a game.
 */
public class GameEndedMessage extends Answer {

    public GameEndedMessage() {
        super(MessageType.GAME_ENDED_MESSAGE);
    }

}
