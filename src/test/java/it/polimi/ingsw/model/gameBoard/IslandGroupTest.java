package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IslandGroupTest {

    Game game;
    ConcreteCharacterFactory cf;
    private IslandGroup islandGroup;
    private Island island1;
    private Island island2;
    private Island island3;
    private Island island4;

    @BeforeEach
    void setUp() {
        game = new Game();
        cf = new ConcreteCharacterFactory(game);
        islandGroup = new IslandGroup();
    }

    @AfterEach
    void tearDown() {
        game = null;
        islandGroup = null;
    }

    @Test
    void getIslands() {
        ArrayList<Island> expectedIslands = new ArrayList<>();
        island1 = new Island(1);
        island2 = new Island(2);

        //Test no island
        assertEquals(expectedIslands, islandGroup.getIslands());

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        expectedIslands.add(island1);
        expectedIslands.add(island2);

        //Test 2 islands
        assertEquals(expectedIslands, islandGroup.getIslands());
    }

    @Test
    void getNumberOfIsland() {
        assertEquals(0, islandGroup.getNumberOfIslands());

        island1 = new Island(1);
        island2 = new Island(2);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        assertEquals(2, islandGroup.getNumberOfIslands());
    }

    @Test
    void addIsland_OK() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        Island islandToAdd1 = new Island(4);
        islandToAdd1.addTower(game, PlayerColor.BLACK);
        Island islandToAdd2 = new Island(1);
        islandToAdd2.addTower(game, PlayerColor.BLACK);

        island2 = new Island(2);
        island2.addTower(game, PlayerColor.BLACK);
        island3 = new Island(3);
        island3.addTower(game, PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island3));

        assertTrue(islandGroup.addIsland(islandToAdd1));
        assertTrue(islandGroup.addIsland(islandToAdd2));
        assertFalse(islandGroup.addIsland(island1));
    }

    @Test
    void addIsland_Boundary() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        Island islandToAdd = new Island(12);
        islandToAdd.addTower(game, PlayerColor.BLACK);
        Island islandToAdd2 = new Island(11);
        islandToAdd2.addTower(game, PlayerColor.BLACK);

        island1 = new Island(1);
        island1.addTower(game, PlayerColor.BLACK);
        island2 = new Island(2);
        island2.addTower(game, PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));

        assertTrue(islandGroup.addIsland(islandToAdd));
        assertTrue(islandGroup.addIsland(islandToAdd2));
    }

    @Test
    void addIsland_Boundary2() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        island1 = new Island(1);
        island2 = new Island(2);
        Island island12 = new Island(12);
        Island islandToAdd = new Island(11);

        island1.addTower(game, PlayerColor.BLACK);
        island2.addTower(game, PlayerColor.BLACK);
        island12.addTower(game, PlayerColor.BLACK);
        islandToAdd.addTower(game, PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island12));

        assertTrue(islandGroup.addIsland(islandToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(11, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());
    }

    @Test
    void addIsland_Boundary3() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        island1 = new Island(1);
        island1.addTower(game, PlayerColor.BLACK);
        island2 = new Island(2);
        island2.addTower(game, PlayerColor.BLACK);
        Island island12 = new Island(12);
        island12.addTower(game, PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island12));

        Island islandToAdd = new Island(3);
        islandToAdd.addTower(game, PlayerColor.BLACK);
        assertTrue(islandGroup.addIsland(islandToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
    }

    @Test
    void getNumberOfBanCardPresent() {
        assertEquals(0, islandGroup.getNumberOfBanCardPresent());

        islandGroup.addBanCard();
        assertEquals(1, islandGroup.getNumberOfBanCardPresent());

        islandGroup.addBanCard();
        assertEquals(2, islandGroup.getNumberOfBanCardPresent());
    }

    @Test
    void removeBanCard() {
        assertFalse(islandGroup.removeBanCard());

        islandGroup.addBanCard();
        assertTrue(islandGroup.removeBanCard());
    }

    @Test
    void connectIslandGroupOK() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(7);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island1.addTower(game, player.getColor());
        island2 = new Island(2);
        island2.addTower(game, player.getColor());
        island3 = new Island(3);
        island3.addTower(game, player.getColor());
        island4 = new Island(4);
        island4.addTower(game, player.getColor());

        islandGroupToAdd.addIsland(island3);
        islandGroupToAdd.addIsland(island4);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        assertNotNull(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());
    }

    @Test
    void connectIslandGroupOK2() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island1.addTower(game, player.getColor());
        island2 = new Island(2);
        island2.addTower(game, player.getColor());
        island3 = new Island(3);
        island3.addTower(game, player.getColor());
        island4 = new Island(4);
        island4.addTower(game, player.getColor());

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island3);
        islandGroup.addIsland(island4);

        assertNotNull(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());
    }

    @Test
    void connectIslandGroupKO() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island1.addTower(game, player.getColor());
        island2 = new Island(2);
        island2.addTower(game, player.getColor());
        island4 = new Island(4);
        island4.addTower(game, player.getColor());

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island4);

        assertNull(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(4, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(1, islandGroup.getNumberOfIslands());
    }

    @Test
    void connectIslandGroupKO2() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        Player player1 = new Player(game, "X", PlayerColor.GRAY);
        player1.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island4 = new Island(4);

        island1.addTower(game, PlayerColor.BLACK);
        island2.addTower(game, PlayerColor.BLACK);
        island4.addTower(game, PlayerColor.GRAY);

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island4);

        assertNull(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(4, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(1, islandGroup.getNumberOfIslands());
    }

    @Test
    void connectIslandGroup_Boundary() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island2 = new Island(2);
        island2.addTower(game, player.getColor());
        island3 = new Island(3);
        island3.addTower(game, player.getColor());
        islandGroupToAdd.addIsland(island2);
        islandGroupToAdd.addIsland(island3);

        Island island12 = new Island(12);
        island12.addTower(game, player.getColor());
        island1 = new Island(1);
        island1.addTower(game, player.getColor());
        islandGroup.addIsland(island12);
        islandGroup.addIsland(island1);

        assertNotNull(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());
    }

    @Test
    void calculateInfluenceNoCharacter() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        game.setCurrentPlayer(player);
        player.getBoard().setTowers(5);

        island1 = new Island(1);
        island1.addTower(game, PlayerColor.BLACK);
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(game, PlayerColor.BLACK);
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(game, PlayerColor.BLACK);
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        Character character = cf.createCharacter(0);
        assertEquals(7, islandGroup.calculateInfluence(game, player, character));
    }

    @Test
    void calculateInfluenceCharacter6() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);


        island1 = new Island(1);
        island1.addTower(game, player.getColor());
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(game, player.getColor());
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(game, player.getColor());
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        Character character = cf.createCharacter(6);
        assertEquals(4, islandGroup.calculateInfluence(game, player, character));
    }

    @Test
    void calculateInfluenceCharacter8() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        island1 = new Island(1);
        island1.addTower(game, PlayerColor.BLACK);
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(game, PlayerColor.BLACK);
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(game, PlayerColor.BLACK);
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character1 = cf.createCharacter(8);
        assertEquals(9, islandGroup.calculateInfluence(game, player, character1));
    }

    @Test
    void calculateInfluenceCharacter9() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        island1 = new Island(1);
        island1.addTower(game, PlayerColor.BLACK);
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(game, PlayerColor.BLACK);
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(game, PlayerColor.BLACK);
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character2 = cf.createCharacter(9);
        character2.setColorNoPoints(CreatureColor.RED);
        assertEquals(4, islandGroup.calculateInfluence(game, player, character2));
    }
}