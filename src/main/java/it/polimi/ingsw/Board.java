package it.polimi.ingsw;
import java.util.ArrayList;

public class Board {
    private ArrayList<Student> entrance;
    private ArrayList<Table> hall;
    private ArrayList<Professor> professors;
    private int towers;
    private Player player;

    public Board(Player player) {
        this.player = player;
        this.entrance = new ArrayList<Student>();
        this.hall = new ArrayList<Table>();
        for(CreatureColor color : CreatureColor.values()){
            hall.add(new Table(color));
        }
        this.professors = new ArrayList<Professor>();
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

    private Table getTableByColor (CreatureColor color){
        for (Table table : hall){
            if (table.getColor().equals(color)){
                return table;
            }
        }
        return null;
    }

    public void addStudentToEntrance(CreatureColor color){}

    public void removeStudentFromEntrance (CreatureColor color){}


    public boolean addStudentToHall(CreatureColor color){
        Table tableToAdd;
        boolean success;

        tableToAdd = getTableByColor(color);

        if (tableToAdd != null) {
            success = tableToAdd.addStudent();
            return success;
        }
        return false;
    }

    public boolean removeStudentFromHall(CreatureColor color){
        Table tableToRemove;

        tableToRemove = getTableByColor(color);

        if (tableToRemove != null) {
            return tableToRemove.removeStudent();
        }
        return false;
    }

    public boolean studentInTable (CreatureColor color){
        Table table = getTableByColor(color);
        return table.getLenght() > 0;
    }

    public void moveStudentToHall(CreatureColor color){}
    public void moveStudentToIsland(CreatureColor color, Island island){}



    //LI METTO PUBBLICI SOLO PER FARE I TEST (SI DEVE IMPLEMENTARE UPDATEPROFESSORS)
    public void winProfessor(Professor professor){
        if (!(professors.contains(professor))){
            professors.add(professor);
        }
    }
    public void loseProfessor(Professor professor){
        if(professors.contains(professor)){
            professors.remove(professor);
        }
    }


    private ArrayList<Professor> updateProfessors(){
        return null;
    }

    public void moveTower(int numberOfTowers){}
    private void checkIfNoTowers(){}
    private void recoverTower(int numberOfTowers){}
}
