package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;
import java.util.Collections;

public class Bag {
    private static Bag bag = null;
    private ArrayList<Student> students = new ArrayList<>();

    private Bag() {}

    public static Bag getBag() {
        if(bag == null) {
            bag = new Bag();
        }
        return bag;
    }

    public void addStudent(Student student) {
        students.add(student);
        //this.shuffle(); commented out for testing purposes
    }

    public int size() {
        return students.size();
    }

    public Student drawStudent() {
        int len = students.size();
        Student student;

        if(len > 0) {
            student = students.get(len - 1);
            students.remove(student);
            return student;
        }
        return null;
    }

    public boolean isEmpty() {
        if(students.size() > 0) {
            return true;
        }
        return false;
    }

    public void shuffle(){
        Collections.shuffle(students);
    }
}
