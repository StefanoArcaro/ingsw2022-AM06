package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to display a player's board.
 */
public class BoardMessage extends Answer {

    private final Board board;

    public BoardMessage(Board board) {
        super(MessageType.BOARD_MESSAGE);
        this.board = board;
    }

    @Override
    public String toString() {
        return "BoardMessage{" +
                "nickname=" + getNickname() +
                ", board=" + board +
                '}';
    }


    public Board getBoard() {
        return board;
    }
}
