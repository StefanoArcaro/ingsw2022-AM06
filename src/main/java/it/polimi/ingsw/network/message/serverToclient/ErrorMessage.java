package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used to notify an error to the user.
 */
public class ErrorMessage extends Answer {

    private final String error;

    public ErrorMessage(String error) {
        super(MessageType.ERROR_MESSAGE);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}