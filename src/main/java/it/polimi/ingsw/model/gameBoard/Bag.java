package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.StudentDestination;

import java.util.ArrayList;
import java.util.Collections;

public class Bag implements StudentDestination {
    private static Bag bag = null;
    private ArrayList<Student> students = new ArrayList<>();

    private Bag() {}

    public static Bag getBag() {
        if(bag == null) {
            bag = new Bag();
        }
        return bag;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public boolean receiveStudent(CreatureColor color) {
        boolean done = students.add(new Student(color));
        this.shuffle();
        return done;
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
        return students.size() <= 0;
    }

    public void shuffle(){
        Collections.shuffle(students);
    }
}
