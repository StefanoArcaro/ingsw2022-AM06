package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class FXCloudMessage extends Answer {

    private final ArrayList<Cloud> clouds;

    public FXCloudMessage(ArrayList<Cloud> clouds) {
        super(MessageType.FX_CLOUD_MESSAGE);
        this.clouds = clouds;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }
}
