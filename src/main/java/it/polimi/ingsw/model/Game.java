package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterID;
import it.polimi.ingsw.model.characters.CharacterInfluenceModifier;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import it.polimi.ingsw.model.phases.Round;

import java.util.ArrayList;

public class Game {

    static int roundNumber = 1; // TODO remove after removing Round class

    private static Game game = null;
    private NumberOfPlayers numberOfPlayers;
    private GameMode gameMode;
    private GameState gameState;
    private final PhaseFactory phaseFactory;
    private Phase currentPhase;
    private final ArrayList<Player> players;
    private Player firstPlayer;
    private Player currentPlayer;
    private final MotherNature motherNature;
    private final ArrayList<IslandGroup> islandGroups;
    private final ArrayList<Cloud> clouds;
    private final ArrayList<Professor> professors;
    private final ArrayList<Character> drawnCharacters;
    private int treasury;

    // TODO check if needed
    private ArrayList<Character> activatedCharacters;

    // TODO remove after removing Round class
    private static Round currentRound; //static attribute to be accessed via class

    /**
     * Private constructor
     */
    private Game() {
        phaseFactory = new PhaseFactory();
        players = new ArrayList<>();
        motherNature = MotherNature.getMotherNature();
        islandGroups = new ArrayList<>();
        clouds = new ArrayList<>();
        professors = new ArrayList<>();
        drawnCharacters = new ArrayList<>();

        // Game begins waiting for players in the Lobby phase
        gameState = GameState.LOBBY_PHASE;

        // TODO remove after fixing tests: this is done in the PreparePhase class
        /*
        for(CreatureColor color : CreatureColor.values()) {
            professors.add(new Professor(color));
        }
        for(int i=1; i<=12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            islandGroups.add(islandGroup);
        }
        */
    }

    /**
     * Game is a Singleton
     * @return singleton instance of the game
     */
    public static Game getGame() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }

    /**
     * Manages the game
     */
    public void startGame() {
        // Phase set before
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
     * @return the player that went first during the previous phase
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Sets the first player who will go first during the next phase
     * @param firstPlayer player to be set
     */
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    /**
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // TODO check if we need a setCurrentPlayer() method

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
    public void addIslandGroup(IslandGroup islandGroup){
        islandGroups.add(islandGroup);
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
                // This should be replaced with the code below
                //Game.getGame().getProfessors();
                //Professor professorToUpdate = Game.getGame().removeProfessor(color);
                //players.get(maxInfluenceIndex).getBoard().winProfessor(professorToUpdate);

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
        // TODO check
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
     * Activates the character passed as a parameter
     * @param characterID ID of the character to activate
     */
    public void activateCharacter(int characterID) {
        // TODO
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

    public void calculateInfluence(int islandGroupIndex) {

    }

    public void calculateInfluence(int islandGroupIndex, CharacterInfluenceModifier activatedCharacter) {

    }







    // TODO remove after removing Round
    /**
     * @return a reference to the current round
     */
    public static Round getCurrentRound() {
        return currentRound;
    } //static method to be able to call it through the class

    public static void setCurrentRound(Round currentRound) {
        Game.currentRound = currentRound;
    }

    /**
     * @return a copy of the characters' set
     */
    public ArrayList<Character> getCharacters() {
        // TODO check if needed (should be useless)
        return null;
    }

    /**
     * Used to keep track of activated characters whose effect can be
     * delayed within the active player's turn
     * @return a copy of the activated characters' list
     */
    public ArrayList<Character> getActivatedCharacters() {
        // TODO check if needed (it shouldn't be)
        return null;
    }

    /**
     * Prepares the "game board" before the actual game begins
     */
    public void prepareGame() {
        // TODO check if needed (PreparePhase's job)
    }

    /**
     * Ends the game based on the ending code defined in gameState
     */
    public void endGame() {
        // TODO check if needed (should be EndgamePhase's job)
    }

    /**
     * Initializes and sets up the island groups on the "board"
     */
    private void initializeIslandGroups() {
        // TODO check if needed (already in PreparePhase)
    }

    /**
     * Randomly places Mother Nature on one of the initial island groups
     * @return the initial position of Mother Nature
     */
    private int placeMotherNature() {
        // TODO check if needed (already in PreparePhase)
        return 0;
    }

    /**
     * Place 2 students of each color (10 total) on the islands, except for
     * the one where Mother Nature was placed and the one on the opposite side
     */
    private void placeInitialStudents() {
        // TODO check if needed (already in PreparePhase)
    }

    // TODO remove after removing Round
    /**
     * @return the number of the current round
     */
    public static int getRoundNumber() {
        return roundNumber;
    }
}
