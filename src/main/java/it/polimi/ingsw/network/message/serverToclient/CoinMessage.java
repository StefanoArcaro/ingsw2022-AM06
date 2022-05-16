package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

public class CoinMessage extends Answer {

    private final String nickname;
    private final int coins;

    public CoinMessage(String nickname, int coins) {
        super(MessageType.COIN_MESSAGE);
        this.nickname = nickname;
        this.coins = coins;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCoins() {
        return coins;
    }
}
