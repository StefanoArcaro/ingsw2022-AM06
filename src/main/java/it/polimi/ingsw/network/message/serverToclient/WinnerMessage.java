package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to notify that a player has won the game.
 */
public class WinnerMessage extends Answer {

    private final String winnerNickname;

    public WinnerMessage(String winnerNickname) {
        super(MessageType.WINNER_MESSAGE);
        this.winnerNickname = winnerNickname;
    }

    public String getWinnerNickname() {
        return winnerNickname;
    }
}
