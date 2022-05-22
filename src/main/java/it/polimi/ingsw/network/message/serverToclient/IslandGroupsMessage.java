package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the island groups and their information.
 */
public class IslandGroupsMessage extends Answer {

    private final ArrayList<IslandGroup> islandGroups;
    private final int motherNatureIndex;

    public IslandGroupsMessage(ArrayList<IslandGroup> islandGroups, int motherNatureIndex) {
        super(MessageType.ISLAND_GROUPS_MESSAGE);
        this.islandGroups = islandGroups;
        this.motherNatureIndex = motherNatureIndex;
    }

    public ArrayList<IslandGroup> getIslandGroups() {
        return new ArrayList<>(islandGroups);
    }

    public int getMotherNatureIndex() {
        return motherNatureIndex;
    }
}
