package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.model.gameBoard.Professor;
import it.polimi.ingsw.model.gameBoard.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfluenceCalculatorTest {

    ConcreteCharacterFactory cf;
    Character character;

    @BeforeEach
    void setUp() {
        cf = new ConcreteCharacterFactory();
        character = cf.createCharacter(3);
    }

    @AfterEach
    void tearDown() {
        cf = null;
        character = null;
    }

    //need calculate influence in game to be implemented
    /*
    @Test
    void effect(){
        Game game = Game.getGame();
        Player p1 = new Player("X", PlayerColor.WHITE);
        Player p2 = new Player("Y", PlayerColor.BLACK);
        game.addPlayer(p1);
        game.addPlayer(p2);
        Round round = new Round(game.getPlayers(), 0);
        Game.getGame().setCurrentRound(round);

        character = cf.createCharacter(3);

        p1.getBoard().setTowers(3);

        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().winProfessor(new Professor(CreatureColor.RED));

        p1.getBoard().addStudentToHall(CreatureColor.PINK);
        p1.getBoard().addStudentToHall(CreatureColor.PINK);
        p1.getBoard().winProfessor(new Professor(CreatureColor.PINK));

        p2.getBoard().setTowers(2);

        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().winProfessor(new Professor(CreatureColor.GREEN));

        Island island1 = new Island(1);
        island1.addStudent(new Student(CreatureColor.RED));
        island1.addStudent(new Student(CreatureColor.RED));
        island1.addStudent(new Student(CreatureColor.GREEN));
        island1.addTower(p2.getColor());

        Island island2 = new Island(2);
        island2.addStudent(new Student(CreatureColor.PINK));
        island2.addStudent(new Student(CreatureColor.PINK));
        island2.addTower(p1.getColor());

        ((CharacterInfluenceCalculator)character).setIslandGroupIndex(1);
        character.effect();

        assertEquals(p1.getColor(), island1.getTower());
        assertEquals(p1.getColor(), island2.getTower());

        assertEquals(2, p1.getBoard().getTowers());
        assertEquals(3, p2.getBoard().getTowers());
    }*/


}