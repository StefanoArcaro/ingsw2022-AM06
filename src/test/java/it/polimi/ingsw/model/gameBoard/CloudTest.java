package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    private Cloud cloud;
    private Bag bag;

    @BeforeEach
    void setUp() {
        cloud = new Cloud(1);
        bag = new Bag();

        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);
    }

    @AfterEach
    void tearDown() {
        cloud = null;
        bag = null;
    }

    @Test
    void constructorTest() {
        assertEquals(1, cloud.getCloudID());
        assertEquals(0, cloud.getStudents().size());
    }

    @Test
    void fill_two_players() {
        cloud.fill(bag, 2);

        ArrayList<Student> students = cloud.getStudents();

        assertEquals(3, students.size());
    }

    @Test
    void fill_three_players() {
        cloud.fill(bag, 3);

        ArrayList<Student> students = cloud.getStudents();

        assertEquals(4, students.size());
    }

    @Test
    void empty_two_players() {
        cloud.fill(bag, 2);
        ArrayList<Student> result = cloud.empty();

        assertEquals(3, result.size());
        assertEquals(0, cloud.getStudents().size());
    }

    @Test
    void empty_three_players() {
        cloud.fill(bag, 3);
        ArrayList<Student> result = cloud.empty();

        assertEquals(4, result.size());
        assertEquals(0, cloud.getStudents().size());
    }
}