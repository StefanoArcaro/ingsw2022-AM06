package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play the third action phase.
 * The client specifies the id of the cloud to take.
 */
public class PickCloudMessage extends Message {

    private final int cloudID;

    public PickCloudMessage(int cloudID) {
        super(MessageType.PICK_CLOUD_MESSAGE);
        this.cloudID = cloudID;
    }

    public int getCloudID() {
        return cloudID;
    }
}
