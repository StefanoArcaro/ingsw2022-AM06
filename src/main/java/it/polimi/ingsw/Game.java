package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Set;

public class Game {

    static int roundNumber = 1;

    private static Game game = null;
    private NumberOfPlayers chosenNumberOfPlayers;
    private GameMode chosenGameMode;
    private GameState gameState;
    private Round currentRound;
    private Player firstPlayer;
    private ArrayList<Player> players;
    private ArrayList<Cloud> clouds;
    private Set<Professor> professors;
    private CircularList<IslandGroup> islandGroups;
    private Set<Character> characters; // TODO change to our Character class
    private ArrayList<Character> drawnCharacters; // TODO this as well
    private ArrayList<Character> activatedCharacters;
    private ArrayList<Coin> treasury;

    /**
     * Private constructor
     */
    private Game() {}

    /**
     * Singleton pattern for the Game class
     * @return singleton instance of the game
     */
    public static Game getGame() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }

    /**
     * @return the number of the current round
     */
    public static int getRoundNumber() {
        return roundNumber;
    }

    /**
     * @return the chosen number of players for the game
     */
    public NumberOfPlayers getNumberOfPlayers() {
        return chosenNumberOfPlayers;
    }

    /**
     * @return the chosen mode for the game
     */
    public GameMode getGameMode() {
        return chosenGameMode;
    }

    /**
     * @return the current state of the game
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @return a reference to the current round
     */
    public Round getCurrentRound() {
        return currentRound;
    }

    /**
     * @return the player that played first during the previous round
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * @return a copy of the players' list
     */
    public ArrayList<Player> getPlayers() {
        // TODO
        return null;
    }

    /**
     * @return a copy of the cloud cards' list
     */
    public ArrayList<Cloud> getClouds() {
        // TODO
        return null;
    }

    /**
     * @return a copy of the professors' set
     */
    public Set<Professor> getProfessors() {
        // TODO
        return null;
    }

    /**
     * @return a copy of the island groups' circular list
     */
    public CircularList<IslandGroup> getIslandGroups() {
        // TODO
        return null;
    }

    /**
     * @return a copy of the characters' set
     */
    public Set<Character> getCharacters() {
        // TODO
        return null;
    }

    /**
     * @return a copy of the list of characters drawn for the game
     */
    public ArrayList<Character> getDrawnCharacters() {
        // TODO
        return null;
    }

    /**
     * Used to keep track of activated characters whose effect can be
     * delayed within the active player's turn
     * @return a copy of the activated characters' list
     */
    public ArrayList<Character> getActivatedCharacters() {
        // TODO
        return null;
    }

    /**
     * @return a copy of the list of coins available on the "board"
     */
    public ArrayList<Coin> getTreasury() {
        // TODO
        return null;
    }

    /**
     * Prepares the "game board" before the actual game begins
     */
    public void prepareGame() {

    }

    /**
     * Manages the game
     */
    public void startGame() {

    }

    /**
     * Ends the game based on the ending code defined in gameState
     */
    public void endGame() {

    }

    /**
     * Determines if a certain nickname has already been chosen by another player
     * @param nickname to check
     * @return whether the nickname has alreasy been chosen
     */
    public boolean isNickNameTaken(String nickname) {
        // TODO
        return false;
    }

    /**
     * Returns the player having the inputted nickname
     * @param nickname to check
     * @return the player whose nickname matches the parameter passed
     */
    public Player getPlayerByNickname(String nickname) {
        // TODO
        return null;
    }

    /**
     * Initializes and sets up the island groups on the "board"
     */
    private void initializeIslandGroups() {

    }

    /**
     * Randomly places Mother Nature on one of the initial island groups
     * @return the initial position of Mother Nature
     */
    private int placeMotherNature() {
        // TODO
        return 0;
    }

    /**
     * Place 2 students of each color (10 total) on the islands, except for
     * the one where Mother Nature was placed and the one on the opposite side
     */
    private void placeInitialStudents() {

    }
}
