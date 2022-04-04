package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Hall {

    private final ArrayList<Table> students;

    /**
     * Default constructor
     */
    public Hall() {
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

    public boolean addStudent(CreatureColor color) {
        Table tableToAdd = getTableByColor(color);

        if(tableToAdd != null) {
            return tableToAdd.addStudent();
        }
        return false;
    }

    public boolean removeStudent(CreatureColor color) {
        Table tableToRemove = getTableByColor(color);

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
