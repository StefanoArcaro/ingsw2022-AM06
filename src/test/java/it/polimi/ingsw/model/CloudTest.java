package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameBoard.Bag;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Student;
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
        bag = Bag.getBag();

        bag.addStudent(new Student(CreatureColor.GREEN));
        bag.addStudent(new Student(CreatureColor.RED));
        bag.addStudent(new Student(CreatureColor.PINK));
        bag.addStudent(new Student(CreatureColor.BLUE));
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

        assertEquals(CreatureColor.BLUE, students.get(0).getColor());
        assertEquals(CreatureColor.PINK, students.get(1).getColor());
        assertEquals(CreatureColor.RED, students.get(2).getColor());
        assertEquals(3, students.size());
    }

    @Test
    void fill_three_players() {
        cloud.fill(bag, 3);

        ArrayList<Student> students = cloud.getStudents();

        assertEquals(CreatureColor.BLUE, students.get(0).getColor());
        assertEquals(CreatureColor.PINK, students.get(1).getColor());
        assertEquals(CreatureColor.RED, students.get(2).getColor());
        assertEquals(CreatureColor.GREEN, students.get(3).getColor());
        assertEquals(4, students.size());
    }

    @Test
    void empty_two_players() {
        cloud.fill(bag, 2);
        ArrayList<Student> result = cloud.empty();

        assertEquals(CreatureColor.BLUE, result.get(0).getColor());
        assertEquals(CreatureColor.PINK, result.get(1).getColor());
        assertEquals(CreatureColor.RED, result.get(2).getColor());
        assertEquals(3, result.size());
        assertEquals(0, cloud.getStudents().size());
    }

    @Test
    void empty_three_players() {
        cloud.fill(bag, 3);
        ArrayList<Student> result = cloud.empty();

        assertEquals(CreatureColor.BLUE, result.get(0).getColor());
        assertEquals(CreatureColor.PINK, result.get(1).getColor());
        assertEquals(CreatureColor.RED, result.get(2).getColor());
        assertEquals(CreatureColor.GREEN, result.get(3).getColor());
        assertEquals(4, result.size());
        assertEquals(0, cloud.getStudents().size());
    }
}