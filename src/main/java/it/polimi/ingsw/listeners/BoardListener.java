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
        BoardMessage message = new BoardMessage((Board) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
