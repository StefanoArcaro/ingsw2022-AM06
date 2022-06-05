package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.serverToclient.FXWizardChoiceMessage;
import it.polimi.ingsw.network.message.serverToclient.WizardsAvailableMessage;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class FXWizardListener extends Listener {

    public FXWizardListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ArrayList<WizardName> wizards = (ArrayList<WizardName>) evt.getNewValue();
        String currentPlayer = virtualView.getGameManager().getGame().getCurrentPlayer().getNickname();
        virtualView.sendAllExcept(new WizardsAvailableMessage(wizards), currentPlayer);
        virtualView.send(new FXWizardChoiceMessage(wizards.get(0)), currentPlayer);
    }
}
