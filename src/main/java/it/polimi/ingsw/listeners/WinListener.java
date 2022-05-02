package it.polimi.ingsw.listeners;

import it.polimi.ingsw.network.message.serverToclient.WinnerMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class WinListener extends Listener {

    public WinListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        WinnerMessage message = new WinnerMessage((String) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
