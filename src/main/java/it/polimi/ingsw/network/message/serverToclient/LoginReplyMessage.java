package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to reply to a login request of a client.
 */
public class LoginReplyMessage extends Answer {

    private final String message;
    private final String nickname;
    private final NumberOfPlayers numberOfPlayers;
    private final GameMode gameMode;

    public LoginReplyMessage(String message, String nickname, NumberOfPlayers numberOfPlayers, GameMode gameMode) {
        super(MessageType.LOGIN_REPLY_MESSAGE);
        this.message = message;
        this.nickname = nickname;
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
    }

    public String getMessage() {
        return message;
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
