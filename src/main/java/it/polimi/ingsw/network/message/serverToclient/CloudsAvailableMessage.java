package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class CloudsAvailableMessage extends Answer {

    private final ArrayList<Cloud> clouds;

    public CloudsAvailableMessage(ArrayList<Cloud> clouds) {
        super(MessageType.CLOUDS_AVAILABLE_MESSAGE);
        this.clouds = clouds;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

}
