package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Entrance;
import it.polimi.ingsw.model.gameBoard.Hall;
import it.polimi.ingsw.model.gameBoard.Professor;
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
}
