package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.InvalidColorException;
import it.polimi.ingsw.exceptions.InvalidDestinationException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentPhaseTest {

    Game game;
    Phase phase;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priorities;
    int color;

    /** Initializes values */
    @BeforeEach
    void setUp() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priorities = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");
        nicknames.add("Nick");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH
        wizardIDs.add(1); // KING

        priorities.add(2);
        priorities.add(1);
        priorities.add(3);
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
        wizardIDs = null;
    }

    /** Tests the first part of the action phase in case of two players */
    @Test
    void play_2P() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        // Lobby phase
        phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Prepare phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setWizardID(wizardIDs.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Planning phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setPriority(priorities.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());

        // Move student phase
        phase = game.getCurrentPhase();

        assertEquals(game.getCurrentPlayer(), game.getPlayingOrder().get(0));

        // Invalid color (lower bound)
        color = -1;
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(0);

        assertThrows(InvalidColorException.class, () -> phase.play());
        assertEquals(7, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());

        // Invalid color (upper bound)
        color = 5;
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(4);

        assertThrows(InvalidColorException.class, () -> phase.play());
        assertEquals(7, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
        }

        // Invalid destination (lower bound)
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(-1);

        assertThrows(InvalidDestinationException.class, () -> phase.play());
        assertEquals(7, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
        }

        // Invalid destination (upper bound)
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(13);

        assertThrows(InvalidDestinationException.class, () -> phase.play());
        assertEquals(7, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
        }

        // First student moved to the hall
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(0);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(6, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            if(colorCheck.getIndex() == color) {
                assertEquals(1, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
            } else {
                assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
            }
        }

        // Second student moved to the island with ID = 12
        int islandStudents = game.getIslandByID(12).getStudents().size();
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(12);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(5, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        assertEquals(islandStudents + 1, game.getIslandByID(12).getStudents().size());

        // Third student moved to the island with ID = 1
        islandStudents = game.getIslandByID(1).getStudents().size();
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(1);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(4, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        assertEquals(islandStudents + 1, game.getIslandByID(1).getStudents().size());

        // Check current player
        assertEquals(game.getCurrentPlayer(), game.getPlayingOrder().get(0));

        // Check updated game state
        assertEquals(GameState.MOVE_MOTHER_NATURE_PHASE, game.getGameState());
    }

    /** Tests the first part of the action phase in case of three players */
    @Test
    void play_3P() {
        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        // Lobby phase
        phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Prepare phase
        phase = game.getCurrentPhase();

        for(int wizardID : wizardIDs) {
            try {
                phase.setWizardID(wizardID);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Planning phase
        phase = game.getCurrentPhase();

        for(int priority : priorities) {
            try {
                phase.setPriority(priority);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());

        // Move student phase
        phase = game.getCurrentPhase();

        assertEquals(game.getCurrentPlayer(), game.getPlayingOrder().get(0));

        // Invalid color (lower bound)
        color = -1;
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(0);

        assertThrows(InvalidColorException.class, () -> phase.play());
        assertEquals(9, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());

        // Invalid color (upper bound)
        color = 5;
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(4);

        assertThrows(InvalidColorException.class, () -> phase.play());
        assertEquals(9, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
        }

        // Invalid destination (lower bound)
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(-1);

        assertThrows(InvalidDestinationException.class, () -> phase.play());
        assertEquals(9, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
        }

        // Invalid destination (upper bound)
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(13);

        assertThrows(InvalidDestinationException.class, () -> phase.play());
        assertEquals(9, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
        }

        // First student moved to the hall
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(0);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(8, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(CreatureColor colorCheck : CreatureColor.values()) {
            if(colorCheck.getIndex() == color) {
                assertEquals(1, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
            } else {
                assertEquals(0, game.getCurrentPlayer().getBoard().getHall().getTableByColor(colorCheck).getLength());
            }
        }

        // Second student moved to the island with ID = 4
        int islandStudents = game.getIslandByID(4).getStudents().size();
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(4);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(7, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        assertEquals(islandStudents + 1, game.getIslandByID(4).getStudents().size());

        // Third student moved to the island with ID = 1
        islandStudents = game.getIslandByID(1).getStudents().size();
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(1);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(6, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        assertEquals(islandStudents + 1, game.getIslandByID(1).getStudents().size());

        // Fourth student moved to the island with ID = 12
        islandStudents = game.getIslandByID(12).getStudents().size();
        color = game.getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getColor().getIndex();
        phase.setCreatureColorIndex(color);
        phase.setStudentDestinationIndex(12);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(5, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        assertEquals(islandStudents + 1, game.getIslandByID(12).getStudents().size());

        // Check current player
        assertEquals(game.getCurrentPlayer(), game.getPlayingOrder().get(0));

        // Check updated game state
        assertEquals(GameState.MOVE_MOTHER_NATURE_PHASE, game.getGameState());
    }
}