package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.AssistantTakenException;
import it.polimi.ingsw.exceptions.InvalidPriorityException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.gameBoard.Bag;
import it.polimi.ingsw.model.gameBoard.Cloud;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlanningPhaseTest {

    private Game game;
    private Phase phase;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private int priority;

    /** Tests the setup of the phase */
    @BeforeEach
    void setUp() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");
        nicknames.add("Nick");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH
        wizardIDs.add(1); // KING
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
        wizardIDs = null;
    }

    /** Tests the planning phase of the game in case of two players */
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

        // Removing 1 assistant to test already played ones
        game.getCurrentPlayer().getWizard().removeAssistant(3);

        // First player chooses invalid priority (lower bound)
        priority = 0;
        phase.setPriority(priority);

        assertThrows(InvalidPriorityException.class, () -> phase.play());
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // First player chooses an assistant he 'already played'
        priority = 3;
        phase.setPriority(priority);

        assertThrows(InvalidPriorityException.class, () -> phase.play());
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // First player chooses playable assistant
        priority = 2;
        phase.setPriority(priority);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Second player chooses an assistant already played in the same
        // turn by another player
        priority = 2;
        phase.setPriority(priority);

        assertThrows(AssistantTakenException.class, () -> phase.play());
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Second player chooses playable assistant
        priority = 1;
        phase.setPriority(priority);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check number of assistants remaining
        assertEquals(9, game.getCurrentPlayer().getWizard().getAssistants().size());
        assertEquals(8, game.getNextPlayer().getWizard().getAssistants().size());

        // Check clouds full
        for(Cloud cloud : game.getClouds()) {
            assertEquals(3, cloud.getStudents().size());
        }

        // Check player order
        assertEquals(game.getPlayers().get(game.getFirstPlayerIndex()), game.getPlayingOrder().get(0));

        // Check updated game state
        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());
    }

    /** Tests the planning phase of the game in case of three players */
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

        // Removing 1 assistant to test already played ones
        game.getCurrentPlayer().getWizard().removeAssistant(3);

        // First player chooses invalid priority (lower bound)
        priority = 0;
        phase.setPriority(priority);

        assertThrows(InvalidPriorityException.class, () -> phase.play());
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // First player chooses an assistant he 'already played'
        priority = 3;
        phase.setPriority(priority);

        assertThrows(InvalidPriorityException.class, () -> phase.play());
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // First player chooses playable assistant
        priority = 2;
        phase.setPriority(priority);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Second player chooses an assistant already played in the same
        // turn by another player
        priority = 2;
        phase.setPriority(priority);

        assertThrows(AssistantTakenException.class, () -> phase.play());
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Second player chooses playable assistant
        priority = 1;
        phase.setPriority(priority);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Third player chooses playable assistant
        priority = 3;
        phase.setPriority(priority);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check number of assistants remaining
        assertEquals(9, game.getCurrentPlayer().getWizard().getAssistants().size());
        assertEquals(8, game.getNextPlayer().getWizard().getAssistants().size());
        game.setCurrentPlayer(game.getNextPlayer());
        assertEquals(9, game.getNextPlayer().getWizard().getAssistants().size());

        // Check clouds full
        for(Cloud cloud : game.getClouds()) {
            assertEquals(4, cloud.getStudents().size());
        }

        // Check playing order
        assertEquals(game.getPlayers().get(game.getFirstPlayerIndex()), game.getPlayingOrder().get(0));

        // Check updated game state
        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());
    }

    /** Tests the planning phase of the game when there are not enough students for the clouds */
    @Test
    void play_notEnoughStudentsForClouds() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EASY);

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

        // Planning phase
        phase = game.getCurrentPhase();

        ArrayList<Integer> priority = new ArrayList<>();
        priority.add(2);
        priority.add(1);

        Bag bag = game.getBag();
        int bagSize = bag.size();

        //to empty the bag
        for(int i = 0; i < bagSize; i++) {
            bag.drawStudent();
        }

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setPriority(priority.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertTrue(game.getSkipPickCloudPhase());
        // Check updated game state
        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());

        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));
        game.getCurrentPhase().setNumberOfSteps(1);

        try{
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());
    }
}