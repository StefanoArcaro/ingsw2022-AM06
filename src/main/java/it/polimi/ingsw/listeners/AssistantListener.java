package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.network.message.serverToclient.AssistantsMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class AssistantListener extends Listener {

    public AssistantListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AssistantsMessage message = new AssistantsMessage((ArrayList<Assistant>) evt.getNewValue());
        virtualView.sendAll(message);
    }
}
