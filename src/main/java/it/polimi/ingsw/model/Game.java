package it.polimi.ingsw.model;

import it.polimi.ingsw.listeners.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.VirtualView;


import java.beans.PropertyChangeSupport;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class Game {

    private static final int MAX_NUMBER_ISLAND_GROUPS = 3;

    private NumberOfPlayers numberOfPlayers;
    private GameMode gameMode;
    private GameState gameState;
    private Phase currentPhase;
    private boolean skipPickCloudPhase;
    private final ArrayList<Player> players;
    private ArrayList<Player> playingOrder;
    private Map<Player, Assistant> playerPriority;
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

    // Listeners
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    /**
     * Default constructor.
     */
    public Game() {
        PhaseFactory phaseFactory = new PhaseFactory(this);
        skipPickCloudPhase = false;
        players = new ArrayList<>();
        playingOrder = new ArrayList<>();
        bag = new Bag();
        motherNature = new MotherNature();
        islandGroups = new ArrayList<>();
        clouds = new ArrayList<>();
        professors = new ArrayList<>();
        drawnCharacters = new ArrayList<>();

        // Game begins waiting for players in the Lobby phase
        gameState = GameState.LOBBY_PHASE;

        // Initialize the first phase
        currentPhase = phaseFactory.createPhase(gameState);
    }

    /**
     * Creates the listeners to the model.
     * @param clientView the virtual client on the server.
     */
    public void createListeners(VirtualView clientView){
        listeners.addPropertyChangeListener(Constants.PLAYER_LISTENER, new PlayerListener(clientView));
        listeners.addPropertyChangeListener(Constants.PHASE_LISTENER, new PhaseListener(clientView));
        listeners.addPropertyChangeListener(Constants.ASSISTANT_LISTENER, new AssistantListener(clientView));
        listeners.addPropertyChangeListener(Constants.ISLAND_LISTENER, new IslandListener(clientView));
        listeners.addPropertyChangeListener(Constants.ISLAND_GROUPS_LISTENER, new IslandGroupListener(clientView));
        listeners.addPropertyChangeListener(Constants.BOARD_LISTENER, new BoardListener(clientView));
        listeners.addPropertyChangeListener(Constants.COIN_LISTENER, new BoardListener(clientView));
        listeners.addPropertyChangeListener(Constants.CLOUD_LISTENER, new CloudListener(clientView));
        listeners.addPropertyChangeListener(Constants.CHARACTER_LISTENER, new CharacterListener(clientView));
        listeners.addPropertyChangeListener(Constants.WIN_LISTENER, new WinListener(clientView));
    }

    /**
     * @return the listeners to the model.
     */
    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    /**
     * @return the chosen number of players for the game.
     */
    public NumberOfPlayers getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Sets the number of players for the game to the chosen number, either 2 or 3.
     * @param numberOfPlayers to be set.
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers == NumberOfPlayers.TWO_PLAYERS.getNum()) {
            this.numberOfPlayers = NumberOfPlayers.TWO_PLAYERS;
        } else if(numberOfPlayers == NumberOfPlayers.THREE_PLAYERS.getNum()) {
            this.numberOfPlayers = NumberOfPlayers.THREE_PLAYERS;
        }
    }

    /**
     * @return the chosen mode for the game.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Sets the game mode to the chosen one.
     * @param gameMode mode chosen.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * @return the current state of the game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the game state to the inputted one.
     * @param gameState to set the game's attribute to.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the current phase.
     */
    public Phase getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Sets the current phase of the game to the specified one.
     * @param phase to set.
     */
    public void setCurrentPhase(Phase phase) {
        this.currentPhase = phase;
        //listeners.firePropertyChange(Constants.PHASE_LISTENER, null, new AbstractMap.SimpleEntry<>(gameState, gameMode));
    }

    /**
     * @return whether to skip the PickCloudPhase or not.
     */
    public boolean getSkipPickCloudPhase() {
        return skipPickCloudPhase;
    }

    /**
     * @param skipPickCloudPhase value to set.
     */
    public void setSkipPickCloudPhase(boolean skipPickCloudPhase) {
        this.skipPickCloudPhase = skipPickCloudPhase;
    }

    /**
     * @return the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Adds a player to the game.
     * Nickname check is done in the LobbyPhase class.
     * @param player to be added.
     */
    public void addPlayer (Player player) {
        players.add(player);
    }

    /**
     * @return the current playing order.
     */
    public ArrayList<Player> getPlayingOrder() {
        return playingOrder;
    }

    /**
     * Sets the playing order to the specified one.
     * @param playingOrder to set.
     */
    public void setPlayingOrder(ArrayList<Player> playingOrder) {
        this.playingOrder = playingOrder;
    }

    /**
     * @return the association of players' to their assistants played.
     */
    public Map<Player, Assistant> getPlayerPriority() {
        return playerPriority;
    }

    /**
     * Sets the player priority (which associates the player to the assistant played)
     * to the specified one.
     * @param playerPriority to set.
     */
    public void setPlayerPriority(Map<Player, Assistant> playerPriority) {
        this.playerPriority = playerPriority;
    }

    /**
     * @return the player that went first during the previous phase.
     */
    public int getFirstPlayerIndex() {
        return firstPlayerIndex;
    }

    /**
     * Sets the first player who will go first during the next phase.
     * @param firstPlayerIndex index to set.
     */
    public void setFirstPlayerIndex(int firstPlayerIndex) {
        this.firstPlayerIndex = firstPlayerIndex;
    }

    /**
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player to the one passed as a parameter.
     * @param player new current player.
     */
    public void setCurrentPlayer (Player player) {
        this.currentPlayer = player;
        //listeners.firePropertyChange(Constants.PLAYER_LISTENER, null, player.getNickname());
    }

    /**
     * @return the player that comes after the current player.
     */
    public Player getNextPlayer() {
        return players.get((players.indexOf(currentPlayer) + 1) % numberOfPlayers.getNum());
    }


    /**
     * The player corresponding to a certain PlayerColor.
     * @param color of the player to return.
     * @return player whose color matches with the inputted one.
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
     * @return the bag.
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * @return the Mother Nature attribute.
     */
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * @return the list of island groups.
     */
    public ArrayList<IslandGroup> getIslandGroups() {
        return new ArrayList<>(islandGroups);
    }

    /**
     * Adds an island group to the islandGroups list.
     * @param islandGroup to be added.
     */
    public void addIslandGroup(IslandGroup islandGroup) {
        islandGroups.add(islandGroup);
    }

    /**
     * Check if the game has to end because of the archipelagos that have formed.
     * @return if the number of island groups is sufficient to stop the game.
     */
    public boolean checkEndDueToIslandGroup() {
        return islandGroups.size() == MAX_NUMBER_ISLAND_GROUPS;
    }

    /**
     * Connects the specified island groups in case they need to be connected.
     * If they get connected, the second island group is removed from the island groups list.
     * @param presentIslandGroup island group that eventually incorporates the other one.
     * @param islandGroupToAdd island group that eventually gets incorporated into the first one.
     */
    public void connectIslandGroups(IslandGroup presentIslandGroup, IslandGroup islandGroupToAdd) {
        if(presentIslandGroup.connectIslandGroup(islandGroupToAdd)) {
            islandGroups.remove(islandGroupToAdd);
        }
    }

    /**
     * Returns the island group corresponding to the inputted index.
     * @param index of the island group to return.
     * @return the island group whose index matches the specified one.
     */
    public IslandGroup getIslandGroupByIndex(int index) {
        return islandGroups.get(index);
    }


    /**
     * Return the index of an island group.
     * @param islandGroup for which the index is being searched.
     * @return the index of the inputted island group.
     */
    public int getIndexOfIslandGroup(IslandGroup islandGroup) {
        return islandGroups.indexOf(islandGroup);
    }

    /**
     * Returns the island that matches the ID passed as a parameter.
     * @param islandID ID of the island to return.
     * @return the island that matches the inputted ID.
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
     * If changes need to be made after calculating it, they are taken care of.
     * @param islandGroupIndex index of the island group to calculate the influence for.
     */
    public void calculateInfluence(int islandGroupIndex) {
        int influence;
        int maxInfluence = -1;
        boolean draw = false;
        IslandGroup islandGroup = getIslandGroupByIndex(islandGroupIndex);
        Player playerMaxInfluence = null;
        PlayerColor towerColorOnIslandGroup = islandGroup.getConquerorColor();
        Player playerOlderConquerorIslandGroup = null;

        if(isBanCardPresent(islandGroup)) {
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

        islandGroupConquered(islandGroupIndex, draw, playerMaxInfluence, playerOlderConquerorIslandGroup);
    }

    /**
     * Checks if there are any ban cards on the island group, in case the influence is not calculated.
     * @param islandGroup group of islands on which to check for the presence of ban cards.
     * @return whether there are any ban cards on the island group.
     */
    private boolean isBanCardPresent(IslandGroup islandGroup) {
        if(islandGroup.getNumberOfBanCardPresent() > 0) {
            islandGroup.removeBanCard();
            //listeners.firePropertyChange(Constants.ISLAND_GROUPS_LISTENER, null, new AbstractMap.SimpleEntry<>(islandGroups, motherNature));
            Character character= getCharacterByID(5);
            if(character != null) {
                character.addBanCard();
            }
            return true;
        }
        return false;
    }

    /**
     * Check if the given island group has been conquered.
     * @param islandGroupIndex id of the island group that is eventually conquered.
     * @param draw indicates whether there is a tie in influence in the island group.
     * @param playerMaxInfluence player with max influence on the island group.
     * @param playerOlderConquerorIslandGroup player who had previously conquered the island group.
     */
    private void islandGroupConquered(int islandGroupIndex, boolean draw, Player playerMaxInfluence, Player playerOlderConquerorIslandGroup) {
        if(!draw && playerMaxInfluence != null && !playerMaxInfluence.equals(playerOlderConquerorIslandGroup)) {
            if(playerOlderConquerorIslandGroup != null){
                for(Island island : getIslandGroupByIndex(islandGroupIndex).getIslands()) {
                    island.removeTower(this);
                }
                listeners.firePropertyChange(Constants.BOARD_LISTENER, null, playerOlderConquerorIslandGroup.getBoard());
            }

            for(Island island : getIslandGroupByIndex(islandGroupIndex).getIslands()) {
                island.addTower(this, playerMaxInfluence.getColor());
            }
            listeners.firePropertyChange(Constants.BOARD_LISTENER, null, playerMaxInfluence.getBoard());

            islandGroups.get(islandGroupIndex).setConquerorColor(playerMaxInfluence.getColor());

            int numberOfIslandGroups = islandGroups.size();

            int indexPreviousIslandGroup = (islandGroupIndex - 1) < 0 ? numberOfIslandGroups - 1 : islandGroupIndex - 1;
            int indexNextIslandGroup = (islandGroupIndex + 1) % numberOfIslandGroups;

            IslandGroup islandGroup = getIslandGroupByIndex(islandGroupIndex);
            IslandGroup previousIslandGroup = getIslandGroupByIndex(indexPreviousIslandGroup);
            IslandGroup nextIslandGroup = getIslandGroupByIndex(indexNextIslandGroup);

            if(islandGroup != null){
                if(previousIslandGroup != null) {
                    connectIslandGroups(islandGroup, previousIslandGroup);
                }
                if(nextIslandGroup != null) {
                    connectIslandGroups(islandGroup, nextIslandGroup);
                }
            }

            //listeners.firePropertyChange(Constants.ISLAND_GROUPS_LISTENER, null, new AbstractMap.SimpleEntry<>(islandGroups, motherNature));
        }
        int motherNatureIndex = getIndexOfIslandGroup(motherNature.getCurrentIslandGroup());
        listeners.firePropertyChange(Constants.ISLAND_GROUPS_LISTENER, null, new AbstractMap.SimpleEntry<>(islandGroups, motherNatureIndex));
    }

    /**
     * @return the list of cloud cards.
     */
    public ArrayList<Cloud> getClouds() {
        return new ArrayList<>(clouds);
    }

    /**
     * Adds a cloud card to the clouds list.
     * @param cloud cloud card to be added.
     */
    public void addCloud(Cloud cloud) {
        this.clouds.add(cloud);
    }

    /**
     * Removes a cloud card from the clouds list.
     * @param cloud cloud card to be removed.
     */
    public void removeCloud(Cloud cloud) {
        this.clouds.remove(cloud);
    }

    /**
     * Returns the cloud that matches the ID passed as a parameter.
     * @param idCloud ID of the cloud to return.
     * @return the cloud that matches the inputted ID.
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
     * @return the list of professors.
     */
    public ArrayList<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }

    /**
     * Adds a professor to the professors list.
     * @param professor to be added.
     */
    public void addProfessor(Professor professor) {
        this.professors.add(professor);
    }

    /**
     * Updates the owner of each professor.
     */
    public void updateProfessors() {
        for(CreatureColor color: CreatureColor.values()) {
            updateProfessor(color);
        }
    }

    /**
     * Updates the owner of a certain professor.
     * @param color of the professor whose owner might be in need of an update.
     */
    public void updateProfessor(CreatureColor color) {
        int oldOwnerInfluence, maxInfluence = 0, maxInfluenceIndex = -1;
        Player owner = null;

        // Find influence over the specified color of player that has the professor
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getBoard().containsProfessor(color)) {
                owner = players.get(i);
                oldOwnerInfluence = owner.getBoard().getHall().getTableByColor(color).getLength();
                maxInfluence = oldOwnerInfluence;
                maxInfluenceIndex = i;
                break;
            }
        }

        // Find player with max influence over the specified color
        for(int i = 0; i < players.size(); i++) {
            int tempInfluence = players.get(i).getBoard().getHall().getTableByColor(color).getLength();
            boolean characterEffectForCurrentPlayer =
                        players.get(i).equals(currentPlayer) &&
                        activatedCharacter.getCharacterID() == CharacterID.CHARACTER_TWO.getID();

            if(tempInfluence > maxInfluence || (characterEffectForCurrentPlayer && tempInfluence >= maxInfluence)) {
                maxInfluence = tempInfluence;
                maxInfluenceIndex = i;
            }
        }

        // Eventually move the professor to another player
        moveProfessor(color, owner, maxInfluence, maxInfluenceIndex);
    }

    /**
     * Moves the professor of the specified color to another player's board, if necessary.
     * @param color of the professor to update.
     * @param owner old owner of the professor.
     * @param maxInfluence influence of the potential new owner.
     * @param maxInfluenceIndex position of the potential new owner in the list of players.
     */
    private void moveProfessor(CreatureColor color, Player owner, int maxInfluence, int maxInfluenceIndex) {
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
     * Removes a professor from the list: called when a player wins such professor.
     * @param color of the professor to remove.
     * @return the professor removed.
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
     * @return the list of characters drawn for the game.
     */
    public ArrayList<Character> getDrawnCharacters() {
        return new ArrayList<>(drawnCharacters);
    }

    /**
     * Adds a character to the drawnCharacters list.
     * @param drawnCharacter character to be added.
     */
    public void addDrawnCharacter(Character drawnCharacter) {
        drawnCharacters.add(drawnCharacter);
    }

    /**
     * @return the activated character.
     */
    public Character getActivatedCharacter() {
        return activatedCharacter;
    }

    /**
     * Sets the active character to the specified one.
     * @param character to activate in the drawn characters list.
     */
    public void setActivatedCharacter(Character character) {
        activatedCharacter = character;
    }

    /**
     * Return the character chosen by its ID if it was initially extracted.
     * @param characterID ID of the character to return.
     * @return the character corresponding to the id.
     */
    public Character getCharacterByID(int characterID) {
        for(Character character : drawnCharacters) {
            if(character.getCharacterID() == characterID) {
                return character;
            }
        }
        return null;
    }

    /**
     * @return the number of coins in the treasury.
     */
    public int getTreasury() {
        return treasury;
    }

    /**
     * Sets the number of coins in the treasury.
     * @param numberOfCoins to be put in the treasury.
     */
    public void setTreasury(int numberOfCoins) {
        this.treasury = numberOfCoins;
    }
}
