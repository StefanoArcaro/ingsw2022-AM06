package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.StudentDestination;

import java.util.ArrayList;
import java.util.Collections;

public class Bag implements StudentDestination {

    private static final int MIN_BAG_SIZE = 0;

    private static Bag bag = null;
    private final ArrayList<Student> students = new ArrayList<>();

    /**
     * Private constructor
     */
    private Bag() {}

    /**
     * Bag is a Singleton
     * @return singleton instance of the bag
     */
    public static Bag getBag() {
        if(bag == null) {
            bag = new Bag();
        }
        return bag;
    }

    /**
     * @return the list of students in the bag
     */
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public boolean receiveStudent(CreatureColor color) {
        boolean done = students.add(new Student(color));
        this.shuffle();
        return done;
    }

    /**
     * @return the number of students in the bag
     */
    public int size() {
        return students.size();
    }

    /**
     * Draws a student from the bag
     * @return the drawn student
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
     * Empties the bag
     */
    public void empty() {
        while(!isEmpty()) {
            bag.drawStudent();
        }
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
