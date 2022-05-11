package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available clouds.
 */
public class CloudChosenMessage extends Answer {

    private final Cloud cloud;

    public CloudChosenMessage(Cloud cloud) {
        super(MessageType.CLOUD_CHOSEN_MESSAGE);
        this.cloud = cloud;
    }

    @Override
    public String getMessage() {
        StringBuilder answer = new StringBuilder();

        answer.append("Student on cloud ").append(cloud.getCloudID()).append(" : ");
        for(Student student : cloud.getStudents()) {
            answer.append(student.getColor().getColorName()).append(" ");
        }
        answer.append("\n");

        return answer.toString();
    }

    public Cloud getCloud() {
        return cloud;
    }
}
