package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

public class CoinMessage extends Answer {

    public int coins;

    public CoinMessage(int coins) {
        super(MessageType.COIN_MESSAGE);
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
