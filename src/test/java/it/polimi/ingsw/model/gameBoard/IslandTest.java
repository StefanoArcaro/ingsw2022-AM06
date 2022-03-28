package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.model.gameBoard.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    private Island island;

    @BeforeEach
    void setUp() {
        island = new Island(1);
    }

    @AfterEach
    void tearDown() {
        island = null;
    }

    @Test
    void getStudents() {

        ArrayList<Student> expectedStudents = new ArrayList<Student> ();

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

        Student studentToAdd = new Student(CreatureColor.GREEN);
        Student studentToAdd2 = new Student(CreatureColor.GREEN);
        ArrayList<Student> expectedStudents = new ArrayList<Student> ();

        island.addStudent(studentToAdd);
        island.addStudent(studentToAdd2);

        expectedStudents.add(studentToAdd);
        expectedStudents.add(studentToAdd2);

        assertEquals(expectedStudents, island.getStudents());
    }

    @Test
    void removeTower() {
        assertFalse(island.removeTower());

        island.addTower(PlayerColor.BLACK);

        assertTrue(island.removeTower());
    }

    @Test
    void addTower(){
        assertTrue(island.addTower(PlayerColor.BLACK));

        assertFalse(island.addTower(PlayerColor.GRAY));
    }


    // the methods on the calculation of the influence are tested in islandGroupTest

}