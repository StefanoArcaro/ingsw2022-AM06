package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class Board {

    private final Player player;
    private final Entrance entrance;
    private final Hall hall;
    private final ArrayList<Professor> professors;
    private int towers;

    /**
     * Default constructor
     * @param player who owns the instantiated board
     */
    public Board(Player player) {
        this.player = player;
        this.entrance = new Entrance();
        this.hall = new Hall();
        this.professors = new ArrayList<>();
    }


    public void setTowers(int towers) {
        this.towers = towers;
    }

    /**
     * @return the Entrance component of the board
     */
    public Entrance getEntrance() {
        return this.entrance;
    }

    /**
     * @return the Hall component of the board
     */
    public Hall getHall() {
        return this.hall;
    }

    /**
     * @return the list of professors on the board
     */
    public ArrayList<Professor> getProfessors() {
        return new ArrayList<>(professors);
    }

    public int getTowers() {
        return towers;
    }

    /**
     * @return the player that owns the board
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Adds a student of the inputted color to the board's entrance
     * @param color of the student to add to the entrance
     */
    public void addStudentToEntrance(CreatureColor color) {
        entrance.receiveStudent(color);
    }

    /**
     * Removes a student of the specified color from the board's entrance
     * @param color of the student to remove
     * @return whether the student was removed
     */
    public boolean removeStudentFromEntrance(CreatureColor color){
        return entrance.sendStudent(color);
    }

    /**
     * Adds a student of the specified color to the board's hall
     * @param color of the student to add
     * @return whether the student was added or not
     */
    public boolean addStudentToHall(CreatureColor color) {
        return hall.receiveStudent(color);
    }

    /**
     * Removes a student of the specified color from the board's hall
     * @param color of the student to remove
     * @return whether the student was removed or not
     */
    public boolean removeStudentFromHall(CreatureColor color) {
        return hall.sendStudent(color);
    }

    /**
     * Moves a student of the specified color to the inputted island
     * @param color of the student to move
     * @param island to move the student to
     * @return whether the student was moved or not
     */
    public boolean moveStudentToIsland(CreatureColor color,Island island) {
       if(removeStudentFromEntrance(color)){
           island.receiveStudent(color);
           return true;
       }
       return false;

    }

    /**
     * Adds the specified professor to the board
     * @param professor to add
     */
    public void winProfessor(Professor professor) {
        if(!professors.contains(professor)) {
            professors.add(professor);
        }
    }

    /**
     * Removes the specified professor from the board
     * @param professor to remove
     */
    public void loseProfessor(Professor professor) {
        professors.remove(professor);
    }

    /**
     * Removes the professor of the specified color from the board
     * @param color of the professor to remove
     * @return the removed professor if it was already on the board, null otherwise
     */
    public Professor loseProfessorByColor(CreatureColor color) {
        if(containsProfessor(color)) {
            Professor result = getProfessorByColor(color);
            professors.remove(result);
            return result;
        }

        return null;
    }

    /**
     * Checks if the professor of the specified color is on the board
     * @param color of the professor to check
     * @return whether the professor is on the board or not
     */
    public boolean containsProfessor(CreatureColor color) {
        for (Professor professor : professors) {
            if (color.equals(professor.getColor())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the professor corresponding to the specified color
     * @param color of the professor to return
     * @return the professor of the specified color if present, null otherwise
     */
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

    /**
     * Increases the board's number of towers by the specified amount
     * @param numberOfTowers to add to the board's towers
     */
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

