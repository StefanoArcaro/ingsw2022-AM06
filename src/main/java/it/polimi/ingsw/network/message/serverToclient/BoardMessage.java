package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to display a player's board.
 */
public class BoardMessage extends Answer {

    private final String nicknamePlayer;
    private final Board board;

    public BoardMessage(String nicknamePlayer, Board board) {
        super(MessageType.BOARD_MESSAGE);
        this.nicknamePlayer = nicknamePlayer;
        this.board = board;
    }

    @Override
    public String toString() {
        return "BoardMessage{" +
                "nickname=" + getNickname() +
                ", nicknamePlayer='" + nicknamePlayer + '\'' +
                ", board=" + board +
                '}';
    }

    public String getNicknamePlayer() {
        return nicknamePlayer;
    }

    public Board getBoard() {
        return board;
    }
}
