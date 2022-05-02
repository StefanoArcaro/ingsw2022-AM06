package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a specific assistant.
 */
public class AssistantRequestMessage extends Message {

    private int assistantID;

    public AssistantRequestMessage (String nickname, int assistantID) {
        super(nickname, MessageType.ASSISTANT_REQUEST_MESSAGE);
        this.assistantID = assistantID;
    }

    @Override
    public String toString() {
        return "AssistantRequestMessage{" +
                "nickname=" + getNickname() +
                ", assistantID=" + assistantID +
                '}';
    }

    public int getAssistantID() {
        return assistantID;
    }
}
