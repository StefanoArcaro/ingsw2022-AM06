package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.network.message.serverToclient.AssistantsMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class AssistantPlayedListener extends Listener {

    public AssistantPlayedListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AssistantsMessage message = new AssistantsMessage((ArrayList<Assistant>) evt.getNewValue());
        virtualView.sendAllExcept(new GenericMessage("ASSISTANTPLAYED_" + getCurrentPlayer() + " played the following assistant:"), getCurrentPlayer());
        virtualView.sendAll(message);
    }
}
