package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to notify generic information to the user.
 */
public class GenericMessage extends Answer {

    private final String message;

    public GenericMessage(String message) {
        super(MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
