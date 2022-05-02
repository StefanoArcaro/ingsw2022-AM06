package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.network.message.serverToclient.IslandMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class IslandListener extends Listener {

    public IslandListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IslandMessage message = new IslandMessage((Island) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
