package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

public class CoinMessage extends Answer {

    private final int coins;

    public CoinMessage(int coins) {
        super(MessageType.MONEY_MESSAGE);
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "CoinMessage{" +
                "nickname=" + getNickname() +
                ", coins=" + coins +
                '}';
    }

    public int getCoins() {
        return coins;
    }
}
