package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.NoAvailableBanCardsException;
import it.polimi.ingsw.exceptions.NoAvailableColorException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Student;

import java.util.ArrayList;

/**
 * Defines the interface of the object to be created by the factory
 */
public abstract class Character {

    protected int characterID;
    protected Game game;
    protected int cost;
    protected boolean used;
    protected int numberOfStudents;
    protected int numberOfBanCards;
    protected int toDoNow;
    protected int numberOfIterations;

    protected ArrayList<Student> students;

    protected CreatureColor colorNoPoints;
    protected boolean towerCounter;
    protected int extraPoints;

    protected int moreSteps;

    /**
     * Modify the game setup in order to use this character.
     */
    public void initialPreparation() {}

    /**
     * Abstract method which represents the effect of the character
     */
    public abstract void effect() throws NoAvailableBanCardsException, OutOfBoundException, NoAvailableColorException;

    /**
     * Return the ID of the character.
     * @return int which represents the character ID
     */
    public int getCharacterID() {
        return characterID;
    }

    /**
     * Return a boolean that specifies whether the character has been used during the game
     * @return boolean which is true only if the character has been used
     */
    private boolean isUsed() {
        return used;
    }

    /**
     * The character has been used.
     */
    public void setUsed(){
        used = true;
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Return the cost of the character, increased by one in case of use
     * @return int which represents the cost for using the character
     */
    public int getCost() {
        if (isUsed()) { return cost + 1; }
        return cost;
    }

    /**
     * @return the number of ban cards on the card
     */
    public int getNumberOfBanCards() {
        return numberOfBanCards;
    }

    /**
     * Increases number of ban cards
     */
    public void addBanCard() {
        numberOfBanCards += 1;
    }

    /**
     * Decreases number of ban cards
     */
    public void removeBanCard() {
        numberOfBanCards -= 1;
    }

    /**
     * @return the number of possible applications of the effect during a turn
     */
    public int getToDoNow() {
        return toDoNow;
    }

    /**
     * @return the number of times this card's effect was applied during the turn
     */
    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    /**
     * Sets the number of times this card's effect was applied during the turn
     * @param numberOfIterations number of times this card's effect was applied during the turn
     */
    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    /**
     * Increases the number of times this card's effect was applied during the turn
     */
    public void increaseNumberOfIteration() {
        numberOfIterations += 1;
    }

    /**
     * @return number of steps added
     */
   public int getMoreSteps() {
       return moreSteps;
   }
    /**
     * @return extra points given by the character effect
     */
    public int getExtraPoints() {
        return extraPoints;
    }

    /**
     * @return whether the towers affect the influence calculation or not
     */
    public boolean getTowerCounter(){
        return towerCounter;
    }

    /**
     * Set a color that will not be considered for the influence calculation
     * @param colorNoPoints the color to ignore
     */
    public void setColorNoPoints(CreatureColor colorNoPoints) {
        this.colorNoPoints = colorNoPoints;
    }

    /**
     * @return the color that will not be considered for the influence calculation
     */
    public CreatureColor getColorNoPoints() {
        return colorNoPoints;
    }
}
