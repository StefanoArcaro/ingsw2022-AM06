package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

import java.util.List;

/**
 * Message which contains information about the current status of the game.
 */
public class MatchInfoMessage extends Answer {

    private final List<String> activePlayers;
    private final List<CharacterID> drawnCharacters;
    private final List<Board> boards;
    private final List<IslandGroup> islandGroups;
    private final String currentPlayerNickname;

    public MatchInfoMessage(List<String> activePlayers, List<CharacterID> drawnCharacters,
                            List<Board> boards, List<IslandGroup> islandGroups, String currentPlayerNickname) {
        super(MessageType.MATCH_INFO_MESSAGE);
        this.activePlayers = activePlayers;
        this.drawnCharacters = drawnCharacters;
        this.boards = boards;
        this.islandGroups = islandGroups;
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public String toString() {
        return "MatchInfoMessage{" +
                "nickname=" + getNickname() +
                ", activePlayers=" + activePlayers +
                ", drawnCharacters=" + drawnCharacters +
                ", boards=" + boards +
                ", islandGroups=" + islandGroups +
                ", currentPlayerNickname='" + currentPlayerNickname + '\'' +
                '}';
    }

    public List<String> getActivePlayers() {
        return activePlayers;
    }

    public List<CharacterID> getDrawnCharacters() {
        return drawnCharacters;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public List<IslandGroup> getIslandGroups() {
        return islandGroups;
    }

    public String getCurrentPlayerNickname() {
        return currentPlayerNickname;
    }
}
