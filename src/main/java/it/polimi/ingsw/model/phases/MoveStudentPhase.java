package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.EntranceMissingColorException;
import it.polimi.ingsw.exceptions.InvalidColorException;
import it.polimi.ingsw.exceptions.InvalidDestinationException;
import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.StudentDestination;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Student;

public class MoveStudentPhase extends ActionPhase {

    private static final int MIN_COLOR_INDEX = 0;
    private static final int MAX_COLOR_INDEX = 4;

    private static final int MIN_DESTINATION_INDEX = 0;
    private static final int MAX_DESTINATION_INDEX = 12;

    private static final int HALL_DESTINATION = 0;
    private static final int MOVES_ENDED = 0;

    private int validMoves;

    /**
     * Default constructor.
     * @param game reference to the game.
     * @param currentPlayer the current player.
     */
    public MoveStudentPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.phaseFactory = new PhaseFactory(game);
        this.currentPlayer = currentPlayer;
        validMoves = game.getNumberOfPlayers().getNum() + 1;
    }

    /**
     * First part of the action phase.
     * The current player moves a certain number of students from his board's
     * entrance to a specified destination (either hall or island).
     * 2P -> 3 students
     * 3P -> 4 students
     * @throws InvalidDestinationException when the specified destination is invalid.
     * @throws EntranceMissingColorException when there are no students of the specified color
     *                                       in the current player's entrance.
     * @throws InvalidColorException when the specified color is invalid.
     * @throws TableFullException when the specified destination is Hall and the table of the
     *                            specified color is already full.
     */
    @Override
    public void play() throws InvalidDestinationException, EntranceMissingColorException,
            InvalidColorException, TableFullException {
        StudentDestination studentDestination;
        CreatureColor creatureColor;

        if(checkValidColor(creatureColorIndex)) {
            if(checkColorInEntrance(creatureColorIndex)) {
                if(checkValidDestination(studentDestinationIndex)) {
                    creatureColor = getCreatureColorByIndex(creatureColorIndex);
                    studentDestination = setDestination(studentDestinationIndex);

                    if(studentDestination.receiveStudent(creatureColor)) {
                        currentPlayer.getBoard().removeStudentFromEntrance(creatureColor);
                        game.updateProfessors();
                        validMoves -= 1;
                    }
                } else {
                    throw new InvalidDestinationException();
                }
            } else {
                throw new EntranceMissingColorException();
            }
        } else {
            throw new InvalidColorException();
        }

        if(validMoves <= MOVES_ENDED) {
            game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
            game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
        }
    }

    /**
     * Checks if the inputted color is a valid one.
     * @param color to check.
     * @return whether the color is valid.
     */
    private boolean checkValidColor(int color) {
        return color >= MIN_COLOR_INDEX && color <= MAX_COLOR_INDEX;
    }

    /**
     * Checks whether the current players' entrance has a student of the specified color.
     * @param color of the student to check.
     * @return whether the student was found.
     */
    private boolean checkColorInEntrance(int color) {
        return currentPlayer.getBoard().getEntrance().getStudents().stream()
                .map(Student::getColor)
                .map(CreatureColor::getIndex)
                .toList().contains(color);
    }

    /**
     * Checks if the specified destination is a valid one.
     * @param destination to check.
     * @return whether the destination is valid.
     */
    private boolean checkValidDestination(int destination) {
        return destination >= MIN_DESTINATION_INDEX && destination <= MAX_DESTINATION_INDEX;
    }

    /**
     * Returns the color corresponding to the specified index.
     * @param creatureColorIndex index of the color to return.
     * @return the color corresponding to the index if it exists, null otherwise.
     */
    private CreatureColor getCreatureColorByIndex(int creatureColorIndex) {
        for(CreatureColor color : CreatureColor.values()) {
            if(color.getIndex() == creatureColorIndex) {
                return color;
            }
        }
        return null;
    }

    /**
     * Sets the destination for a selected student to the specified one.
     * @param studentDestinationIndex index of the destination to set (0 = hall, 1-12 = island).
     */
    private StudentDestination setDestination(int studentDestinationIndex) {
        if(studentDestinationIndex == HALL_DESTINATION) {
            return currentPlayer.getBoard().getHall();
        } else {
            return game.getIslandByID(studentDestinationIndex);
        }
    }
}
