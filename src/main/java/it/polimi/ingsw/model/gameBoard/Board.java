package it.polimi.ingsw.model.gameBoard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;

import java.util.ArrayList;

public class Board {

    private Player player;
    private Entrance entrance;
    private Hall hall;
    private ArrayList<Professor> professors;
    private int towers;


    /* TODO
    private Entrance entrance;
    private Hall hall;
     */

    public Board(Player player) {
        this.player = player;
        this.entrance = new Entrance(this);
        this.hall = new Hall(this);
        this.professors = new ArrayList<>();
    }

    public void setTowers(int towers) {
        this.towers = towers;
    }

    public Entrance getEntrance() {
        return this.entrance;
    }

    public Hall getHall() {
        return this.hall;
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



    public void addStudentToEntrance(CreatureColor color) {
        entrance.addStudent(color);
    }

    public void removeStudentFromEntrance(CreatureColor color){
        entrance.removeStudent(color);
    }

    public boolean addStudentToHall(CreatureColor color) {
        return hall.addStudent(color);
    }

    public boolean removeStudentFromHall(CreatureColor color) {
        return hall.removeStudent(color);
    }



    public void moveStudentToHall(CreatureColor color) {}
    public void moveStudentToIsland(CreatureColor color, Island island) {}

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

    public void moveTower(int numberOfTowers) {}
    private void checkIfNoTowers() {}
    private void recoverTower(int numberOfTowers) {}
}
