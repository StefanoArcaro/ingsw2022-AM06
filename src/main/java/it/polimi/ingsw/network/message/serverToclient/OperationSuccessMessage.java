package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used to notify the success of an operation to the user.
 */
public class OperationSuccessMessage extends Answer {

    private final String successMessage;

    public OperationSuccessMessage(String successMessage) {
        super(MessageType.OPERATION_SUCCESS_MESSAGE);
        this.successMessage = successMessage;
    }

    @Override
    public String toString() {
        return "OperationSuccessMessage{" +
                "nickname=" + getNickname() +
                ", successMessage='" + successMessage + '\'' +
                '}';
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
