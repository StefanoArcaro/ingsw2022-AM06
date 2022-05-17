package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

public class LoserMessage extends Answer {

    private final String winnerNickname;

    public LoserMessage(String winnerNickname) {
        super(MessageType.LOSER_MESSAGE);
        this.winnerNickname = winnerNickname;
    }

    public String getWinnerNickname() {
        return winnerNickname;
    }
}
