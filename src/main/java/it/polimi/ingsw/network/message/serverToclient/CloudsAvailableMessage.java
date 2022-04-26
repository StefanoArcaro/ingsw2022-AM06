package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available clouds.
 */
public class CloudsAvailableMessage extends Answer {

    private final ArrayList<Integer> clouds;

    public CloudsAvailableMessage(ArrayList<Integer> clouds) {
        super(MessageType.CLOUDS_AVAILABLE_MESSAGE);
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        return "CloudsAvailableMessage{" +
                "nickname=" + getNickname() +
                ", clouds=" + clouds +
                '}';
    }

    public ArrayList<Integer> getClouds() {
        return clouds;
    }
}
