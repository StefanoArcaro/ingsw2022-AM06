package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to reply to a login request of a client.
 */
public class LoginReplyMessage extends Answer {

    private final String message;

    public LoginReplyMessage(String message) {
        super(MessageType.LOGIN_REPLY_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
