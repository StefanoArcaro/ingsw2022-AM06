package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available wizards.
 */
public class WizardsAvailableMessage extends Answer {

    private final ArrayList<WizardName> wizardNames;

    public WizardsAvailableMessage(ArrayList<WizardName> wizardNames) {
        super(MessageType.WIZARDS_AVAILABLE_MESSAGE);
        this.wizardNames = wizardNames;
    }

    @Override
    public String toString() {
        return "WizardsAvailableMessage{" +
                "nickname=" + getNickname() +
                ", wizardNames=" + wizardNames +
                '}';
    }

    public ArrayList<WizardName> getWizardNames() {
        return wizardNames;
    }
}
