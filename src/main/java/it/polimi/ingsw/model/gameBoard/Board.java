package it.polimi.ingsw.model.gameBoard;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Board {
    private final Player player;
    private final Entrance entrance;
    private final Hall hall;
    private ArrayList<Professor> professors;
    private int towers;


    public Board(Player player) {
        this.player = player;
        this.entrance = new Entrance();
        this.hall = new Hall();
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
        entrance.receiveStudent(color);
    }

    public boolean removeStudentFromEntrance(CreatureColor color){
        return entrance.sendStudent(color);
    }

    public boolean addStudentToHall(CreatureColor color) {
        return hall.receiveStudent(color);
    }

    public boolean removeStudentFromHall(CreatureColor color) {
        return hall.sendStudent(color);
    }

    /**
     * it moves a student from the entrance to a known island
     */
    public boolean moveStudentToIsland(CreatureColor color,Island island) {
       if(removeStudentFromEntrance(color)){
           island.receiveStudent(color);
           return true;
       }
       return false;

    }

    public void winProfessor(Professor professor) {
        if(!professors.contains(professor)) {
            professors.add(professor);
        }
    }

    public void loseProfessor(Professor professor) {
        professors.remove(professor);
    }

    /**
     * it eliminates the professor of the given color, and returns that specific professor
      */
    public Professor loseProfessorByColor(CreatureColor color) {
        if(containsProfessor(color)) {
            Professor result = getProfessorByColor(color);
            professors.remove(result);
            return result;
        }

        return null;
    }

    public boolean containsProfessor(CreatureColor color) {
        for (Professor professor : professors) {
            if (color.equals(professor.getColor())) {
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

    public void addTowers(int numberOfTowers) {
        this.towers = this.towers + numberOfTowers;
    }

    /**
     * Removes a certain amount of towers from the board
     * @return if there are no more towers
     */
    public boolean removeTower() {
        if(towers > 0) {
            this.towers -= 1;
            return true;
        }
        return false;
    }

    //TODO check
    public boolean isThereNoTowers() {
        return this.towers <= 0;
    }


}


