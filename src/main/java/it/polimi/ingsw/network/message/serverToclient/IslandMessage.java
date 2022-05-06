package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

public class IslandMessage extends Answer {

    private final Island island;

    public IslandMessage(Island island) {
        super(MessageType.ISLAND_MESSAGE);
        this.island = island;
    }

    public String getMessage() {
        String message = "Island group: " + island.getIslandID() + ":\n";

        for(Student s : island.getStudents()) {
            message = message + "\t-" + s.getColor().getColorName() + "\n";
        }

        if(island.getTower() != null) {
            message = message + "\tConquered by -> " + island.getTower() + "\n";
        }

        return message;
    }
}
