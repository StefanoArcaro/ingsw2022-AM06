package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to request a login to the server.
 */
public class LoginRequestMessage extends Message {

    private int numberOfPlayers;
    private GameMode gameMode;

    public LoginRequestMessage(String nickname, int numberOfPlayers, GameMode gameMode) {
        super(nickname, MessageType.LOGIN_REQUEST_MESSAGE);
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
    }

    @Override
    public String toString() {
        return "LoginRequestMessage{" +
                "nickname=" + getNickname() +
                ", numberOfPlayers=" + numberOfPlayers +
                ", gameMode=" + gameMode +
                '}';
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

}
