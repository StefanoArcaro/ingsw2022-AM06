package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.gameBoard.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();

        for(int i = 1; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Test
    void calculateInfluence_Conquer() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character = cf.createCharacter(0);

        Player player1 = new Player("X", PlayerColor.BLACK);
        game.addPlayer(player1);
        player1.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player1.getBoard().winProfessor(new Professor(CreatureColor.GREEN));
        player1.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));
        player1.getBoard().setTowers(5);

        Player player2 = new Player("Y", PlayerColor.WHITE);
        game.addPlayer(player2);
        player2.getBoard().winProfessor(new Professor(CreatureColor.PINK));
        player2.getBoard().setTowers(5);

        game.setCurrentPlayer(player1);

        IslandGroup islandGroup1 = game.getIslandGroups().get(1);
        IslandGroup islandGroup2 = game.getIslandGroups().get(2);
        IslandGroup islandGroup3 = game.getIslandGroups().get(3);
        IslandGroup islandGroup4 = game.getIslandGroups().get(4);

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).addTower(game, player1.getColor());
        islandGroup1.setConquerorColor(player1.getColor());

        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup2.getIslands().get(0).addTower(game, player1.getColor());
        islandGroup2.setConquerorColor(player1.getColor());

        game.connectIslandGroups(1, 2);

        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup3.getIslands().get(0).addTower(game, player2.getColor());
        islandGroup3.setConquerorColor(player2.getColor());

        islandGroup4.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup4.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup4.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup4.getIslands().get(0).addTower(game, player2.getColor());
        islandGroup4.setConquerorColor(player2.getColor());

        game.calculateInfluence(2, character);

        assertEquals(10, game.getIslandGroups().size());
        assertEquals(PlayerColor.BLACK, game.getIslandGroups().get(1).getConquerorColor());
        assertEquals(3, game.getIslandGroups().get(1).getNumberOfIslands());
        assertEquals(2, player1.getBoard().getTowers());
        assertEquals(4, player2.getBoard().getTowers());
    }

    @Test
    void calculateInfluence_WithCharacterNoTower() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character = cf.createCharacter(6);

        Player player1 = new Player("X", PlayerColor.BLACK);
        game.addPlayer(player1);
        player1.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player1.getBoard().winProfessor(new Professor(CreatureColor.GREEN));
        player1.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));
        player1.getBoard().setTowers(5);

        Player player2 = new Player("Y", PlayerColor.WHITE);
        game.addPlayer(player2);
        player2.getBoard().winProfessor(new Professor(CreatureColor.PINK));
        player2.getBoard().setTowers(5);

        game.setCurrentPlayer(player1);

        IslandGroup islandGroup1 = game.getIslandGroups().get(1);

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).addTower(game, player1.getColor());
        islandGroup1.setConquerorColor(player1.getColor());

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);

        game.calculateInfluence(1, character);

        assertEquals(12, game.getIslandGroups().size());
        assertEquals(PlayerColor.WHITE, game.getIslandGroups().get(1).getConquerorColor());
        assertEquals(1, game.getIslandGroups().get(1).getNumberOfIslands());
        assertEquals(5, player1.getBoard().getTowers());
        assertEquals(4, player2.getBoard().getTowers());
    }
}