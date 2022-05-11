package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class CloudsAvailableMessage extends Answer {

    private final ArrayList<Cloud> clouds;

    public CloudsAvailableMessage(ArrayList<Cloud> clouds) {
        super(MessageType.CLOUDS_AVAILABLE_MESSAGE);
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
