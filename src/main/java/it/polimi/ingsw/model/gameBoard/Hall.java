package it.polimi.ingsw.model.gameBoard;

import java.util.ArrayList;

public class Hall {

    private Board board;
    private ArrayList<Table> students;

    public Hall (Board board){
        this.board = board;
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

    public boolean addStudent(CreatureColor color){
        Table tableToAdd;
        boolean success;

        tableToAdd = getTableByColor(color);

        if(tableToAdd != null) {
            success = tableToAdd.addStudent();
            return success;
        }
        return false;
    }

    public boolean removeStudent(CreatureColor color){
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
