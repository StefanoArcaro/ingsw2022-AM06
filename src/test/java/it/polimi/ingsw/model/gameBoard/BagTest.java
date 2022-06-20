package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    Bag bag;

    /** Initializes values */
    @BeforeEach
    void setUp() {
        bag = new Bag();
    }

    /** Tests the addition of students in the bag */
    @Test
    void addStudent() {
        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.YELLOW);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);

        assertEquals(5,bag.size());
    }


    /** Tests the drawing of students from the bag */
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

    /** Tests if the bag is empty */
    @Test
    void isEmpty() {
        bag.receiveStudent(CreatureColor.GREEN);
        assertFalse(bag.isEmpty());
        bag.drawStudent();
        assertTrue(bag.isEmpty());
    }
}