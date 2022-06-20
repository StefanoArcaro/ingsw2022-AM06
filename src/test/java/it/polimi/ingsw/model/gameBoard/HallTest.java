package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setGameMode(GameMode.EXPERT);
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    /** Tests the return of the table of a specific color */
    @Test
    void getTableByColor() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        assertEquals(board.getHall().getTableByColor(CreatureColor.GREEN), board.getHall().getStudents().get(0));
    }

    /** Tests whether there's at least one student in the table of a specific color */
    @Test
    void studentInTable() {
        Player player = new Player(game, "Chiara", PlayerColor.BLACK);
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