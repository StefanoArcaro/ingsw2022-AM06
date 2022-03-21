package it.polimi.ingsw;
import java.util.ArrayList;

public class Bag {
    private static Bag bag = null;
    private ArrayList<Student> students;

    private Bag(ArrayList<Student> students) {
        this.students = students;
    }

    public static synchronized Bag getBag(){
        if(bag == null){
            bag = new Bag();
        }
        return bag;
    }

    public void addStudent(Student student){}
    public Student drawStudent(){}
    public boolean isEmpty(){}
    public void Shuffle(){}
}
