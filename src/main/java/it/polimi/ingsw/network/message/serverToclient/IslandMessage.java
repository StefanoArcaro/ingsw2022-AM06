package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.network.message.MessageType;

public class IslandMessage extends Answer {

    private final Island island;

    public IslandMessage(Island island) {
        super(MessageType.ISLAND_MESSAGE);
        this.island = island;
    }

    public Island getIsland() {
        return island;
    }
}
