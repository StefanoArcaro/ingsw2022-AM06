package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to play the first action phase.
 * The client specifies the color of the student to move and the destination.
 */
public class MoveStudentMessage extends Message {

    private CreatureColor color;
    private int destination;

    public MoveStudentMessage(String nickname, CreatureColor color, int destination) {
        super(nickname, MessageType.MOVE_STUDENT_MESSAGE);
        this.color = color;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "MoveStudentMessage{" +
                "nickname=" + getNickname() +
                ", color=" + color +
                ", destination=" + destination +
                '}';
    }

    public CreatureColor getColor() {
        return color;
    }

    public int getDestination() {
        return destination;
    }
}
