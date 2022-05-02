package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to notify generic information to the user.
 */
public class GenericMessage extends Answer {

    private final String message;

    public GenericMessage(String message) {
        super(MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "nickname=" + getNickname() +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }
}
