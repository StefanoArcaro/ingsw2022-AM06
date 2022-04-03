package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Entrance {

    private Board board;
    private ArrayList<Student> students;

    public Entrance(Board board) {
        this.board = board;
        this.students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void addStudent(CreatureColor color){
        students.add(new Student(color));
    }

    public boolean removeStudent(CreatureColor color) {
        for(Student student : students) {
            if(student.getColor().equals(color)) {
                students.remove(student);
                return true;
            }
        }
        return false;
    }
}
