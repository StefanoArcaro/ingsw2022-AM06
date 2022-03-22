package it.polimi.ingsw;
import java.util.ArrayList;

public class Bag {
    private static Bag bag = null;
    private ArrayList<Student> students;

    private Bag(ArrayList<Student> students) {
        this.students = students;
    }

    public static Bag getBag(){
        if(bag == null){
            bag = new Bag(null);
        }
        return bag;
    }

    public void addStudent(Student student){}
    public Student drawStudent(){
        return null;
    }
    public boolean isEmpty(){
        return false;
    }
    public void Shuffle(){}

}
