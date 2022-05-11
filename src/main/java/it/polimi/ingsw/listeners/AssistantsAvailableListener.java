package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.network.message.serverToclient.AssistantsMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class AssistantsAvailableListener extends Listener {


    /**
     * Default constructor.
     *
     * @param virtualView virtual representation of the clients' view.
     */
    public AssistantsAvailableListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ArrayList<Assistant> assistants = (ArrayList<Assistant>) evt.getNewValue();
        virtualView.send(new GenericMessage("Available assistants:\n"), getCurrentPlayer());
        virtualView.send(new AssistantsMessage(assistants), getCurrentPlayer());
    }
}
