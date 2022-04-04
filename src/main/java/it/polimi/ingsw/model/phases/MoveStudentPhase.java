package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentDestination;
import it.polimi.ingsw.model.gameBoard.CreatureColor;

public class MoveStudentPhase extends ActionPhase {

    private CreatureColor creatureColor;
    private StudentDestination studentDestination;

    /**
     * Default constructor
     * @param game reference to the game
     * @param currentPlayer the current player
     */
    public MoveStudentPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
    }

    /**
     * First part of the action phase.
     * The current player moves a certain number of students from his board's
     * entrance to a specified destination (either hall or island) --- 0 = hall, 1-12 = islands
     * 2P -> 3 students
     * 3P -> 4 students
     */
    @Override
    public void play() {

    }

    public CreatureColor getCreatureColor() {
        return creatureColor;
    }

    /**
     * Sets the color of the student to move
     * @param creatureColor to set
     */
    public void setCreatureColor(CreatureColor creatureColor) {
        this.creatureColor = creatureColor;
    }

    public StudentDestination getStudentDestination() {
        return studentDestination;
    }

    /**
     * Sets the destination for a selected student to the specified one
     * @param studentDestination to set
     */
    public void setStudentDestination(StudentDestination studentDestination) {
        this.studentDestination = studentDestination;
    }
}
