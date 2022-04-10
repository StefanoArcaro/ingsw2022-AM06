package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterID;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;

import java.util.ArrayList;
import java.util.Map;

public class Game {

    private NumberOfPlayers numberOfPlayers;
    private GameMode gameMode;
    private GameState gameState;
    private final PhaseFactory phaseFactory;
    private Phase currentPhase;
    private final ArrayList<Player> players;
    private ArrayList<Player> playingOrder;
    private Map<Player, Assistant> playerPriority; //(a)
    private Player currentPlayer;
    private int firstPlayerIndex;
    private final Bag bag;
    private final MotherNature motherNature;
    private final ArrayList<IslandGroup> islandGroups;
    private final ArrayList<Cloud> clouds;
    private final ArrayList<Professor> professors;
    private final ArrayList<Character> drawnCharacters;
    private Character activatedCharacter;
    private int treasury;

    /**
     * Default constructor
     */
    public Game() {
        phaseFactory = new PhaseFactory(this);
        players = new ArrayList<>();
        playingOrder = new ArrayList<>();
        bag = new Bag();
        motherNature = new MotherNature();
        islandGroups = new ArrayList<>();
        clouds = new ArrayList<>();
        professors = new ArrayList<>();
        drawnCharacters = new ArrayList<>();
        activatedCharacter = null;

        // Expert version of the game
        gameMode = GameMode.EXPERT;

        // Game begins waiting for players in the Lobby phase
        gameState = GameState.LOBBY_PHASE;

        // Initialize the first phase
        currentPhase = phaseFactory.createPhase(gameState);
    }

