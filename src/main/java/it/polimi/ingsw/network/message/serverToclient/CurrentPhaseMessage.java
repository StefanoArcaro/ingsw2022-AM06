package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to display the current phase.
 */
public class CurrentPhaseMessage extends Answer {

    private final String currentPhase;
    private final String instructions;

    public CurrentPhaseMessage(String currentPhase, String instructions) {
        super(MessageType.CURRENT_PHASE_MESSAGE);
        this.currentPhase = currentPhase;
        this.instructions = instructions;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public String getInstructions() {
        return instructions;
    }
}
