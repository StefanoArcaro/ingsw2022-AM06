package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to request a specific wizard.
 */
public class WizardRequestMessage extends Message {

    private WizardName wizardName;

    public WizardRequestMessage(String nickname, WizardName wizardName) {
        super(nickname, MessageType.WIZARD_REQUEST_MESSAGE);
        this.wizardName = wizardName;
    }

    @Override
    public String toString() {
        return "WizardRequestMessage{" +
                "nickname=" + getNickname() +
                ", wizardName=" + wizardName +
                '}';
    }

    public WizardName getWizardName() {
        return wizardName;
    }
}
