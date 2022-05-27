package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available wizards.
 */
public class WizardsAvailableMessage extends Answer {

    private final ArrayList<WizardName> wizards;

    public WizardsAvailableMessage(ArrayList<WizardName> wizards) {
        super(MessageType.WIZARDS_AVAILABLE_MESSAGE);
        this.wizards = wizards;
    }

    public ArrayList<WizardName> getWizards() {
        return wizards;
    }
}
