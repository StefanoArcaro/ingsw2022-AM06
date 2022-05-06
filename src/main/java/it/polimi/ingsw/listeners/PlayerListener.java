package it.polimi.ingsw.listeners;

import it.polimi.ingsw.network.message.serverToclient.CurrentPlayerMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class PlayerListener extends Listener {

    public PlayerListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        virtualView.sendAll(new CurrentPlayerMessage((String) evt.getNewValue()));
    }
}
