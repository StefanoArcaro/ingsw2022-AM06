package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the island groups and their information.
 */
public class IslandGroupsMessage extends Answer {

    private final ArrayList<IslandGroup> islandGroups;

    public IslandGroupsMessage(ArrayList<IslandGroup> islandGroups) {
        super(MessageType.ISLAND_GROUPS_MESSAGE);
        this.islandGroups = islandGroups;
    }

    @Override
    public String toString() {
        return "IslandGroupsMessage{" +
                "nickname=" + getNickname() +
                ", islandGroups=" + islandGroups +
                '}';
    }

    public ArrayList<IslandGroup> getIslandGroups() {
        return islandGroups;
    }
}
