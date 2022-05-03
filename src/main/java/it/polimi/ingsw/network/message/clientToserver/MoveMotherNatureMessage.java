package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to play the second action phase.
 * The client specifies the number of steps mother nature must take.
 */
public class MoveMotherNatureMessage extends Message {

    private final int numberOfSteps;

    public MoveMotherNatureMessage(int numberOfSteps) {
        super(MessageType.MOVE_MOTHER_NATURE_MESSAGE);
        this.numberOfSteps = numberOfSteps;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }
}
