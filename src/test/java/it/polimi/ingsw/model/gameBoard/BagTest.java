package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @BeforeEach
    void setUp() {
        Bag bag = Bag.getBag();
        int bagSize = bag.size();
        for(int i = 0; i < bagSize; i++) {
            bag.drawStudent();
        }
    }

    @Test
    void getBag() {
        Bag bag = Bag.getBag();
        assertNotNull(Bag.getBag());
        assertEquals(bag, Bag.getBag());
    }

    @Test
    void addStudent() {
        Bag bag = Bag.getBag();

        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.YELLOW);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);

        assertEquals(5,bag.size());

        int bagSize = bag.size();
        for(int i = 0; i < bagSize; i++) {
            bag.drawStudent();
        }
    }


    @Test
    void drawStudent() {
        Bag bag = Bag.getBag();

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

        int bagSize = bag.size();
        for(int i = 0; i < bagSize; i++) {
            bag.drawStudent();
        }
    }

    @Test
    void isEmpty() {
        Bag bag = Bag.getBag();
        bag.receiveStudent(CreatureColor.GREEN);
        assertFalse(bag.isEmpty());
        bag.drawStudent();
        assertTrue(bag.isEmpty());

        int bagSize = bag.size();
        for(int i = 0; i < bagSize; i++) {
            bag.drawStudent();
        }
    }
}