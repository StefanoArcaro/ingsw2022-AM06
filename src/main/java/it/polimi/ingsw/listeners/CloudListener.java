package it.polimi.ingsw.listeners;

import it.polimi.ingsw.network.message.serverToclient.CloudsMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class CloudListener extends Listener {

    public CloudListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CloudsMessage message = new CloudsMessage((ArrayList<Integer>) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
