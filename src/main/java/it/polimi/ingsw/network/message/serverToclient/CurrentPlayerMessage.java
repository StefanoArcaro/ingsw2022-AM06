package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to display the current player.
 */
public class CurrentPlayerMessage extends Answer {

    private final String currentPlayer;

    public CurrentPlayerMessage(String currentPlayer) {
        super(MessageType.CURRENT_PLAYER_MESSAGE);
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
