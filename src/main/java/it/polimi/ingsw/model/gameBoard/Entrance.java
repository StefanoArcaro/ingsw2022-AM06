package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.StudentDestination;
import it.polimi.ingsw.model.StudentSource;

import java.util.ArrayList;

public class Entrance implements StudentSource, StudentDestination {

    private ArrayList<Student> students;

    public Entrance() {
        this.students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public boolean receiveStudent(CreatureColor color){
        return students.add(new Student(color));
    }

    public boolean sendStudent(CreatureColor color) {
        for(Student student : students) {
            if(student.getColor().equals(color)) {
                students.remove(student);
                return true;
            }
        }
        return false;
    }
}
