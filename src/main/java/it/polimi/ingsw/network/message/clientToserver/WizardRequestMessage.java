package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to request a specific wizard.
 */
public class WizardRequestMessage extends Message {

    private final WizardName wizardName;

    public WizardRequestMessage(WizardName wizardName) {
        super(MessageType.WIZARD_REQUEST_MESSAGE);
        this.wizardName = wizardName;
    }

    public WizardName getWizardName() {
        return wizardName;
    }
}
