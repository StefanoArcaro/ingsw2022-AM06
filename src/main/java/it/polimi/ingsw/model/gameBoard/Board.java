package it.polimi.ingsw.model.gameBoard;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Board {
    private ArrayList<Student> entrance;
    private ArrayList<Table> hall;
    private ArrayList<Professor> professors;
    private int towers;
    private Player player;

    /* TODO
    private Entrance entrance;
    private Hall hall;
     */

    public Board(Player player) {
        this.player = player;
        this.entrance = new ArrayList<>();
        this.hall = new ArrayList<>();
        for(CreatureColor color : CreatureColor.values()) {
            hall.add(new Table(color));
        }
        this.professors = new ArrayList<>();
    }


    public void setTowers(int towers) {
        this.towers = towers;
    }

    public ArrayList<Student> getEntrance() {
        return new ArrayList<Student>(entrance);
    }

    public ArrayList<Table> getHall() {
        return new ArrayList<Table>(hall);
    }

    public ArrayList<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }

    public int getTowers() {
        return towers;
    }

    public Player getPlayer() {
        return player;
    }

    public Table getTableByColor(CreatureColor color) {
        return hall.get(color.getIndex());
    }

    public void addStudentToEntrance(CreatureColor color) {
        entrance.add(new Student(color));
    }

    public boolean removeStudentFromEntrance(CreatureColor color) {
        if(isThereColorInEntrance(color)){
            for(Student student : entrance){
                if(student.getColor().equals(color)) {
                    entrance.remove(student);
                    return true;
                }
            }
        }
        return false;


    }

    /**
     * it checks if there's in entrance at least ONE student of the given color
     */
    public boolean isThereColorInEntrance(CreatureColor color){
        for(Student student : entrance){
            if(student.getColor().equals(color)){
                return true;
            }
        }
        return false;
    }

    public boolean addStudentToHall(CreatureColor color) {
        Table tableToAdd;
        boolean success;

        tableToAdd = getTableByColor(color);

        if(tableToAdd != null) {
            success = tableToAdd.addStudent();
            return success;
        }
        return false;
    }

    public boolean removeStudentFromHall(CreatureColor color) {
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

    /**
     * it moves a student from the entrance to a known island
     */
    public boolean moveStudentToIsland(CreatureColor color,Island island) {
        if(isThereColorInEntrance(color)){
            removeStudentFromEntrance(color);
            island.getStudents().add(new Student(color));
            }
        return false;
    }


    public void winProfessor(Professor professor) {
        if(!professors.contains(professor)) {
            professors.add(professor);
        }
    }

    public void loseProfessor(Professor professor) {
        if(professors.contains(professor)) {
            professors.remove(professor);
        }
    }

    // TODO check
    public Professor loseProfessorByColor(CreatureColor color) {
        if(containsProfessor(color)) {
            Professor result = getProfessorByColor(color);
            professors.remove(result);
            return result;
        }

        return null;
    }

    // TODO check if correct
    public boolean containsProfessor(CreatureColor color) {
        for(int i = 0; i < professors.size(); i++) {
            if(color.equals(professors.get(i).getColor())) {
                return true;
            }
        }
        return false;
    }

    private Professor getProfessorByColor(CreatureColor color) {
        if(containsProfessor(color)) {
            for(Professor professor : professors) {
                if(professor.getColor().equals(color)) {
                    return professor;
                }
            }
        }

        return null;
    }

    private void addTowers(int numberOfTowers) {
        this.towers = this.towers + numberOfTowers;
    }

    public boolean removeTower(){
        if(this.towers>0) {
            this.towers--;
            return true;
        }
        return false;
    }

    private boolean isThereNoTowers() {
        if (this.towers == 0)
            return true;
        else return false;

    }


}


