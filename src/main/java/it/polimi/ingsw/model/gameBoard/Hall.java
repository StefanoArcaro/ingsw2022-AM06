package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.enumerations.CreatureColor;

import java.util.ArrayList;

public class Hall implements StudentSource, StudentDestination {

    private final ArrayList<Table> students;

    /**
     * Default constructor.
     */
    public Hall() {
        this.students = new ArrayList<>();
        for(CreatureColor color : CreatureColor.values()) {
            students.add(new Table(color));
        }
    }

    /**
     * @return the list of tables in the hall.
     */
    public ArrayList<Table> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * @param color of the table to get.
     * @return the table of the specified color.
     */
    public Table getTableByColor(CreatureColor color) {
        return students.get(color.getIndex());
    }

    /**
     * Sends a student away from the hall.
     * @param color of the student to send.
     * @return whether the student was sent.
     */
    public boolean sendStudent(CreatureColor color) {
        return getTableByColor(color).removeStudent();
    }

    /**
     * Receives a student of the specified color.
     * @param color of the student to receive.
     * @return whether the transfer was successful.
     * @throws TableFullException when there's no more space for a student in that color's table.
     */
    public boolean receiveStudent(CreatureColor color) throws TableFullException {
        return getTableByColor(color).addStudent();
    }

    /**
     * @param color of the table to check.
     * @return whether there's at least one student in the table of the specified color.
     */
    public boolean studentInTable(CreatureColor color) {
        Table table = getTableByColor(color);
        return table.getLength() > 0;
    }
}
