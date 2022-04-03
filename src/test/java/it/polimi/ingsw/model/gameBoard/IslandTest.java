package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        for(int i=0; i<numberOfPlayers; i++){
            Game.getGame().removePlayer(i);
        }

    }

    @Test
    void addTower(){
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
        for(int i=0; i<numberOfPlayers; i++){
            Game.getGame().removePlayer(i);
        }
    }


    @Test
    void getStudents() {

        Island island = new Island(1);

        ArrayList<Student> expectedStudents = new ArrayList<> ();

        assertEquals(expectedStudents, island.getStudents());

        Student s1 = new Student(CreatureColor.BLUE);
        Student s2 = new Student(CreatureColor.RED);
        Student s3 = new Student(CreatureColor.GREEN);
        Student s4 = new Student(CreatureColor.PINK);
        Student s5 = new Student(CreatureColor.YELLOW);

        island.addStudent(s1);
        island.addStudent(s2);
        island.addStudent(s3);
        island.addStudent(s4);
        island.addStudent(s5);

        expectedStudents.add(s1);
        expectedStudents.add(s2);
        expectedStudents.add(s3);
        expectedStudents.add(s4);
        expectedStudents.add(s5);

        assertEquals(expectedStudents, island.getStudents());
    }

    @Test
    void addStudent() {
        Island island = new Island(1);

        Student studentToAdd = new Student(CreatureColor.GREEN);
        Student studentToAdd2 = new Student(CreatureColor.GREEN);
        ArrayList<Student> expectedStudents = new ArrayList<> ();

        island.addStudent(studentToAdd);
        island.addStudent(studentToAdd2);

        expectedStudents.add(studentToAdd);
        expectedStudents.add(studentToAdd2);

        assertEquals(expectedStudents, island.getStudents());
    }




    // the methods on the calculation of the influence are tested in islandGroupTest

}