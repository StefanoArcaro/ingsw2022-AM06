package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message used by the client to play the third action phase.
 * The client specifies the id of the cloud to take.
 */
public class PickCloudMessage extends Message {

    private int cloudID;

    public PickCloudMessage(String nickname, int cloudID) {
        super(nickname, MessageType.PICK_CLOUD_MESSAGE);
        this.cloudID = cloudID;
    }

    @Override
    public String toString() {
        return "PickCloudMessage{" +
                "nickname=" + getNickname() +
                ", cloudID=" + cloudID +
                '}';
    }

    public int getCloudID() {
        return cloudID;
    }
}
