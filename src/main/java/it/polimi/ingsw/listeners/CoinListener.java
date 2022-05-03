package it.polimi.ingsw.listeners;

import it.polimi.ingsw.network.message.serverToclient.CoinMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class CoinListener extends Listener {

    public CoinListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CoinMessage message = new CoinMessage((int)evt.getNewValue());
        virtualView.sendAll(message);
    }
}
