package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.MessageType;

public class FXWizardChoiceMessage extends Answer {

    private final WizardName wizardName;

    public FXWizardChoiceMessage(WizardName wizardName) {
        super(MessageType.FX_WIZARD_CHOICE_MESSAGE);
        this.wizardName = wizardName;
    }

    public WizardName getWizardName() {
        return wizardName;
    }
}
