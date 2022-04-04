package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IslandGroupTest {

    private IslandGroup islandGroup;
    private Island island1;
    private Island island2;
    private Island island3;
    private Island island4;

    @BeforeEach
    void setUp() {
        islandGroup = new IslandGroup();
    }

    @AfterEach
    void tearDown() {
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
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        Island islandToAdd1 = new Island(4);
        islandToAdd1.addTower(PlayerColor.BLACK);
        Island islandToAdd2 = new Island(1);
        islandToAdd2.addTower(PlayerColor.BLACK);

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island3));

        assertTrue(islandGroup.addIsland(islandToAdd1));
        assertTrue(islandGroup.addIsland(islandToAdd2));
        assertFalse(islandGroup.addIsland(island1));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void addIsland_KO() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        Player player1 = new Player("X", PlayerColor.GRAY);
        game.addPlayer(player1);
        player1.getBoard().setTowers(5);

        Island islandToAdd = new Island(2);
        islandToAdd.addTower(PlayerColor.BLACK);
        Island islandToAdd2 = new Island(1);
        islandToAdd2.addTower(PlayerColor.GRAY);

        island4 = new Island(4);
        island4.addTower(PlayerColor.GRAY);
        island3 = new Island(3);
        island3.addTower(PlayerColor.GRAY);

        assertTrue(islandGroup.addIsland(island3));
        assertTrue(islandGroup.addIsland(island4));

        assertFalse(islandGroup.addIsland(islandToAdd));
        assertFalse(islandGroup.addIsland(islandToAdd2));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void addIsland_Boundary() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        Island islandToAdd = new Island(12);
        islandToAdd.addTower(PlayerColor.BLACK);
        Island islandToAdd2 = new Island(11);
        islandToAdd2.addTower(PlayerColor.BLACK);

        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));

        assertTrue(islandGroup.addIsland(islandToAdd));
        assertTrue(islandGroup.addIsland(islandToAdd2));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void addIsland_Boundary2() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        island1 = new Island(1);
        island2 = new Island(2);
        Island island12 = new Island(12);
        Island islandToAdd = new Island(11);

        island1.addTower(PlayerColor.BLACK);
        island2.addTower(PlayerColor.BLACK);
        island12.addTower(PlayerColor.BLACK);
        islandToAdd.addTower(PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island12));

        assertTrue(islandGroup.addIsland(islandToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(11, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void addIsland_Boundary3() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        Island island12 = new Island(12);
        island12.addTower(PlayerColor.BLACK);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island12));

        Island islandToAdd = new Island(3);
        islandToAdd.addTower(PlayerColor.BLACK);
        assertTrue(islandGroup.addIsland(islandToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
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
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(7);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island1.addTower(player.getColor());
        island2 = new Island(2);
        island2.addTower(player.getColor());
        island3 = new Island(3);
        island3.addTower(player.getColor());
        island4 = new Island(4);
        island4.addTower(player.getColor());

        islandGroupToAdd.addIsland(island3);
        islandGroupToAdd.addIsland(island4);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        assertTrue(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void connectIslandGroupOK2() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island1.addTower(player.getColor());
        island2 = new Island(2);
        island2.addTower(player.getColor());
        island3 = new Island(3);
        island3.addTower(player.getColor());
        island4 = new Island(4);
        island4.addTower(player.getColor());

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island3);
        islandGroup.addIsland(island4);

        assertTrue(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void connectIslandGroupKO() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island1.addTower(player.getColor());
        island2 = new Island(2);
        island2.addTower(player.getColor());
        island4 = new Island(4);
        island4.addTower(player.getColor());

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island4);

        assertFalse(islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(4, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(1, islandGroup.getNumberOfIslands());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void connectIslandGroupKO2() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        Player player1 = new Player("X", PlayerColor.GRAY);
        player1.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island4 = new Island(4);

        island1.addTower(PlayerColor.BLACK);
        island2.addTower(PlayerColor.BLACK);
        island4.addTower(PlayerColor.GRAY);

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island4);

        assertFalse(islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(4, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(1, islandGroup.getNumberOfIslands());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void connectIslandGroup_Boundary() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        IslandGroup islandGroupToAdd = new IslandGroup();
        island2 = new Island(2);
        island2.addTower(player.getColor());
        island3 = new Island(3);
        island3.addTower(player.getColor());
        islandGroupToAdd.addIsland(island2);
        islandGroupToAdd.addIsland(island3);

        Island island12 = new Island(12);
        island12.addTower(player.getColor());
        island1 = new Island(1);
        island1.addTower(player.getColor());
        islandGroup.addIsland(island12);
        islandGroup.addIsland(island1);

        assertTrue(islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIslands());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void calculateInfluenceNoCharacter() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        assertEquals(7, islandGroup.calculateInfluence(player));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }

    }

    @Test
    void calculateInfluenceCharacter6() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);


        island1 = new Island(1);
        island1.addTower(player.getColor());
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(player.getColor());
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(player.getColor());
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character = cf.createCharacter(6);
        assertEquals(4, islandGroup.calculateInfluence(player, character));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void calculateInfluenceCharacter8() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character1 = cf.createCharacter(8);
        assertEquals(9, islandGroup.calculateInfluence(player, character1));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }

    @Test
    void calculateInfluenceCharacter9() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.receiveStudent(CreatureColor.RED);

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.receiveStudent(CreatureColor.RED);
        island2.receiveStudent(CreatureColor.YELLOW);

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.receiveStudent(CreatureColor.BLUE);
        island3.receiveStudent(CreatureColor.RED);

        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character2 = cf.createCharacter(9);
        character2.setColorNoPoints(CreatureColor.RED);
        assertEquals(4, islandGroup.calculateInfluence(player, character2));

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(0);
        }
    }
}