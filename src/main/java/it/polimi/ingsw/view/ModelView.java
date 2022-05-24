package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.message.serverToclient.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of the game model.
 */
public class ModelView {

    private String currentPhase;
    private String currentPlayer;
    private Map<String, PlayerColor> players;
    private final Map<String, Board> nicknameToBoard;
    private final Map<String, Integer> nicknameToCoins;
    private ArrayList<IslandGroup> islandGroups;
    private int motherNatureIndex;
    private final Map<Integer, CharacterView> idToCharacter;
    private int activeCharacterID;
    private ArrayList<Cloud> clouds;
    private String winner;

    /**
     * Default constructor.
     */
    public ModelView() {
        this.nicknameToBoard = new HashMap<>();
        this.nicknameToCoins = new HashMap<>();
        this.idToCharacter = new HashMap<>();
    }

    /**
     * @return the current phase.
     */
    public String getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Sets the current phase to the specified one.
     * @param currentPhase to set.
     */
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * @return the current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player to the specified one.
     * @param currentPlayer to set.
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the list of players in the game.
     */
    public Map<String, PlayerColor> getPlayers() {
        return players;
    }

    /**
     * Sets the players in the game to the ones in the specified list.
     * @param players to set.
     */
    public void setPlayers(Map<String, PlayerColor> players) {
        this.players = players;
    }

    /**
     * @return the list of the players' nicknames, sorted by their color's index.
     */
    public ArrayList<String> getPlayersSorted() {
        ArrayList<String> playersSorted = new ArrayList<>();

        // TODO improve this
        playersSorted.add(getPlayerByColor(PlayerColor.BLACK));
        playersSorted.add(getPlayerByColor(PlayerColor.WHITE));

        if(getNumberOfPlayers() > 2) {
            playersSorted.add(getPlayerByColor(PlayerColor.GRAY));
        }

        return playersSorted;
    }

    /**
     * @return the number of players in the game.
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     * @param color the color of the player to find.
     * @return the nickname of the player with the specified color if it exists, null otherwise.
     */
    public String getPlayerByColor(PlayerColor color) {
        for(String name : players.keySet()) {
            if(players.get(name).equals(color)) {
                return name;
            }
        }
        return null;
    }

    /**
     * @param nickname the nickname of the board's owner.
     * @return the board owned by the player with the specified nickname.
     */
    public Board getBoard(String nickname) {
        return nicknameToBoard.get(nickname);
    }

    /**
     * Sets the board of the specified player to the specified one.
     * Both nickname and board are found in the BoardMessage.
     * @param msg the message containing the nickname and board to set.
     */
    public void setBoard(BoardMessage msg) {
        String nickname = msg.getNickname();
        Board board;

        if(nicknameToBoard.containsKey(nickname)) {
            board = nicknameToBoard.get(nickname);
        } else {
            board = new Board();
            nicknameToBoard.put(nickname, board);
        }

        board.setEntrance(msg.getEntrance());
        board.setHall(msg.getHall());
        board.setProfessors(msg.getProfessors());
        board.setTowers(msg.getTowers());
    }

    /**
     * @return the list of island groups of the game.
     */
    public ArrayList<IslandGroup> getIslandGroups() {
        return islandGroups;
    }

    /**
     * Sets the island groups of the game to the ones in the specified list.
     * @param islandGroups the list of island groups to set.
     * @param motherNatureIndex the position of Mother Nature.
     */
    public void setIslandGroups(ArrayList<IslandGroup> islandGroups, int motherNatureIndex) {
        this.islandGroups = islandGroups;
        this.motherNatureIndex = motherNatureIndex;
    }

    /**
     * @return the position of Mother Nature.
     */
    public int getMotherNatureIndex() {
        return motherNatureIndex;
    }

    /**
     * @param islandID the ID of the island to return.
     * @return the island whose ID is the specified one.
     */
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

    /**
     * Sets the island with the ID of the specified one to the specified one.
     * @param island the island to set.
     */
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

    /**
     * Sets the cloud cards to the ones in teh specified list.
     * @param clouds to set.
     */
    public void setClouds(ArrayList<Cloud> clouds) {
        this.clouds = clouds;
    }

    /**
     * @param nickname of the player whose number of coins is returned.
     * @return the number of coins of the player with the specified nickname.
     */
    public int getCoinsByNickname(String nickname) {
        Integer coins = nicknameToCoins.get(nickname);
        if(coins != null) {
            return coins;
        }
        return 0;
    }

    /**
     * Sets the specified number of coins for the specified player.
     * @param msg the CoinMessage containing both the nickname and coins information.
     */
    public void setCoins(CoinMessage msg) {
        String nickname = msg.getNickname();
        int coins = msg.getCoins();

        if(nicknameToCoins.containsKey(nickname)) {
            nicknameToCoins.replace(nickname, coins);
        } else {
            nicknameToCoins.put(nickname, coins);
        }
    }

    /**
     * Sets the drawn characters to the ones specified in the specified CharacterDrawnMessage.
     * @param msg the message containing the drawn characters' information.
     */
    public void setDrawnCharacter(CharacterDrawnMessage msg) {
        CharacterView characterView = new CharacterView(msg.getCharacterID(), msg.getCost(), msg.isUsed(), msg.getStudents(), msg.getBanCards());
        idToCharacter.put(characterView.getCharacterID(), characterView);
    }

    /**
     * Sets the played character to the one specified in the CharacterPlayedMessage passed.
     * @param msg the message containing the played character's information.
     */
    public void setPlayedCharacter(CharacterPlayedMessage msg) {
        this.activeCharacterID = msg.getCharacterID();
        CharacterView characterView = new CharacterView(msg.getCharacterID(), msg.getCost(), msg.isUsed(), msg.getStudents(), msg.getBanCards());
        idToCharacter.replace(characterView.getCharacterID(), characterView);
    }

    /**
     * @return the ID to character mapping.
     */
    public Map<Integer, CharacterView> getIdToCharacter() {
        return new HashMap<>(idToCharacter);
    }

    /**
     * @param characterID the ID of the character to return.
     * @return the character whose ID is the specified one.
     */
    public CharacterView getCharacterViewById(int characterID) {
        return idToCharacter.get(characterID);
    }

    /**
     * @return the ID of the active character.
     */
    public int getActiveCharacterID() {
        return activeCharacterID;
    }

    /**
     * @return the list of cloud cards.
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * @return the nickname of the winner.
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets the winner of the game.
     * @param winner the nickname of the game's winner.
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }
}
