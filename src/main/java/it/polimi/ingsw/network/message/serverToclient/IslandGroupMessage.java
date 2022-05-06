package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the island groups and their information.
 */
public class IslandGroupMessage extends Answer {

    private final IslandGroup islandGroup;

    public IslandGroupMessage(IslandGroup islandGroup) {
        super(MessageType.ISLAND_GROUP_MESSAGE);
        this.islandGroup = islandGroup;
    }

    public IslandGroup getIslandGroups() {
        return islandGroup;
    }
}
