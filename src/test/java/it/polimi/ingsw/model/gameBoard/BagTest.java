package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void getBag() {
        Bag bag= Bag.getBag();
        assertNotNull(Bag.getBag());
        assertEquals(bag, Bag.getBag());

    }

    @Test
    void addStudent() {
        Bag bag = Bag.getBag();

        bag.addStudent(new Student(CreatureColor.GREEN));
        bag.addStudent(new Student(CreatureColor.RED));
        bag.addStudent(new Student(CreatureColor.YELLOW));
        bag.addStudent(new Student(CreatureColor.PINK));
        bag.addStudent(new Student(CreatureColor.BLUE));

        assertEquals(5,bag.getStudents().size());

    }


    @Test
    void drawStudent() {
        Bag bag = Bag.getBag();

        bag.addStudent(new Student(CreatureColor.GREEN));
        bag.addStudent(new Student(CreatureColor.RED));
        bag.addStudent(new Student(CreatureColor.YELLOW));
        bag.addStudent(new Student(CreatureColor.PINK));
        bag.addStudent(new Student(CreatureColor.BLUE));

        bag.drawStudent();
        assertEquals(4,bag.size());
        bag.drawStudent();
        assertEquals(3,bag.size());
        bag.drawStudent();
        assertEquals(2,bag.size());
        bag.drawStudent();
        assertEquals(1,bag.size());
        bag.drawStudent();
        assertEquals(0,bag.size());
        assertNull(bag.drawStudent());




    }

    @Test
    void isEmpty() {
        Bag bag = Bag.getBag();
        bag.addStudent(new Student(CreatureColor.GREEN));
        assertFalse(bag.isEmpty());
        bag.drawStudent();
        assertTrue(bag.isEmpty());

    }

}