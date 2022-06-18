package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display a player's board.
 */
public class BoardMessage extends Answer {

    private final String nickname;
    private final Entrance entrance;
    private final Hall hall;
    private final int towers;
    private final ArrayList<Professor> professors;

    public BoardMessage(String nickname, Entrance entrance, Hall hall, ArrayList<Professor> professors, int towers) {
        super(MessageType.BOARD_MESSAGE);
        this.nickname = nickname;
        this.entrance = entrance;
        this.hall = hall;
        this.towers = towers;
        this.professors = professors;
    }

    public String getNickname() {
        return nickname;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public Hall getHall() {
        return hall;
    }

    public int getTowers() {
        return towers;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    @Override
    public String getMessage() {
        String owner = nickname + "'s board.\n";

        StringBuilder entranceStudents = new StringBuilder("Entrance:\n");
        for(Student student : entrance.getStudents()) {
            entranceStudents.append(student.getColor().getColorName()).append(" ");
        }
        entranceStudents.append("\n");

        StringBuilder hallStudents = new StringBuilder("Hall:\n");
        for(Table table : hall.getStudents()) {
            hallStudents.append(table.getColor().getColorName()).append(" ").append(table.getLength()).append("\n");
        }

        StringBuilder professorsInBoard = new StringBuilder("Professors: ");
        if(professors.size() > 0) {
            for (Professor professor : professors) {
                professorsInBoard.append(professor.getColor().getColorName()).append(" ");
            }
        }
        professorsInBoard.append("\n");


        String numTowers = "Number of towers: " + towers;

        return owner + entranceStudents + hallStudents + professorsInBoard + numTowers;
    }
}
