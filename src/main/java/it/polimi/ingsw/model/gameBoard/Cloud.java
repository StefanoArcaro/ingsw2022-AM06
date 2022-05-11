package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Cloud {

    private final int cloudID;
    private final ArrayList<Student> students;

    /**
     * Default constructor.
     */
    public Cloud(int cloudID) {
        this.cloudID = cloudID;
        students = new ArrayList<>();
    }

    /**
     * @return the cloud card's ID.
     */
    public int getCloudID() {
        return cloudID;
    }

    /**
     * @return the students on the cloud card.
     */
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Fills the cloud card with the correct number of students.
     * based on the chosen number of players for the game.
     * @param bag that contains all the students that have yet to be drawn.
     * @return whether all the clouds were filled or not.
     */
    public boolean fill(Bag bag, int numberOfStudents) {
        Student student;

        for(int i = 0; i < numberOfStudents + 1; i++) {
            student = bag.drawStudent();

            if(student != null) {
                students.add(student);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Empties the cloud card's students' list.
     * @return the ArrayList of the students that were on the cloud card.
     */
    public ArrayList<Student> empty() {
        ArrayList<Student> result = new ArrayList<>(students);
        students.clear();
        return result;
    }

    /**
     * @return whether the cloud is empty.
     */
    public boolean isEmpty() {
        return students.isEmpty();
    }
}
