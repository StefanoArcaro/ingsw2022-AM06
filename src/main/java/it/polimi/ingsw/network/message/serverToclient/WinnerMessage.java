package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to notify that a player has won the game.
 */
public class WinnerMessage extends Answer {

    private final String winnerNickname;

    public WinnerMessage(String winnerNickname) {
        super(MessageType.WINNER_MESSAGE);
        this.winnerNickname = winnerNickname;
    }

    @Override
    public String toString() {
        return "WinnerMessage{" +
                "nickname=" + getNickname() +
                ", winnerNickname='" + winnerNickname + '\'' +
                '}';
    }

    public String getWinnerNickname() {
        return winnerNickname;
    }
}
