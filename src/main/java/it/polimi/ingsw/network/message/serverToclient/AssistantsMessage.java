package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available assistants.
 */
public class AssistantsMessage extends Answer {

    private final ArrayList<Assistant> assistants;
    private final boolean played;

    public AssistantsMessage(ArrayList<Assistant> assistants, boolean played) {
        super(MessageType.ASSISTANTS_MESSAGE);
        this.assistants = assistants;
        this.played = played;
    }

    @Override
    public String getMessage() {
        StringBuilder assistantString = new StringBuilder();

        for(Assistant assistant : assistants) {
            assistantString.append("Priority = ").append(assistant.getPriority()).append("- Number of steps = ").append(assistant.getNumberOfSteps()).append("\n");
        }

        return assistantString.toString();
    }

    public ArrayList<Assistant> getAssistants() {
        return assistants;
    }

    public boolean isPlayed() {
        return played;
    }
}
