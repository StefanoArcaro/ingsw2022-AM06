package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Test
    void removeTower() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        Island island = new Island(1);

        assertFalse(island.removeTower(game));

        assertTrue(island.addTower(game, player.getColor()));
        assertEquals(4, player.getBoard().getTowers());
        assertTrue(island.removeTower(game));
        assertEquals(5, player.getBoard().getTowers());
    }

    @Test
    void addTower() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(6);

        Island island = new Island(1);

        assertEquals(6, player.getBoard().getTowers());
        assertTrue(island.addTower(game, PlayerColor.BLACK));
        assertEquals(5, player.getBoard().getTowers());

        assertFalse(island.addTower(game, PlayerColor.GRAY));
        assertEquals(5, player.getBoard().getTowers());
    }

    @Test
    void getStudents() {
        Island island = new Island(1);

        ArrayList<Student> expectedStudents = new ArrayList<>();

        island.receiveStudent(CreatureColor.BLUE);
        island.receiveStudent(CreatureColor.RED);
        island.receiveStudent(CreatureColor.GREEN);
        island.receiveStudent(CreatureColor.PINK);
        island.receiveStudent(CreatureColor.YELLOW);

        expectedStudents.add(new Student(CreatureColor.BLUE));
        expectedStudents.add(new Student(CreatureColor.RED));
        expectedStudents.add(new Student(CreatureColor.GREEN));
        expectedStudents.add(new Student(CreatureColor.PINK));
        expectedStudents.add(new Student(CreatureColor.YELLOW));

        assertEquals(expectedStudents.stream().map(Creature::getColor).collect(Collectors.toList()), island.getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));
    }

    @Test
    void addStudent() {
        Island island = new Island(1);

        Student studentToAdd = new Student(CreatureColor.GREEN);
        Student studentToAdd2 = new Student(CreatureColor.GREEN);
        ArrayList<Student> expectedStudents = new ArrayList<>();

        island.receiveStudent(CreatureColor.GREEN);
        island.receiveStudent(CreatureColor.GREEN);

        expectedStudents.add(studentToAdd);
        expectedStudents.add(studentToAdd2);

        assertEquals(expectedStudents.stream().map(Creature::getColor).collect(Collectors.toList()), island.getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));
    }
}