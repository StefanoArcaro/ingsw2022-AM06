package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getTableByColor() {
        Player player = new Player("nick", PlayerColor.BLACK);
        Board board = new Board(player);
        assertEquals(board.getTableByColor(CreatureColor.GREEN),board.getHall().get(0) );

    }


    @Test
    void removeStudentFromEntrance() {
        Player player = new Player("nick", PlayerColor.BLACK);
        Board board = new Board(player);
        ArrayList<Student> entrance=board.getEntrance();
        assertEquals(entrance.size(), 0);
        board.addStudentToEntrance(CreatureColor.BLUE);
        assertEquals(entrance.size(),1);
        board.removeStudentFromEntrance(CreatureColor.BLUE);
        assertEquals(entrance.size(), 0);
        assertFalse(board.removeStudentFromEntrance(CreatureColor.BLUE));


    }


    @Test
    void removeStudentFromHall() {
        Player player = new Player("nick", PlayerColor.BLACK);
        Board board = new Board(player);
        Table tablePink= board.getTableByColor(CreatureColor.PINK);
        Table tableRed= board.getTableByColor(CreatureColor.RED);
        board.addStudentToHall(CreatureColor.PINK);
        assertEquals(tablePink.getLength(), 1);
        assertEquals(tableRed.getLength(),0);
        board.removeStudentFromHall(CreatureColor.PINK);
        assertEquals(tablePink.getLength(), 0);
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
        assertEquals(board.getProfessors().size(),1);

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

        for (CreatureColor color : CreatureColor.values())
            board.winProfessor(new Professor(color));
        assertTrue(board.containsProfessor(CreatureColor.RED));
        assertTrue(board.containsProfessor(CreatureColor.GREEN));
        assertTrue(board.containsProfessor(CreatureColor.BLUE));
        assertTrue(board.containsProfessor(CreatureColor.PINK));
        assertTrue(board.containsProfessor(CreatureColor.YELLOW));
    }

}