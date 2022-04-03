package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void addStudent() {
        Table table = new Table(CreatureColor.RED);
        assertEquals(table.getLength(),0);
        for(int i=0;i<10; i++)
            table.addStudent();
        assertEquals(table.getLength(),10);
        assertFalse(table.addStudent());

    }

    @Test
    void removeStudent() {
        Table table = new Table(CreatureColor.RED);
        table.addStudent();
        assertTrue(table.removeStudent());
        assertFalse(table.removeStudent());

    }

}