    /**
     * @return the chosen number of players for the game
     */
    public NumberOfPlayers getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Sets the number of players for the game to the chosen number, either 2 or 3
     * @param numberOfPlayers to be set
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers == NumberOfPlayers.TWO_PLAYERS.getNum()) {
            this.numberOfPlayers = NumberOfPlayers.TWO_PLAYERS;
        } else if(numberOfPlayers == NumberOfPlayers.THREE_PLAYERS.getNum()) {
            this.numberOfPlayers = NumberOfPlayers.THREE_PLAYERS;
        }
    }

    /**
     * @return the chosen mode for the game
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Sets the game mode to the chosen one
     * @param gameMode mode chosen
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * @return the current state of the game
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the game state to the inputted one
     * @param gameState to set the game's attribute to
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the current phase
     */
    public Phase getCurrentPhase() {
        return currentPhase;
    }

    // Used for testing purposes
    public void setCurrentPhase(Phase phase) {
        this.currentPhase = phase;
    }

    /**
     * @return the list of players
     */
    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Adds a player to the game.
     * Nickname check is done in the LobbyPhase class
     * @param player to be added
     */
    public void addPlayer (Player player) {
        players.add(player);
    }

    /**
     * @return the current playing order
     */
    public ArrayList<Player> getPlayingOrder() {
        return playingOrder;
    }

    /**
     * Sets the playing order to the specified one
     * @param playingOrder to set
     */
    public void setPlayingOrder(ArrayList<Player> playingOrder) {
        this.playingOrder = playingOrder;
    }

    /**
     * (a)
     * @return the association of players' to their assistants played
     */
    public Map<Player, Assistant> getPlayerPriority() {
        return playerPriority;
    }

    /**
     * Sets the player priority (which associates the player to the assistant played)
     * to the specified one
     * @param playerPriority to set
     */
    public void setPlayerPriority(Map<Player, Assistant> playerPriority) {
        this.playerPriority = playerPriority;
    }

    /**
     * @return the player that went first during the previous phase
     */
    public int getFirstPlayerIndex() {
        return firstPlayerIndex;
    }

    /**
     * Sets the first player who will go first during the next phase
     * @param firstPlayerIndex index to set
     */
    public void setFirstPlayerIndex(int firstPlayerIndex) {
        this.firstPlayerIndex = firstPlayerIndex;
    }

    /**
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player to the one passed as a parameter
     * @param player new current player
     */
    public void setCurrentPlayer (Player player) {
        this.currentPlayer = player;
    }

    /**
     * The player corresponding to a certain PlayerColor
     * @param color of the player to return
     * @return player whose color matches with the inputted one
     */
    public Player getPlayerByColor(PlayerColor color) {
        for(Player player : players) {
            if(player.getColor().equals(color)) {
                return player;
            }
        }
        return null;
    }

    /**
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * @return the Mother Nature attribute
     */
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * @return the list of island groups
     */
    public ArrayList<IslandGroup> getIslandGroups() {
        return new ArrayList<>(islandGroups);
    }

    /**
     * Adds an island group to the islandGroups list
     * @param islandGroup to be added
     */
    public void addIslandGroup(IslandGroup islandGroup) {
        islandGroups.add(islandGroup);
    }

    /**
     * Connects the specified island groups in case they need to be connected.
     * If they get connected, the second island group is removed from the island groups list
     * @param indexPresentIslandGroup island group that eventually incorporates the other one
     * @param indexIslandGroupToAdd island group that eventually gets incorporated into the first one
     */
    public void connectIslandGroups(int indexPresentIslandGroup, int indexIslandGroupToAdd) {
        IslandGroup presentIslandGroup = getIslandGroupByIndex(indexPresentIslandGroup);
        IslandGroup islandGroupToAdd = getIslandGroupByIndex(indexIslandGroupToAdd);

        if(presentIslandGroup.connectIslandGroup(islandGroupToAdd)) {
            islandGroups.remove(indexIslandGroupToAdd);
        }
    }

    /**
     * Returns the island group corresponding to the inputted index
     * @param index of the island group to return
     * @return the island group whose index matches the specified one
     */
    public IslandGroup getIslandGroupByIndex(int index) {
        return islandGroups.get(index);
    }


    /**
     * Return the index of an island group
     * @param islandGroup for which the index is being searched
     * @return the index of the inputted island group
     */
    public int getIndexOfIslandGroup(IslandGroup islandGroup) {
        for (int i = 0; i<islandGroups.size(); i++) {
            if(getIslandGroupByIndex(i).equals(islandGroup)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the island that matches the ID passed as a parameter
     * @param islandID ID of the island to return
     * @return the island that matches the inputted ID
     */
    public Island getIslandByID(int islandID) {
        for(IslandGroup islandGroup : islandGroups) {
            for(Island island : islandGroup.getIslands()) {
                if(island.getIslandID() == islandID) {
                    return island;
                }
            }
        }
        return null;
    }

    /**
     * Calculates the influence of all the players on the specified island group.
     * If changes need to be made after calculating it, they are taken care of
     * @param islandGroupIndex index of the island group to calculate the influence for
     * @param activatedCharacter character that eventually modifies the influence calculations
     */
    public void calculateInfluence(int islandGroupIndex, Character activatedCharacter) {
        int influence;
        int maxInfluence = -1;
        boolean draw = false;
        IslandGroup islandGroup = getIslandGroupByIndex(islandGroupIndex);
        Player playerMaxInfluence = null;
        PlayerColor towerColorOnIslandGroup = islandGroup.getConquerorColor();
        Player playerOlderConquerorIslandGroup = null;

        if(islandGroup.getNumberOfBanCardPresent() > 0) {
            islandGroup.removeBanCard();
            Character character= getCharacterByID(5);
            if(character != null) {
                character.addBanCard();
            }
            return;
        }

        if(towerColorOnIslandGroup != null) {
            playerOlderConquerorIslandGroup = getPlayerByColor(towerColorOnIslandGroup);
        }

        for(Player player : players) {
            influence = islandGroup.calculateInfluence(this, player, activatedCharacter);

            if(influence > maxInfluence) {
                maxInfluence = influence;
                playerMaxInfluence = player;
            } else if(influence == maxInfluence) {
                if(!player.equals(playerOlderConquerorIslandGroup)) {
                    draw = true;
                } else {
                    playerMaxInfluence = player;
                }
            }
        }

        if(!draw && playerMaxInfluence != null && !playerMaxInfluence.equals(playerOlderConquerorIslandGroup)) {
            if(playerOlderConquerorIslandGroup != null){
                for(Island island : getIslandGroupByIndex(islandGroupIndex).getIslands()) {
                    island.removeTower(this);
                }
            }

            for(Island island : getIslandGroupByIndex(islandGroupIndex).getIslands()) {
                island.addTower(this, playerMaxInfluence.getColor());
            }

            islandGroups.get(islandGroupIndex).setConquerorColor(playerMaxInfluence.getColor());

            int numberOfIslandGroups = islandGroups.size();

            int indexPreviousIslandGroup = (islandGroupIndex - 1) < 0 ? numberOfIslandGroups - 1 : islandGroupIndex - 1;
            int indexNextIslandGroup = (islandGroupIndex + 1) % numberOfIslandGroups;

            connectIslandGroups(islandGroupIndex, indexPreviousIslandGroup);
            connectIslandGroups(islandGroupIndex, indexNextIslandGroup);
        }
    }

    /**
     * Overloading of @calculateInfluence(int, Character)
     * Called if easy mode
     * @param islandGroupIndex index of the island group to calculate the influence for
     */
    public void calculateInfluence(int islandGroupIndex) {
        int influence;
        int maxInfluence = -1;
        boolean draw = false;
        IslandGroup islandGroup = getIslandGroupByIndex(islandGroupIndex);
        Player playerMaxInfluence = null;
        PlayerColor towerColorOnIslandGroup = islandGroup.getConquerorColor();
        Player playerOlderConquerorIslandGroup = null;

        if(towerColorOnIslandGroup != null) {
            playerOlderConquerorIslandGroup = getPlayerByColor(towerColorOnIslandGroup);
        }

        for(Player player : players) {
            influence = islandGroup.calculateInfluence(player);

            if(influence > maxInfluence) {
                maxInfluence = influence;
                playerMaxInfluence = player;
            } else if(influence == maxInfluence) {
                if(!player.equals(playerOlderConquerorIslandGroup)) {
                    draw = true;
                } else {
                    playerMaxInfluence = player;
                }
            }
        }

        if(!draw && playerMaxInfluence != null && !playerMaxInfluence.equals(playerOlderConquerorIslandGroup)) {
            if(playerOlderConquerorIslandGroup != null){
                for(Island island : getIslandGroupByIndex(islandGroupIndex).getIslands()) {
                    island.removeTower(this);
                }
            }

            for(Island island : getIslandGroupByIndex(islandGroupIndex).getIslands()) {
                island.addTower(this, playerMaxInfluence.getColor());
            }

            islandGroups.get(islandGroupIndex).setConquerorColor(playerMaxInfluence.getColor());

            int numberOfIslandGroups = islandGroups.size();

            int indexPreviousIslandGroup = (islandGroupIndex - 1) < 0 ? numberOfIslandGroups - 1 : islandGroupIndex - 1;
            int indexNextIslandGroup = (islandGroupIndex + 1) % numberOfIslandGroups;

            connectIslandGroups(islandGroupIndex, indexPreviousIslandGroup);
            connectIslandGroups(islandGroupIndex, indexNextIslandGroup);
        }
    }

    /**
     * @return the list of cloud cards
     */
    public ArrayList<Cloud> getClouds() {
        return new ArrayList<>(clouds);
    }

    /**
     * Adds a cloud card to the clouds list
     * @param cloud cloud card to be added
     */
    public void addCloud(Cloud cloud) {
        this.clouds.add(cloud);
    }

    /**
     * Removes a cloud card from the clouds list
     * @param cloud cloud card to be removed
     */
    public void removeCloud(Cloud cloud) {
        this.clouds.remove(cloud);
    }

    /**
     * Returns the cloud that matches the ID passed as a parameter
     * @param idCloud ID of the cloud to return
     * @return the cloud that matches the inputted ID
     */
    public Cloud getCloudByID(int idCloud) {
        for (Cloud cloud : clouds) {
            if(cloud.getCloudID() == idCloud) {
                return cloud;
            }
        }
        return null;
    }

    /**
     * @return the list of professors
     */
    public ArrayList<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }

    /**
     * Adds a professor to the professors list
     * @param professor to be added
     */
    public void addProfessor(Professor professor) {
        this.professors.add(professor);
    }

    /**
     * Updates the owner of each professor
     */
    public void updateProfessors() {
        for(CreatureColor color: CreatureColor.values()) {
            updateProfessor(color);
        }
    }

    /**
     * Updates the owner of a certain professor
     * @param color of the professor whose owner might be in need of an update
     */
    public void updateProfessor(CreatureColor color) {
        int oldOwnerInfluence, maxInfluence = 0, maxInfluenceIndex = -1;
        Player owner = null;

        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getBoard().containsProfessor(color)) {
                owner = players.get(i);
                oldOwnerInfluence = owner.getBoard().getHall().getTableByColor(color).getLength();
                maxInfluence = oldOwnerInfluence;
                maxInfluenceIndex = i;
                break;
            }
        }

        for(int i = 0; i < players.size(); i++) {
            int tempInfluence = players.get(i).getBoard().getHall().getTableByColor(color).getLength();
            boolean characterEffectForCurrentPlayer = false;
            if(currentPhase.getActivatedCharacter().getCharacterID() != CharacterID.CHARACTER_NONE.getID()) {
                characterEffectForCurrentPlayer =
                        players.get(i).equals(currentPlayer) &&
                        currentPhase.getActivatedCharacter().getCharacterID() == CharacterID.CHARACTER_TWO.getID();
            }

            if(tempInfluence > maxInfluence || (characterEffectForCurrentPlayer && tempInfluence >= maxInfluence)) {
                maxInfluence = tempInfluence;
                maxInfluenceIndex = i;
            }
        }

        if(maxInfluence > 0) {
            if(owner != null) {
                if(!players.get(maxInfluenceIndex).equals(owner)) {
                    Professor professorToUpdate = owner.getBoard().loseProfessorByColor(color);
                    players.get(maxInfluenceIndex).getBoard().winProfessor(professorToUpdate);
                }
            } else if(maxInfluenceIndex != -1) {
                Professor professorToUpdate = removeProfessor(color);
                players.get(maxInfluenceIndex).getBoard().winProfessor(professorToUpdate);
            }
        }
    }

    /**
     * Removes a professor from the list: called when a player wins such professor
     * @param color of the professor to remove
     * @return the professor removed
     */
    public Professor removeProfessor(CreatureColor color) {
        for(Professor professor : professors) {
            if(professor.getColor().equals(color)) {
                professors.remove(professor);
                return professor;
            }
        }
        return null;
    }

    /**
     * @return the list of characters drawn for the game
     */
    public ArrayList<Character> getDrawnCharacters() {
        return new ArrayList<>(drawnCharacters);
    }

    /**
     * Adds a character to the drawnCharacters list
     * @param drawnCharacter character to be added
     */
    public void addDrawnCharacter(Character drawnCharacter) {
        this.drawnCharacters.add(drawnCharacter);
    }

    /**
     * @return the activated character
     */
    public Character getActivatedCharacter() {
        return activatedCharacter;
    }


    /**
     * Sets the active character to the specified one
     * @param characterID ID of the character to activate in the drawn characters list
     */
    public void setActivatedCharacter(int characterID) {
        activatedCharacter = getCharacterByID(characterID); // TODO: input
    }

    /**
     * Activates the character passed as a parameter
     * @param characterID ID of the character to activate
     */
    private void playCharacter(int characterID) throws NoAvailableBanCardsException, OutOfBoundException,
            NoAvailableColorException, NotEnoughMoneyException, TooManyIterationsException {

        // TODO fix this
        // TODO implement without checking, cast and if failure then exception (not action phase)

        if(gameState.getCode() > 2 && gameState.getCode() < 6 && activatedCharacter != null) {
            ((ActionPhase)currentPhase).playCharacter(activatedCharacter);
        }
    }

    /**
     * Return the character chosen by its ID if it was initially extracted
     * @param characterID ID of the character to return
     * @return the character corresponding to the id
     */
    private Character getCharacterByID(int characterID) {
        for (Character character : drawnCharacters) {
            if(character.getCharacterID() == characterID){
                return character;
            }
        }
        return null;
    }

    /**
     * @return the number of coins in the treasury
     */
    public int getTreasury() {
        return treasury;
    }

    /**
     * Sets the number of coins in the treasury
     * @param numberOfCoins to be put in the treasury
     */
    public void setTreasury(int numberOfCoins) {
        this.treasury = numberOfCoins;
    }
}
