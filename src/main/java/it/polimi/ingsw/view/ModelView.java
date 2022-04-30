package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.serverToclient.Answer;

import java.util.ArrayList;

/**
 * Representation of the game model.
 */
public class ModelView {

    private String currentPhase;
    private String currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Board> boards;
    private ArrayList<IslandGroup> islandGroups;
    private int activeCharacterID;
    private ArrayList<Integer> drawnCharactersID;

    private Answer serverAnswer;





    //TODO

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public ArrayList<IslandGroup> getIslandGroups() {
        return islandGroups;
    }

    public void setIslandGroups(ArrayList<IslandGroup> islandGroups) {
        this.islandGroups = islandGroups;
    }

    public int getActiveCharacterID() {
        return activeCharacterID;
    }

    public void setActiveCharacterID(int activeCharacterID) {
        this.activeCharacterID = activeCharacterID;
    }

    public ArrayList<Integer> getDrawnCharactersID() {
        return drawnCharactersID;
    }

    public void setDrawnCharactersID(ArrayList<Integer> drawnCharactersID) {
        this.drawnCharactersID = drawnCharactersID;
    }





    public Answer getServerAnswer() {
        return serverAnswer;
    }

    public void setServerAnswer(Answer serverAnswer) {
        this.serverAnswer = serverAnswer;
    }
}
