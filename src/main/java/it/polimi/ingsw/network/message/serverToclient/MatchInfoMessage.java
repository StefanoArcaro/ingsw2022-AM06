package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.model.gameBoard.MotherNature;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Message which contains information about the current status of the game.
 */
public class MatchInfoMessage extends Answer {

    private final ArrayList<Player> activePlayers;
    private final ArrayList<Integer> drawnCharacters;
    private final ArrayList<Board> boards;
    private final ArrayList<IslandGroup> islandGroups;
    private final String currentPlayerNickname;
    private final int motherNatureIndex;

    public MatchInfoMessage(ArrayList<Player> activePlayers, ArrayList<Integer> drawnCharacters,
                            ArrayList<Board> boards, ArrayList<IslandGroup> islandGroups,
                            String currentPlayerNickname, int motherNatureIndex) {
        super(MessageType.MATCH_INFO_MESSAGE);
        this.activePlayers = activePlayers;
        this.drawnCharacters = drawnCharacters;
        this.boards = boards;
        this.islandGroups = islandGroups;
        this.currentPlayerNickname = currentPlayerNickname;
        this.motherNatureIndex = motherNatureIndex;
    }

    public String getMessage() {
        String playersInGame = "Active players:\n";
        for(Player p : activePlayers) {
            playersInGame = playersInGame + p.getNickname() + " ";
        }
        playersInGame = playersInGame + "\n";

        String boardPlayers = "Player boards:\n";
        for(Board b : boards) {
            boardPlayers = boardPlayers +
                new BoardMessage(b.getPlayerNickname(), b.getEntrance(), b.getHall(), b.getProfessors(), b.getTowers()).getMessage();
        }
        boardPlayers = boardPlayers + "\n";

        String drawn = "";
        if(drawnCharacters.size() != 1) {
            drawn = "Characters drawn:\n";
            for(int c : drawnCharacters) {
                if(c != 0) {
                    drawn = drawn + c + " ";
                }
            }
            drawn = drawn + "\n";
        }

        String groups = new IslandGroupsMessage(islandGroups, motherNatureIndex).getMessage() + "\n";

        String currentP = "The current player is: " + currentPlayerNickname + "\n";

        return playersInGame + boardPlayers + drawn + groups + currentP;

    }

}
