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

    private final Entrance entrance;
    private final Hall hall;
    private final int towers;
    private final ArrayList<Professor> professors;


    public BoardMessage(Entrance entrance, Hall hall, ArrayList<Professor> professors, int towers) {
        super(MessageType.BOARD_MESSAGE);
        this.entrance = entrance;
        this.hall = hall;
        this.towers = towers;
        this.professors = professors;
    }

    /*public BoardMessage(Board board) {
        super(MessageType.BOARD_MESSAGE);
        this.board = board;
    }*/



    @Override
    public String toString() {
        return "BoardMessage{" +
                "nickname=" + getNickname() +
                '}';
    }


}
