package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Cloud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanningPhaseTest {

    Game game;
    PhaseFactory phaseFactory;
    Phase phase;

    @Test
    void play() {
        game = Game.getGame();
        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        phaseFactory = new PhaseFactory();
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        phase.play();

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        phase = phaseFactory.createPhase(GameState.PREPARE_PHASE);
        phase.play();

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        phase = phaseFactory.createPhase(GameState.PLANNING_PHASE);
        phase.play();

        for(Cloud cloud : game.getClouds()) {
            assertEquals(4, cloud.getStudents().size());
        }

        // Check discarded assistant
        for(Player player : game.getPlayers()) {
            assertEquals(9, player.getWizard().getAssistants().size());
        }

        // Check player order
        assertEquals(game.getPlayers().get(game.getFirstPlayerIndex()), game.getPlayingOrder().get(0));

        // Check updated game state
        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());
    }
}