package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
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

    protected ArrayList<Student> students;

    protected CreatureColor colorNoPoints;
    protected boolean towerCounter;
    protected int extraPoints;

    /**
     * Modify the game setup in order to use this character.
     */
    public void initialPreparation() {}

    public abstract void effect();

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

    public int getToDoNow() {
        return toDoNow;
    }

    public int getNumberOfBanCards() {
        return numberOfBanCards;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
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

    public CreatureColor getColorNoPoints() {
        return colorNoPoints;
    }
}
