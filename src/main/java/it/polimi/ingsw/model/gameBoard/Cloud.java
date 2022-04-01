package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Cloud {

    private final int cloudID;
    private final ArrayList<Student> students;

    /**
     * Default constructor
     */
    public Cloud(int cloudID) {
        this.cloudID = cloudID;
        students = new ArrayList<>();
    }

    /**
     * @return the cloud card's ID
     */
    public int getCloudID() {
        return cloudID;
    }

    /**
     * @return the students on the clout card
     */
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Fills the cloud card with the correct number of students
     * based on the chosen number of players for the game
     * @param bag that contains all the students that have yet to be drawn
     */
    public void fill(Bag bag, int numberOfStudents) {
        for(int i = 0; i < numberOfStudents + 1; i++) {
            students.add(bag.drawStudent());
        }
    }

    /**
     * Empties the cloud card's students' list
     * @return the ArrayList of the students that were on the cloud card
     */
    public ArrayList<Student> empty() {
        ArrayList<Student> result = new ArrayList<>(students);
        students.clear();
        return result;
    }
}
