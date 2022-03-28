package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterInfluenceModifier;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.gameBoard.*;
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
        ArrayList<Island> expectedIslands = new ArrayList<Island>();
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
        assertEquals(0, islandGroup.getNumberOfIsland());

        island1 = new Island(1);
        island2 = new Island(2);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        assertEquals(2, islandGroup.getNumberOfIsland());
    }

    @Test
    void addIsland_OK() {
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
    }

    @Test
    void addIsland_KO() {
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

    }

    @Test
    void addIsland_Boundary() {
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

    }

    @Test
    void addIsland_Boundary2() {
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
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void addIsland_Boundary3() {
        island1 = new Island(1);
        island2 = new Island(2);
        Island island12 = new Island(12);

        assertTrue(islandGroup.addIsland(island1));
        assertTrue(islandGroup.addIsland(island2));
        assertTrue(islandGroup.addIsland(island12));

        Island islandToAdd = new Island(3);
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
        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island3 = new Island(3);
        island4 = new Island(4);

        islandGroupToAdd.addIsland(island3);
        islandGroupToAdd.addIsland(island4);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);


        assertTrue(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroupOK2() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island3 = new Island(3);
        island4 = new Island(4);

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island3);
        islandGroup.addIsland(island4);

        assertTrue(islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroupKO() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island4 = new Island(4);

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island4);

        assertFalse(islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(4, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(1, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroupKO2() {
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
        assertEquals(1, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroup_Boundary() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island2 = new Island(2);
        island3 = new Island(3);
        islandGroupToAdd.addIsland(island2);
        islandGroupToAdd.addIsland(island3);

        Island island12 = new Island(12);
        island1 = new Island(1);
        islandGroup.addIsland(island12);
        islandGroup.addIsland(island1);

        assertTrue(islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void calculateInfluenceNoCharacter() {
        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.addStudent(new Student(CreatureColor.RED));

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.addStudent(new Student(CreatureColor.RED));
        island2.addStudent(new Student(CreatureColor.YELLOW));

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.addStudent(new Student(CreatureColor.BLUE));
        island3.addStudent(new Student(CreatureColor.RED));

        Player player = new Player("chiara", PlayerColor.BLACK);
        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        assertEquals(7, islandGroup.calculateInfluence(player));

    }

    @Test
    void calculateInfluenceCharacter6() {
        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.addStudent(new Student(CreatureColor.RED));

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.addStudent(new Student(CreatureColor.RED));
        island2.addStudent(new Student(CreatureColor.YELLOW));

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.addStudent(new Student(CreatureColor.BLUE));
        island3.addStudent(new Student(CreatureColor.RED));

        Player player = new Player("chiara", PlayerColor.BLACK);
        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character = cf.createCharacter(6);
        assertEquals(4, islandGroup.calculateInfluence(player, (CharacterInfluenceModifier) character));
    }

    /* need game and round to be implemented
    @Test
    void calculateInfluenceCharacter8() {
        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.addStudent(new Student(CreatureColor.RED));

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.addStudent(new Student(CreatureColor.RED));
        island2.addStudent(new Student(CreatureColor.YELLOW));

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.addStudent(new Student(CreatureColor.BLUE));
        island3.addStudent(new Student(CreatureColor.RED));

        Player player = new Player("chiara", PlayerColor.BLACK);
        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character1 = cf.createCharacter(8);
        assertEquals(9, islandGroup.calculateInfluence(player, (CharacterInfluenceModifier) character1));
    }
    */

    @Test
    void calculateInfluenceCharacter9() {
        island1 = new Island(1);
        island1.addTower(PlayerColor.BLACK);
        island1.addStudent(new Student(CreatureColor.RED));

        island2 = new Island(2);
        island2.addTower(PlayerColor.BLACK);
        island2.addStudent(new Student(CreatureColor.RED));
        island2.addStudent(new Student(CreatureColor.YELLOW));

        island3 = new Island(3);
        island3.addTower(PlayerColor.BLACK);
        island3.addStudent(new Student(CreatureColor.BLUE));
        island3.addStudent(new Student(CreatureColor.RED));

        Player player = new Player("chiara", PlayerColor.BLACK);
        player.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);
        islandGroup.addIsland(island3);

        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character2 = cf.createCharacter(9);
        ((CharacterInfluenceModifier) character2).setColorNoPoints(CreatureColor.RED);
        assertEquals(4, islandGroup.calculateInfluence(player, (CharacterInfluenceModifier) character2));

    }
}