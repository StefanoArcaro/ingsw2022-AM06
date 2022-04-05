package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    Bag bag;

    @BeforeEach
    void setUp() {
        bag = new Bag();
    }

    @Test
    void addStudent() {
        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.YELLOW);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);

        assertEquals(5,bag.size());
    }


    @Test
    void drawStudent() {
        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.YELLOW);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);

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
        bag.receiveStudent(CreatureColor.GREEN);
        assertFalse(bag.isEmpty());
        bag.drawStudent();
        assertTrue(bag.isEmpty());
    }
}