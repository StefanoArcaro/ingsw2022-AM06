package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to request a login to the server.
 */
public class LoginRequestMessage extends Message {

    private final String nickname;
    private final NumberOfPlayers numberOfPlayers;
    private final GameMode gameMode;

    public LoginRequestMessage(String nickname, NumberOfPlayers numberOfPlayers, GameMode gameMode) {
        super(MessageType.LOGIN_REQUEST_MESSAGE);
        this.nickname = nickname;
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
    }

    public String getNickname() {
        return nickname;
    }

    public NumberOfPlayers getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

}
