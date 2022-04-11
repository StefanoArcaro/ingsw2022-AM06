package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.StudentDestination;
import it.polimi.ingsw.model.StudentSource;

import java.util.ArrayList;

public class Hall implements StudentSource, StudentDestination {

    private final ArrayList<Table> students;

    /**
     * Default constructor
     */
    public Hall (){
        this.students = new ArrayList<>();
        for(CreatureColor color : CreatureColor.values()) {
            students.add(new Table(color));
        }
    }

    public ArrayList<Table> getStudents() {
        return new ArrayList<>(students);
    }

    public Table getTableByColor(CreatureColor color) {
        return students.get(color.getIndex());
    }

    public boolean receiveStudent(CreatureColor color) throws TableFullException {
        Table tableToAdd = getTableByColor(color);

        if(tableToAdd != null) {
            return tableToAdd.addStudent();
        }
        return false;
    }

    public boolean sendStudent(CreatureColor color) {
        Table tableToRemove;

        tableToRemove = getTableByColor(color);

        if(tableToRemove != null) {
            return tableToRemove.removeStudent();
        }
        return false;
    }

    /**
     * returns true if there is at least one student in table
     */
    public boolean studentInTable(CreatureColor color) {
        Table table = getTableByColor(color);
        return table.getLength() > 0;
    }
}
