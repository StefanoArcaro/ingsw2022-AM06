package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gameBoard.Student;

import java.util.ArrayList;

public class CharacterView {

    private final int characterID;
    private int cost;
    private boolean used;
    private ArrayList<Student> students;
    private int banCards;

    /**
     * Default constructor.
     * @param characterID ID of the character.
     * @param cost cost of the character.
     * @param used whether the character has been used at least once.
     * @param students the list of students on this character.
     * @param banCards the number of ban cards on this character.
     */
    public CharacterView(int characterID, int cost, boolean used, ArrayList<Student> students, int banCards) {
        this.characterID = characterID;
        this.cost = cost;
        this.used = used;
        this.students = students;
        this.banCards = banCards;
    }

    /**
     * @return this character's ID.
     */
    public int getCharacterID() {
        return characterID;
    }

    /**
     * @return this character's cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of this character to the specified one.
     * @param cost to set.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * @return whether this character has been used at least once.
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Sets the 'used' attribute of this character.
     * @param used to set.
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * @return this character's list of students.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Sets this character's list of students to the specified one.
     * @param students to set.
     */
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    /**
     * @return the number of ban cards on this character.
     */
    public int getBanCards() {
        return banCards;
    }

    /**
     * Sets the number of ban cards on this character to the specified one.
     * @param banCards to set.
     */
    public void setBanCards(int banCards) {
        this.banCards = banCards;
    }
}
