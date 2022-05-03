package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a specific assistant.
 */
public class AssistantRequestMessage extends Message {

    private final int assistantID;

    public AssistantRequestMessage (int assistantID) {
        super(MessageType.ASSISTANT_REQUEST_MESSAGE);
        this.assistantID = assistantID;
    }

    public int getAssistantID() {
        return assistantID;
    }
}
