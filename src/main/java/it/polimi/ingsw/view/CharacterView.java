package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gameBoard.Student;

import java.util.ArrayList;

public class CharacterView {

    private final int characterID;
    private final int cost;
    private final boolean used;
    private final ArrayList<Student> students;
    private final int banCards;

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
     * @return whether this character has been used at least once.
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * @return this character's list of students.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * @return the number of ban cards on this character.
     */
    public int getBanCards() {
        return banCards;
    }

}
