package it.polimi.ingsw;

import java.util.ArrayList;

public class Table {
    private CreatureColor color;
    private ArrayList<Student> students;

    public Table(CreatureColor color, ArrayList<Student> students) {
        this.color = color;
        this.students = students;
    }

    public void addStudent(){}
    public int getLenght(){
        return 0;
    }
}
