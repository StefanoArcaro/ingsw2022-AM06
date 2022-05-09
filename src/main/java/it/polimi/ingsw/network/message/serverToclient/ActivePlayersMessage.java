package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;

import java.util.List;

/**
 * Message which contains information about the current status of the game.
 */
public class ActivePlayersMessage extends Answer {

    private final List<String> activePlayers;

    public ActivePlayersMessage(List<String> activePlayers) {
        super(MessageType.ACTIVE_PLAYERS_MESSAGE);
        this.activePlayers = activePlayers;
    }

    public String getMessage() {
        String playersInGame = "Active players:\n";
        for(String p : activePlayers) {
            playersInGame = playersInGame + p + " ";
        }
        playersInGame = playersInGame + "\n";

        /*String boardPlayers = "Player boards:\n";
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

        return playersInGame + boardPlayers + drawn + groups + currentP;*/

        return playersInGame;
    }

    public List<String> getActivePlayers() {
        return activePlayers;
    }
}
