package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.ExceededStepsException;
import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PickCloudPhaseTest {

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

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

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

        game.setCurrentPlayer(p1);

        phase = phaseFactory.createPhase(GameState.PICK_CLOUD_PHASE);

        ((PickCloudPhase)phase).setCloudID(1);
        List<CreatureColor> colorCloud = game.getCloudByID(1).getStudents().stream().map(Creature::getColor).toList();
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        List<CreatureColor> entrancePlayer1 = p1.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList();

        assertTrue(entrancePlayer1.containsAll(colorCloud));
        assertEquals(1, game.getClouds().size());

        game.setCurrentPlayer(p2);

        List<CreatureColor> entrancePlayer2 = p2.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList();

       ((PickCloudPhase)phase).setCloudID(1);
        assertThrows(NoAvailableCloudException.class, ()-> phase.play());

        assertEquals(entrancePlayer2, p2.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList());
    }

}