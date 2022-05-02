package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to reply to a login request of a client.
 */
public class LoginReplyMessage extends Answer {

    private final String message;

    public LoginReplyMessage(String message) {
        super(MessageType.LOGIN_REPLY_MESSAGE);
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginSuccessMessage{" +
                "nickname= "  + getNickname() +
                ", nickname accepted and connection established" +
                '}';
    }

    public String getMessage() {
        return message;
    }
}
