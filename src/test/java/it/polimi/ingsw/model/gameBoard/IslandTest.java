package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    @Test
    void removeTower() {
        System.out.println("removeTower");

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(5);

        Island island = new Island(1);

        assertFalse(island.removeTower());

        assertTrue(island.addTower(player.getColor()));
        System.out.println(player);
        assertEquals(4, player.getBoard().getTowers());
        assertTrue(island.removeTower());
        assertEquals(5, player.getBoard().getTowers());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(i);
        }
    }

    @Test
    void addTower() {
        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.BLACK);
        game.addPlayer(player);
        player.getBoard().setTowers(6);

        Island island = new Island(1);

        assertEquals(6, player.getBoard().getTowers());
        assertTrue(island.addTower(PlayerColor.BLACK));
        assertEquals(5, player.getBoard().getTowers());

        assertFalse(island.addTower(PlayerColor.GRAY));
        assertEquals(5, player.getBoard().getTowers());

        int numberOfPlayers = game.getPlayers().size();
        for(int i = 0; i < numberOfPlayers; i++) {
            Game.getGame().removePlayer(i);
        }
    }

    @Test
    void getStudents() {
        Island island = new Island(1);

        ArrayList<Student> expectedStudents = new ArrayList<>();

        assertEquals(expectedStudents, island.getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));

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