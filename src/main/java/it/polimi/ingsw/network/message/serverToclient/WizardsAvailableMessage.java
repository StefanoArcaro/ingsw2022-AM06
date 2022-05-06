package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.gameBoard.Wizard;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available wizards.
 */
public class WizardsAvailableMessage extends Answer {

    private final ArrayList<Wizard> wizards;

    public WizardsAvailableMessage(ArrayList<Wizard> wizards) {
        super(MessageType.WIZARDS_AVAILABLE_MESSAGE);
        this.wizards= wizards;
    }

    @Override
    public String getMessage() {
        StringBuilder wizardNames = new StringBuilder("Available wizards: ");

        for(Wizard wizard : wizards) {
            wizardNames.append(wizard.getWizardName()).append(" ");
        }
        return wizardNames.toString();
    }
}
