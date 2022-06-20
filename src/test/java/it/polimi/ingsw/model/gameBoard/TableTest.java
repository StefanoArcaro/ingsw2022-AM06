package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    /** Tests the addition of a student to the table */
    @Test
    void addStudent() {
        Table table = new Table(CreatureColor.RED);
        assertEquals(0, table.getLength());
        for(int i = 0; i < 10; i++) {
            try {
                assertTrue(table.addStudent());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        assertEquals(10, table.getLength());
        assertThrows(TableFullException.class, table::addStudent);
    }

    /** Tests the removal of a student from the table */
    @Test
    void removeStudent() {
        Table table = new Table(CreatureColor.RED);
        try {
            table.addStudent();
        } catch (TableFullException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(table.removeStudent());
        assertFalse(table.removeStudent());
    }
}