package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.network.message.MessageType;

import java.util.Map;

/**
 * Message which contains information about the current status of the game.
 */
public class ActivePlayersMessage extends Answer {

    // private final List<String> activePlayers;
    private final Map<String, PlayerColor> activePlayers;

    public ActivePlayersMessage(Map<String, PlayerColor> activePlayers) {
        super(MessageType.ACTIVE_PLAYERS_MESSAGE);
        this.activePlayers = activePlayers;
    }

    public Map<String, PlayerColor> getActivePlayers() {
        return activePlayers;
    }
}
