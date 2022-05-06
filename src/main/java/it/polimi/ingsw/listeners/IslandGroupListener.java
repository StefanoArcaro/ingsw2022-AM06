package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.serverToclient.IslandGroupMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class IslandGroupListener extends Listener {

    public IslandGroupListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        IslandGroup islandGroups = (IslandGroup) evt.getNewValue();
        IslandGroupMessage message = new IslandGroupMessage(islandGroups);
        virtualView.sendAll(message);
    }
}
