package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyPhaseTest {

    Game game;
    PhaseFactory phaseFactory;
    Phase phase;

    @BeforeEach
    void setUp() {
        Game.getGame().resetGame();
        game = Game.getGame();
    }

    @Test
    void play() {
        game.setNumberOfPlayers(3);

        phaseFactory = new PhaseFactory();
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        phase.play();

        assertEquals("Stefano", game.getPlayers().get(0).getNickname());
        assertEquals("Chiara", game.getPlayers().get(1).getNickname());
        assertEquals("Nick", game.getPlayers().get(2).getNickname());

        assertEquals(PlayerColor.BLACK, game.getPlayers().get(0).getColor());
        assertEquals(PlayerColor.WHITE, game.getPlayers().get(1).getColor());
        assertEquals(PlayerColor.GRAY, game.getPlayers().get(2).getColor());

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());
    }
}