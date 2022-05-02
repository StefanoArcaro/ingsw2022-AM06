package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available clouds.
 */
public class CloudsMessage extends Answer {

    private final ArrayList<Integer> clouds;

    public CloudsMessage(ArrayList<Integer> clouds) {
        super(MessageType.CLOUDS_MESSAGE);
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        return "CloudsMessage{" +
                "nickname=" + getNickname() +
                ", clouds=" + clouds +
                '}';
    }

    public ArrayList<Integer> getClouds() {
        return clouds;
    }
}
