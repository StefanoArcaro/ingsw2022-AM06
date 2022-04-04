package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.StudentDestination;
import it.polimi.ingsw.model.StudentSource;

import java.util.ArrayList;

public class Entrance implements StudentSource, StudentDestination {

    private final ArrayList<Student> students;

    /**
     * Default constructor
     */
    public Entrance() {
        this.students = new ArrayList<>();
    }

    /**
     * @return the list of students in the entrance
     */
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Adds a student of the specified color to the entrance
     * @param color of the student to add
     */
    public boolean receiveStudent(CreatureColor color){
        return students.add(new Student(color));
    }

    /**
     * Removes a student of the specified color from the entrance
     * @param color of the student to remove
     * @return whether the student was removed or not
     */
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
