package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available clouds.
 */
public class CloudsMessage extends Answer {

    private final ArrayList<Cloud> clouds;

    public CloudsMessage(ArrayList<Cloud> clouds) {
        super(MessageType.CLOUDS_MESSAGE);
        this.clouds = clouds;
    }

    @Override
    public String getMessage() {
        StringBuilder answer = new StringBuilder();

        for(Cloud cloud : clouds) {
            answer.append("Student on cloud ").append(cloud.getCloudID()).append(" : ");
            for(Student student : cloud.getStudents()) {
                answer.append(student.getColor().getColorName()).append(" ");
            }
            answer.append("\n");
        }

        return answer.toString();
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
}
