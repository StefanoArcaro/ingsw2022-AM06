package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.network.message.serverToclient.FXCloudMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class FXCloudListener extends Listener {

    public FXCloudListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ArrayList<Cloud> clouds = (ArrayList<Cloud>) evt.getNewValue();

        // Available clouds
        FXCloudMessage message = new FXCloudMessage(clouds);
        virtualView.sendAll(message);
    }

}
