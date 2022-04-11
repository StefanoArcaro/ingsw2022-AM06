package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveMotherNaturePhaseTest {

    private Game game;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;

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
        game.setGameMode(GameMode.EXPERT);

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

    @Test
    void play() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(1);

        int previousMNIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        int expectedMNIndex = (previousMNIndex + 1) % game.getIslandGroups().size();

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedMNIndex, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));
        assertEquals(GameState.PICK_CLOUD_PHASE, game.getGameState());
    }

    @Test
    void play_exceededSteps() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(3);

        assertThrows(ExceededStepsException.class, ()->game.getCurrentPhase().play());
        assertEquals(GameState.MOVE_MOTHER_NATURE_PHASE, game.getGameState());

        game.getCurrentPhase().setNumberOfSteps(1);

        int previousMNIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        int expectedMNIndex = (previousMNIndex + 1) % game.getIslandGroups().size();

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedMNIndex, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));
        assertEquals(GameState.PICK_CLOUD_PHASE, game.getGameState());
    }

    @Test
    void play_EndIsland() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));
        game.getCurrentPhase().setNumberOfSteps(1);

        Player p1 = game.getPlayingOrder().get(0);
        Player p2 = game.getPlayingOrder().get(1);

        //connect some island groups
        game.getIslandGroups().get(0).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(0).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(1).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p1.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));
        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));
        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));

        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(4).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(1).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(4).setConquerorColor(p1.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));
        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));
        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));

        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(4).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(5).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(4).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(5).setConquerorColor(p2.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));
        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));
        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));

        assertEquals(3, game.getIslandGroups().size());

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(GameState.ENDED_TOWER, game.getGameState());

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, game.getCurrentPhase().getWinner());
    }
}