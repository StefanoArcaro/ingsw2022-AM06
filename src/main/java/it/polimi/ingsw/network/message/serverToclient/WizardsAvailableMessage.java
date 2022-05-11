package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.gameBoard.Wizard;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available wizards.
 */
public class WizardsAvailableMessage extends Answer {

    private final ArrayList<WizardName> wizards;

    public WizardsAvailableMessage(ArrayList<WizardName> wizards) {
        super(MessageType.WIZARDS_AVAILABLE_MESSAGE);
        this.wizards= wizards;
    }

    @Override
    public String getMessage() {
        StringBuilder wizardNames = new StringBuilder("Available wizards: ");

        for(WizardName wizard : wizards) {
            wizardNames.append(wizard).append(" ");
        }
        return wizardNames.toString();
    }

    public ArrayList<WizardName> getWizards() {
        return wizards;
    }
}
