package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentPhaseTest {
/*
    Game game;
    PhaseFactory phaseFactory;
    Phase phase;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void play() {
        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        phaseFactory = new PhaseFactory(game);
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        phase.play();

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        phase = phaseFactory.createPhase(GameState.PREPARE_PHASE);
        phase.play();

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        phase = phaseFactory.createPhase(GameState.PLANNING_PHASE);
        phase.play();

        game.setCurrentPlayer(game.getPlayers().get(game.getFirstPlayerIndex()));

        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());

        phase = phaseFactory.createPhase(game.getGameState());
        phase.play();

        ArrayList<Student> entranceStudents = game.getCurrentPlayer().getBoard().getEntrance().getStudents();

        // First student
        CreatureColor color = entranceStudents.get(0).getColor();
        ((MoveStudentPhase)phase).setCreatureColor(color);
        ((MoveStudentPhase)phase).setStudentDestination(0);

        assertEquals(8, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        assertEquals(1, game.getCurrentPlayer().getBoard().getHall().getTableByColor(color).getLength());

        // Second student
        color = entranceStudents.get(0).getColor();
        ((MoveStudentPhase)phase).setCreatureColor(color);
        ((MoveStudentPhase)phase).setStudentDestination(5);

        assertEquals(7, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        if(game.getIslandByID(5).getStudents().size() > 0) {
            assertEquals(2, game.getIslandByID(5).getStudents().size());
        } else {
            assertEquals(1, game.getIslandByID(5).getStudents().size());
        }
        assertTrue(game.getIslandByID(5).getStudents().stream()
                        .map(Student::getColor)
                        .collect(Collectors.toList()).contains(color));

        // Third student
        color = entranceStudents.get(0).getColor();
        ((MoveStudentPhase)phase).setCreatureColor(color);
        ((MoveStudentPhase)phase).setStudentDestination(4);

        assertEquals(6, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        if(game.getIslandByID(5).getStudents().size() > 0) {
            assertEquals(2, game.getIslandByID(4).getStudents().size());
        } else {
            assertEquals(1, game.getIslandByID(4).getStudents().size());
        }
        assertTrue(game.getIslandByID(4).getStudents().stream()
                .map(Student::getColor)
                .collect(Collectors.toList()).contains(color));

        // Fourth student
        color = entranceStudents.get(0).getColor();
        ((MoveStudentPhase)phase).setCreatureColor(color);
        ((MoveStudentPhase)phase).setStudentDestination(12);

        assertEquals(5, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        if(game.getIslandByID(5).getStudents().size() > 0) {
            assertEquals(2, game.getIslandByID(12).getStudents().size());
        } else {
            assertEquals(1, game.getIslandByID(12).getStudents().size());
        }
        assertTrue(game.getIslandByID(12).getStudents().stream()
                .map(Student::getColor)
                .collect(Collectors.toList()).contains(color));
    }

    @Test
    void getCreatureColor() {
    }

    @Test
    void setCreatureColor() {
    }

    @Test
    void getStudentDestination() {
    }

    @Test
    void setStudentDestination() {
    }

 */
}