package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.InvalidCharacterIDException;
import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PickCloudPhaseTest {

    private Game game;
    Phase phase;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;

    /** Initializes values */
    @BeforeEach
    void setUp() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(2);
        priority.add(1);

        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EASY);

        // Lobby phase
        Phase phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

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

        // Planning phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setPriority(priority.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
        wizardIDs = null;
        priority = null;
    }

    /** Tests the shift of the students from a specific cloud to the player's entrance */
    @Test
    void play() {
        Player player = game.getCurrentPlayer();

        game.setGameState(GameState.PICK_CLOUD_PHASE);
        phase = new PhaseFactory(game).createPhase(game.getGameState());
        game.setCurrentPhase(phase);

        phase.setCloudID(1);
        List<CreatureColor> colorCloud = game.getCloudByID(1).getStudents().
                stream().map(Creature::getColor).toList();
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CreatureColor> entrancePlayer = player.getBoard().
                getEntrance().getStudents().stream().map(Creature::getColor).toList();

        assertTrue(entrancePlayer.containsAll(colorCloud));
        assertEquals(1, game.getClouds().stream().filter(cloud -> !cloud.isEmpty()).toList().size());
    }

    /** Tests the play method in case of no cloud left */
    @Test
    void play_noCloud() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        phase = new PhaseFactory(game).createPhase(game.getGameState());
        game.setCurrentPhase(phase);

        phase.setCloudID(3);
        assertThrows(NoAvailableCloudException.class, ()-> phase.play());
    }

    /** Tests the play method in case of no student left */
    @Test
    void play_endStudent() {
        Player lastPlayer = game.getPlayingOrder().get(game.getNumberOfPlayers().getNum() - 1);
        game.setCurrentPlayer(lastPlayer);
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        phase = new PhaseFactory(game).createPhase(game.getGameState());
        game.setCurrentPhase(phase);

        phase.setCloudID(2);

        int bagSize = game.getBag().size();
        for(int i = 0; i < bagSize; i++) {
            game.getBag().drawStudent();
        }

        List<CreatureColor> colorCloud = game.getCloudByID(2).getStudents().stream().map(Creature::getColor).toList();

        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CreatureColor> entrancePlayer = lastPlayer.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList();

        assertTrue(entrancePlayer.containsAll(colorCloud));
        assertEquals(1, game.getClouds().stream().filter(cloud -> !cloud.isEmpty()).toList().size());

        assertEquals(GameState.ENDED_STUDENTS, game.getGameState());
    }

    /** Tests the play method in case of no assistant left */
    @Test
    void play_endAssistant() {
        Player lastPlayer = game.getPlayingOrder().get(game.getNumberOfPlayers().getNum() - 1);
        game.setCurrentPlayer(lastPlayer);
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        phase = new PhaseFactory(game).createPhase(game.getGameState());
        game.setCurrentPhase(phase);

        phase.setCloudID(2);

        for(Player player : game.getPlayers()) {
            for (int i = 1; i < 11; i++) {
                player.getWizard().removeAssistant(i);
            }
        }

        List<CreatureColor> colorCloud = game.getCloudByID(2).getStudents().stream().map(Creature::getColor).toList();

        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CreatureColor> entrancePlayer = lastPlayer.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList();

        assertTrue(entrancePlayer.containsAll(colorCloud));
        assertEquals(1, game.getClouds().stream().filter(cloud -> !cloud.isEmpty()).toList().size());

        assertEquals(GameState.ENDED_ASSISTANTS, game.getGameState());
    }

    /** Tests the correct alternation of phases (after pick cloud phase, if the player is the last one, go to planning phase) */
    @Test
    void play_lastPlayer() {
        Player lastPlayer = game.getPlayingOrder().get(game.getNumberOfPlayers().getNum() - 1);
        game.setCurrentPlayer(lastPlayer);
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        phase = new PhaseFactory(game).createPhase(game.getGameState());
        game.setCurrentPhase(phase);

        phase.setCloudID(2);

        List<CreatureColor> colorCloud = game.getCloudByID(2).getStudents().stream().map(Creature::getColor).toList();

        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CreatureColor> entrancePlayer = lastPlayer.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList();

        assertTrue(entrancePlayer.containsAll(colorCloud));
        assertEquals(1, game.getClouds().stream().filter(cloud -> !cloud.isEmpty()).toList().size());

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());
    }

    /** Tests that it is not possible to play a character if you do not have enough coins
     * or if the character chosen is not among those extracted */
    @Test
    void play_errorsMoneyAndIdCharacter() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));
        game.getCurrentPhase().setNumberOfSteps(1);
        game.addDrawnCharacter(new ConcreteCharacterFactory(game).createCharacter(1));

        Phase phase = game.getCurrentPhase();

        assertThrows(InvalidCharacterIDException.class, ()->((ActionPhase)phase).playCharacter(6));

        assertThrows(NotEnoughMoneyException.class, ()->((ActionPhase)phase).playCharacter(1));
    }

}