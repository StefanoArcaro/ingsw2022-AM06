package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EndgamePhaseTest {

    Game game;
    PhaseFactory phaseFactory;
    Phase phase;

    @BeforeEach
    void setUp() {
        game = new Game();
        phaseFactory = new PhaseFactory(game);
    }


    @Test
    void play_equity() {
        Player player1 = new Player(game, "x", PlayerColor.BLACK);
        Player player2 = new Player(game, "y", PlayerColor.WHITE);

        game.addPlayer(player1);
        player1.getBoard().setTowers(3);
        player1.getBoard().winProfessor(new Professor(CreatureColor.GREEN));
        player1.getBoard().winProfessor(new Professor(CreatureColor.RED));
        game.addPlayer(player2);
        player2.getBoard().setTowers(3);
        player2.getBoard().winProfessor(new Professor(CreatureColor.PINK));

        ArrayList<Player> playingOrder = new ArrayList<>(Arrays.asList(player1, player2));
        game.setPlayingOrder(playingOrder);
        game.setCurrentPlayer(player2);

        game.setGameState(GameState.ENDED_ISLAND);
        phase = phaseFactory.createPhase(game.getGameState());
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(player1, phase.getWinner());
    }

    @Test
    void play() {
        Player player1 = new Player(game, "x", PlayerColor.BLACK);
        Player player2 = new Player(game, "y", PlayerColor.WHITE);

        game.addPlayer(player1);
        player1.getBoard().setTowers(3);
        game.addPlayer(player2);
        player2.getBoard().setTowers(4);

        ArrayList<Player> playingOrder = new ArrayList<>(Arrays.asList(player1, player2));
        game.setPlayingOrder(playingOrder);
        game.setCurrentPlayer(player2);

        game.setGameState(GameState.ENDED_STUDENTS);
        phase = phaseFactory.createPhase(game.getGameState());
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(player1, phase.getWinner());
    }

    @Test
    void play_noTower() {
        Player player1 = new Player(game, "x", PlayerColor.BLACK);
        Player player2 = new Player(game, "y", PlayerColor.WHITE);

        game.addPlayer(player1);
        player1.getBoard().setTowers(0);
        game.addPlayer(player2);
        player2.getBoard().setTowers(3);

        ArrayList<Player> playingOrder = new ArrayList<>(Arrays.asList(player1, player2));
        game.setPlayingOrder(playingOrder);
        game.setCurrentPlayer(player1);

        game.setGameState(GameState.ENDED_TOWER);
        phase = phaseFactory.createPhase(game.getGameState());
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(player1, phase.getWinner());
    }
}