package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Cloud {

    private boolean isEmpty;
    private ArrayList<Student> students;

    /**
     * Default constructor
     */
    public Cloud() {
        isEmpty = true;
        // TODO initialize arraylist to an empty one
    }

    /**
     * Fills the cloud card with the correct number of students
     * based on the chosen number of players for the game
     * @param bag that contains all the students that have yet to be drawn
     */
    public void fill(Bag bag) {

    }

    /**
     * Empties the cloud card's students' list
     * @return the ArrayList of the students that were on the cloud card
     */
    public ArrayList<Student> empty() {
        // TODO
        return null;
    }
}
