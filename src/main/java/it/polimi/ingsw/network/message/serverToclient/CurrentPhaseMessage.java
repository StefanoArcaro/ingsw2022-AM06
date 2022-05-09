package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to display the current phase.
 */
public class CurrentPhaseMessage extends Answer {

    private final String currentPhase;

    public CurrentPhaseMessage(String currentPhase) {
        super(MessageType.CURRENT_PHASE_MESSAGE);
        this.currentPhase = currentPhase;
    }

    @Override
    public String getMessage() {
        return currentPhase;
    }

    public String getCurrentPhase() {
        return currentPhase; //todo
    }
}
