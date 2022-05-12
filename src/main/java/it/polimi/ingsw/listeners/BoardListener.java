package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.network.message.serverToclient.BoardMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class BoardListener extends Listener {

    public BoardListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Board board = (Board) evt.getNewValue();
        BoardMessage message = new BoardMessage(board.getPlayerNickname(), board.getEntrance(), board.getHall(), board.getProfessors(), board.getTowers());
        if(evt.getOldValue() != null) {
            String nickname = (String) evt.getOldValue();
            virtualView.send(message, nickname);
        } else {
            virtualView.sendAll(message);
        }
    }
}
