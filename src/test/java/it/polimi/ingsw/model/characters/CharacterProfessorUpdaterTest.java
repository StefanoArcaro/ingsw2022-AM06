package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.phases.PhaseFactory;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Professor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CharacterProfessorUpdaterTest {

    Game game;
    ConcreteCharacterFactory cf;
    Character character;

    @BeforeEach
    void setUp() {
        game = new Game();

        cf = new ConcreteCharacterFactory(game);
    }

    @AfterEach
    void tearDown() {
        cf = null;
        character = null;
    }

    @Test
    void effect() {
        Player p1 = new Player("X", PlayerColor.WHITE);
        Player p2 = new Player("Y", PlayerColor.BLACK);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.setCurrentPlayer(p2);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));

        character = cf.createCharacter(2);
        game.getCurrentPhase().activateCharacter(character);

        Professor prof1 = new Professor(CreatureColor.RED);
        Professor prof3 = new Professor(CreatureColor.PINK);
        Professor prof4 = new Professor(CreatureColor.GREEN);

        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().winProfessor(prof1);
        p1.getBoard().addStudentToHall(CreatureColor.PINK);
        p1.getBoard().winProfessor(prof3);


        p2.getBoard().addStudentToHall(CreatureColor.RED);
        p2.getBoard().addStudentToHall(CreatureColor.RED);
        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().winProfessor(prof4);


        ArrayList<CreatureColor> expectedProf1 =
                new ArrayList<>(Collections.singletonList(CreatureColor.PINK));

        ArrayList<CreatureColor> expectedProf2 =
                new ArrayList<>(Arrays.asList(CreatureColor.GREEN, CreatureColor.RED));


        character.effect();


        assertEquals(expectedProf1, p1.getBoard().getProfessors().stream()
                                            .map(Creature::getColor).collect(Collectors.toList()));

        assertEquals(expectedProf2, p2.getBoard().getProfessors().stream()
                                             .map(Creature::getColor).collect(Collectors.toList()));
    }
}