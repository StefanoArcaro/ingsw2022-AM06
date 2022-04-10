package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentDestination;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Student;

import java.util.stream.Collectors;

public class MoveStudentPhase extends ActionPhase {

    private static final int HALL_DESTINATION = 0;
    private static final int MOVES_ENDED = 0;

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
        creatureColor = null;
        studentDestination = null;
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
        //int validMoves = game.getNumberOfPlayers().getNum() + 1;
        int validMoves = game.getNumberOfPlayers().getNum() + 1;

        do {
            // TODO Wait for input

            if(checkColorInEntrance(creatureColor)) {
                if(studentDestination.receiveStudent(creatureColor)) {
                    currentPlayer.getBoard().removeStudentFromEntrance(creatureColor);
                    validMoves -= 1;
                } else {
                    // TODO Send error message : table is full
                }
            } else {
                // TODO send error message : color not in entrance
            }
            System.out.println(validMoves);
        } while(validMoves > MOVES_ENDED);
    }

    /**
     * @return the color of the student to move
     */
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

    /**
     * @return the destination of the student
     */
    public StudentDestination getStudentDestination() {
        return studentDestination;
    }

    /**
     * Sets the destination for a selected student to the specified one.
     * Parameter: 0 = hall, 1-12 = islands
     * @param studentDestination to set
     */
    public void setStudentDestination(int studentDestination) {
        if(studentDestination == HALL_DESTINATION) {
            this.studentDestination = currentPlayer.getBoard().getHall();
        } else {
            this.studentDestination = game.getIslandByID(studentDestination);
        }
    }

    /**
     * Checks whether the current players' entrance has a student of the specified color
     * @param color of the student to check
     * @return whether the student was found
     */
    private boolean checkColorInEntrance(CreatureColor color) {
        return currentPlayer.getBoard().getEntrance().getStudents().stream()
                .map(Student::getColor)
                .collect(Collectors.toList()).contains(color);
    }
}
