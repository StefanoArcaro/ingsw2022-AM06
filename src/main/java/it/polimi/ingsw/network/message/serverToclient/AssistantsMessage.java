package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available assistants.
 */
public class AssistantsMessage extends Answer {

    private final ArrayList<Assistant> assistants;

    public AssistantsMessage(ArrayList<Assistant> assistants) {
        super(MessageType.ASSISTANTS_MESSAGE);
        this.assistants = assistants;
    }

    public ArrayList<Assistant> getAssistants() {
        return assistants;
    }
}
