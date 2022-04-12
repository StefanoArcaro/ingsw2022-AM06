package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.enumerations.CreatureColor;

import java.util.ArrayList;
import java.util.Collections;

public class Bag implements StudentDestination {

    private static final int MIN_BAG_SIZE = 0;

    private final ArrayList<Student> students;

    /**
     * Default constructor.
     */
    public Bag() {
        students = new ArrayList<>();
    }

    /**
     * Adds student of the specified color to the bag, then shuffles the bag.
     * @param color of the student to add.
     * @return whether the student was added.
     */
    public boolean receiveStudent(CreatureColor color) {
        boolean done = students.add(new Student(color));
        this.shuffle();
        return done;
    }

    /**
     * @return the number of students in the bag.
     */
    public int size() {
        return students.size();
    }

    /**
     * Draws a student from the bag.
     * @return the drawn student.
     */
    public Student drawStudent() {
        int len = students.size();
        Student student;

        if(len > MIN_BAG_SIZE) {
            student = students.get(len - 1);
            students.remove(student);
            return student;
        }
        return null;
    }

    /**
     * @return whether the bag is empty or not
     */
    public boolean isEmpty() {
        return students.size() <= MIN_BAG_SIZE;
    }

    /**
     * Shuffles the bag
     */
    public void shuffle(){
        Collections.shuffle(students);
    }
}
