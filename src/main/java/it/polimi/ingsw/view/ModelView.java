package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.serverToclient.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of the game model.
 */
public class ModelView {

    private String currentPhase;
    private String currentPlayer;
    private List<String> players;
    private Map<String, Board> nicknameToBoard;
    private Map<String, Integer> nicknameToCoins;
    private ArrayList<IslandGroup> islandGroups;
    private int motherNatureIndex;
    private Map<Integer, CharacterView> idToCharacter;
    private int activeCharacterID;
    private ArrayList<Cloud> clouds;
    private String winner;

    private Answer serverAnswer;


    public ModelView() {
        this.nicknameToBoard = new HashMap<>();
        this.nicknameToCoins = new HashMap<>();
        this.idToCharacter = new HashMap<>();
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    //todo
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public Board getBoard(String nickname) {
        return nicknameToBoard.get(nickname);
    }

    public void setBoard(BoardMessage msg) {
        String nickname = msg.getNickname();
        Board board;

        if(nicknameToBoard.containsKey(nickname)) {
            board = nicknameToBoard.get(nickname);
        } else {
            board = new Board(nickname);
            nicknameToBoard.put(nickname, board);
        }

        board.setEntrance(msg.getEntrance());
        board.setHall(msg.getHall());
        board.setProfessors(msg.getProfessors());
        board.setTowers(msg.getTowers());
    }

    public ArrayList<IslandGroup> getIslandGroups() {
        return islandGroups;
    }

    public void setIslandGroups(ArrayList<IslandGroup> islandGroups, int motherNatureIndex) {
        this.islandGroups = islandGroups;
        this.motherNatureIndex = motherNatureIndex;
    }

    public int getMotherNatureIndex() {
        return motherNatureIndex;
    }

    public Island getIsland(int islandID) {
        for(IslandGroup iG : islandGroups) {
            for(Island i : iG.getIslands()) {
                if(i.getIslandID() == islandID) {
                    return i;
                }
            }
        }
        return null;
    }

    public void setIsland(Island island) {
        int islandID = island.getIslandID();

        for(IslandGroup islandGroup : islandGroups) {
            for(Island i : islandGroup.getIslands()) {
                if(i.getIslandID() == islandID) {
                    i.setStudents(island.getStudents());
                    i.setTower(island.getTower());
                    return;
                }
            }
        }
    }

    public void setClouds(ArrayList<Cloud> clouds) {
        this.clouds = clouds;
    }

    public int getCoinsByNickname(String nickname) {
        return nicknameToCoins.get(nickname);
    }

    public void setCoins(CoinMessage msg) {
        String nickname = msg.getNickname();
        int coins = msg.getCoins();

        if(nicknameToCoins.containsKey(nickname)) {
            nicknameToCoins.replace(nickname, coins);
        } else {
            nicknameToCoins.put(nickname, coins);
        }
    }



    public void setDrawnCharacter(CharacterDrawnMessage msg) {
        CharacterView characterView = new CharacterView(msg.getCharacterID(), msg.getCost(), msg.isUsed(), msg.getStudents(), msg.getBanCards());
        idToCharacter.put(characterView.getCharacterID(), characterView);
    }

    public void setPlayedCharacter(CharacterPlayedMessage msg) {
        this.activeCharacterID = msg.getCharacterID();
        CharacterView characterView = new CharacterView(msg.getCharacterID(), msg.getCost(), msg.isUsed(), msg.getStudents(), msg.getBanCards());
        idToCharacter.replace(characterView.getCharacterID(), characterView);
    }

    public Map<Integer, CharacterView> getIdToCharacter() {
        return new HashMap<>(idToCharacter); //todo
    }

    public int getActiveCharacterID() {
        return activeCharacterID;
    }

    public CharacterView getCharacterViewById(int characterID) {
        return idToCharacter.get(characterID);
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }

    public void setServerAnswer(Answer serverAnswer) {
        this.serverAnswer = serverAnswer;
    }
}
