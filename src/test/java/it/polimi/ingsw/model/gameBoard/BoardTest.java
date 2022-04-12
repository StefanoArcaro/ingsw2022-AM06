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

class BoardTest {

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

    @Test
    void removeStudentFromEntrance() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        assertEquals(0, board.getEntrance().getStudents().size());
        board.addStudentToEntrance(CreatureColor.BLUE);
        assertEquals(1, board.getEntrance().getStudents().size());
        board.removeStudentFromEntrance(CreatureColor.BLUE);
        assertEquals(0, board.getEntrance().getStudents().size());
        assertFalse(board.removeStudentFromEntrance(CreatureColor.BLUE));
    }

    @Test
    void removeStudentFromHall() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        Table tablePink = board.getHall().getTableByColor(CreatureColor.PINK);
        Table tableRed = board.getHall().getTableByColor(CreatureColor.RED);
        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(1, tablePink.getLength());
        assertEquals(0, tableRed.getLength());
        board.removeStudentFromHall(CreatureColor.PINK);
        assertEquals(0, tablePink.getLength());
        assertFalse(board.removeStudentFromHall(CreatureColor.PINK));
    }

    @Test
    void winProfessor() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        assertTrue(board.getProfessors().isEmpty());
        Professor professor = new Professor(CreatureColor.RED);
        board.winProfessor(professor);
        assertFalse(board.getProfessors().isEmpty());
        board.winProfessor(professor);
        assertEquals(1, board.getProfessors().size());
    }

    @Test
    void loseProfessor() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        assertTrue(board.getProfessors().isEmpty());
        Professor professorRed = new Professor(CreatureColor.RED);
        Professor professorGreen = new Professor(CreatureColor.GREEN);
        board.loseProfessor(professorRed);
        assertTrue(board.getProfessors().isEmpty());
        board.winProfessor(professorRed);
        board.loseProfessor(professorGreen);
        assertFalse(board.getProfessors().isEmpty());
        board.loseProfessor(professorRed);
        assertTrue(board.getProfessors().isEmpty());
    }

    @Test
    void loseProfessorByColor() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        Professor professorRed = new Professor(CreatureColor.RED);

        assertNull(board.loseProfessorByColor(CreatureColor.RED));
        board.winProfessor(professorRed);
        assertEquals(professorRed, board.loseProfessorByColor(CreatureColor.RED));
        assertNull(board.loseProfessorByColor(CreatureColor.RED));
    }

    @Test
    void containsProfessor() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);

        for(CreatureColor color : CreatureColor.values()) {
            board.winProfessor(new Professor(color));
        }

        assertTrue(board.containsProfessor(CreatureColor.RED));
        assertTrue(board.containsProfessor(CreatureColor.GREEN));
        assertTrue(board.containsProfessor(CreatureColor.BLUE));
        assertTrue(board.containsProfessor(CreatureColor.PINK));
        assertTrue(board.containsProfessor(CreatureColor.YELLOW));
    }

    @Test
    void addStudentToHall() {
        Player player = new Player(game, "nick", PlayerColor.BLACK);
        Board board = new Board(player);
        player.getGame().setGameMode(GameMode.EXPERT);

        assertEquals(0,player.getGame().getTreasury());
        assertEquals(0,player.getCoins());

        player.getGame().setTreasury(2);

        for(int i=1;i<3;i++) {
            board.addStudentToHall(CreatureColor.PINK);
            assertEquals(i, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
            assertEquals(0,player.getCoins());
        } // now there are 2 students in the considered table

        assertEquals(2,player.getGame().getTreasury());

        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(3, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
        assertEquals(1,player.getCoins());
        assertEquals(1,player.getGame().getTreasury());

        for(int i=4;i<6;i++) {
            board.addStudentToHall(CreatureColor.PINK);
            assertEquals(i, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
            assertEquals(1, player.getCoins());
        } // now there are 5 students in the considered table

        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(6, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
        assertEquals(2,player.getCoins());
        assertEquals(0,player.getGame().getTreasury());

        for(int i=7;i<9;i++) {
            board.addStudentToHall(CreatureColor.PINK);
            assertEquals(i, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
            assertEquals(2, player.getCoins());
        } // now there are 8 students in the considered table

        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(9, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
        assertEquals(2, player.getCoins());
        /*
        even if the position was a multiple on 3, the player didn't receive an extra
        coins because the treasury was empty!
        */
        assertEquals(0,player.getGame().getTreasury());
        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(10, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
        // I don't expect an error message

        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(10, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
        // I expect one error message

        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(10, board.getHall().getTableByColor(CreatureColor.PINK).getLength());
        // I expect two error messages
    }



}