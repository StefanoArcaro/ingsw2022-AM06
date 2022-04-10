package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.ExceededStepsException;
import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveMotherNaturePhaseTest {

    Game game;
    PhaseFactory phaseFactory;
    Phase phase;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void play() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        phaseFactory = new PhaseFactory(game);
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        Player p1 = game.getPlayers().get(0);
        Player p2 = game.getPlayers().get(1);

        phase = phaseFactory.createPhase(GameState.PREPARE_PHASE);
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        phase = phaseFactory.createPhase(GameState.PLANNING_PHASE);
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        game.setCurrentPlayer(p2);

        //connect some island groups
        game.getIslandGroups().get(0).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p1.getColor());

        game.getIslandGroups().get(0).connectIslandGroup(game.getIslandGroups().get(1));
        game.getIslandGroups().get(0).connectIslandGroup(game.getIslandGroups().get(2));

        game.getIslandGroups().get(9).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(10).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(11).getIslands().get(0).addTower(game, p2.getColor());

        game.getIslandGroups().get(9).connectIslandGroup(game.getIslandGroups().get(10));
        game.getIslandGroups().get(9).connectIslandGroup(game.getIslandGroups().get(11));

        phase = phaseFactory.createPhase(GameState.MOVE_MOTHER_NATURE_PHASE);
        ((MoveMotherNaturePhase)phase).setNumberOfSteps(1);

        int previousMNIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        int expectedMNIndex = (previousMNIndex + 1) % game.getIslandGroups().size();

        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        assertEquals(expectedMNIndex, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));

        game.setCurrentPlayer(p1);
        ((MoveMotherNaturePhase)phase).setNumberOfSteps(3);

        assertThrows(ExceededStepsException.class, ()->phase.play());
    }
}