package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the server to display the available clouds.
 */
public class CloudChosenMessage extends Answer {

    private final Cloud cloud;

    public CloudChosenMessage(Cloud cloud) {
        super(MessageType.CLOUD_CHOSEN_MESSAGE);
        this.cloud = cloud;
    }

    public Cloud getCloud() {
        return cloud;
    }
}
