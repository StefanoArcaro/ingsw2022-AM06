package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.serverToclient.IslandGroupsMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Map;

public class IslandGroupListener extends Listener {

    public IslandGroupListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Map.Entry<ArrayList<IslandGroup>, Integer> islands = (Map.Entry<ArrayList<IslandGroup>, Integer>) evt.getNewValue();
        ArrayList<IslandGroup> islandGroups = islands.getKey();
        int motherNatureIndex = islands.getValue();
        IslandGroupsMessage message = new IslandGroupsMessage(islandGroups, motherNatureIndex);

        if(evt.getOldValue() != null) {
            String nickname = (String) evt.getOldValue();
            virtualView.send(message, nickname);
        } else {
            virtualView.sendAll(message);
        }
    }
}
