package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

public class CoinMessage extends Answer {

    private final int coins;

    public CoinMessage(int coins) {
        super(MessageType.COIN_MESSAGE);
        this.coins = coins;
    }

    @Override
    public String getMessage() {
        return "The current player has " + coins + " coins.";
    }
}
