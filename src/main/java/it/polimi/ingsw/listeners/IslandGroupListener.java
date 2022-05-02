package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.serverToclient.IslandGroupsMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class IslandGroupListener extends Listener {

    public IslandGroupListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ArrayList<IslandGroup> islandGroups = (ArrayList<IslandGroup>) evt.getNewValue();
        IslandGroupsMessage message = new IslandGroupsMessage(islandGroups);
        virtualView.sendAll(message);
    }
}
