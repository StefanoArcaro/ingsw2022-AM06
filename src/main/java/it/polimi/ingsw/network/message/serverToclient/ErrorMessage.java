package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to notify an error to the user.
 */
public class ErrorMessage extends Answer {

    private final String error;

    public ErrorMessage(String error) {
        super(MessageType.ERROR_MESSAGE);
        this.error = error;
    }

    @Override
    public String toString() {
        return "OperationSuccessMessage{" +
                "nickname=" + getNickname() +
                ", error='" + error + '\'' +
                '}';
    }

    public String getError() {
        return error;
    }
}