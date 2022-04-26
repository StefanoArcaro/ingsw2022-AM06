package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to reply to a login request of a client.
 */
public class LoginReplyMessage extends Answer {

    private final boolean connectionAccepted;
    private final boolean nicknameAccepted;

    public LoginReplyMessage(boolean connectionAccepted, boolean nicknameAccepted) {
        super(MessageType.LOGIN_REPLY_MESSAGE);
        this.connectionAccepted = connectionAccepted;
        this.nicknameAccepted = nicknameAccepted;
    }

    @Override
    public String toString() {
        return "LoginSuccessMessage{" +
                "nickname= "  + getNickname() +
                ", nickname accepted and connection established" +
                '}';
    }
}
