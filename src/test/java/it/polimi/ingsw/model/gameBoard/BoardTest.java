package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    @Test
    void removeStudentFromEntrance() {
        Player player = new Player("nick", PlayerColor.BLACK);
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
        Player player = new Player("nick", PlayerColor.BLACK);
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
        Player player = new Player("nick", PlayerColor.BLACK);
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
        Player player = new Player("nick", PlayerColor.BLACK);
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
        Player player = new Player("nick", PlayerColor.BLACK);
        Board board = new Board(player);
        Professor professorRed = new Professor(CreatureColor.RED);

        assertNull(board.loseProfessorByColor(CreatureColor.RED));
        board.winProfessor(professorRed);
        assertEquals(professorRed, board.loseProfessorByColor(CreatureColor.RED));
        assertNull(board.loseProfessorByColor(CreatureColor.RED));
    }

    @Test
    void containsProfessor() {
        Player player = new Player("nick", PlayerColor.BLACK);
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
}