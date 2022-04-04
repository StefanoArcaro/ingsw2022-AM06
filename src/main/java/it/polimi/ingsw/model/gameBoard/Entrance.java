package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Entrance {

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
    public void addStudent(CreatureColor color) {
        students.add(new Student(color));
    }

    /**
     * Removes a student of the specified color from the entrance
     * @param color of the student to remove
     * @return whether the student was removed or not
     */
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
