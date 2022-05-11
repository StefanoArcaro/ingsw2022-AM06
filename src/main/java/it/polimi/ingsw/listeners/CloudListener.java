package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.network.message.serverToclient.CloudChosenMessage;
import it.polimi.ingsw.network.message.serverToclient.CloudsAvailableMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class CloudListener extends Listener {

    public CloudListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ArrayList<Cloud> clouds = (ArrayList<Cloud>) evt.getNewValue();

        if(clouds.size() == 1) {
            // Cloud chosen
            CloudChosenMessage message = new CloudChosenMessage(clouds.get(0));
            virtualView.sendAllExcept(message, getCurrentPlayer());
        } else {
            // Available clouds
            CloudsAvailableMessage message = new CloudsAvailableMessage(clouds);
            virtualView.send(message, getCurrentPlayer());
        }
    }
}
