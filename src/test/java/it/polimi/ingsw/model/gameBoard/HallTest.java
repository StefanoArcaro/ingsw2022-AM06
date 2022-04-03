package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {


    @Test
    void getTableByColor() {
        Player player = new Player("nick", PlayerColor.BLACK);
        Board board = new Board(player);
        assertEquals(board.getHall().getTableByColor(CreatureColor.GREEN), board.getHall().getStudents().get(0) );

    }

    @Test
    void studentInTable() {
        Player player = new Player("Chiara", PlayerColor.BLACK);
        Board board = new Board(player);
        board.addStudentToHall(CreatureColor.RED);
        board.addStudentToHall(CreatureColor.PINK);
        board.addStudentToHall(CreatureColor.GREEN);

        assertTrue(board.getHall().studentInTable(CreatureColor.RED));
        assertTrue(board.getHall().studentInTable(CreatureColor.PINK));
        assertTrue(board.getHall().studentInTable(CreatureColor.GREEN));
        assertFalse(board.getHall().studentInTable(CreatureColor.BLUE));
        assertFalse(board.getHall().studentInTable(CreatureColor.YELLOW));
    }
}