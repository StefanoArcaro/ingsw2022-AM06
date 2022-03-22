package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.Set;

public class Board {
    private ArrayList<Student> entrance;
    private ArrayList<Table> hall;
    private Set<Professor> professors;
    private ArrayList<Tower> towers;
    private Player player;

    public void addStudentToEntrance(Student student){}
    public void addStudentToHall(CreatureColor color){}
    public void moveStudentToHall(CreatureColor color){}
    public void moveStudentToIsland(CreatureColor color, Island island){}
    public void removeStudentFromHall(CreatureColor color){}
    private void winProfessor(Professor professor){}
    private void loseProfessor(Professor professor){}
    private Set<Professor> updateProfessors(){
        return null;
    }
    public void addTower(TowerColor color){}
    public void moveTower(Island island){}
    private void checkIfNoTowers(){}
    private void recoverTower(Tower tower){}

    public Board(ArrayList<Student> entrance, ArrayList<Table> hall, Set<Professor> professors, ArrayList<Tower> towers, Player player) {
        this.entrance = entrance;
        this.hall = hall;
        this.professors = professors;
        this.towers = towers;
        this.player = player;
    }

    public ArrayList<Student> getEntrance() {
        return entrance;
    }

    public ArrayList<Table> getHall() {
        return hall;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public Player getPlayer() {
        return player;
    }
}
