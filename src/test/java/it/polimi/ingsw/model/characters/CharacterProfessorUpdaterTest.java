package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.model.phases.MoveMotherNaturePhase;
import it.polimi.ingsw.model.phases.Phase;
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
    PhaseFactory phaseFactory;
    Phase phase;

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

        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        phaseFactory = new PhaseFactory(game);
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        phase = phaseFactory.createPhase(GameState.PREPARE_PHASE);
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        phase = phaseFactory.createPhase(GameState.PLANNING_PHASE);
        try {
            phase.play();
        } catch (ExceededStepsException | NoAvailableCloudException e) {
            e.printStackTrace();
        }

        Player p1 = game.getPlayers().get(1);
        Player p2 = game.getPlayers().get(0);

        p2.receiveCoin();
        p2.receiveCoin();

        game.setCurrentPlayer(p2);

        phase = phaseFactory.createPhase(GameState.MOVE_MOTHER_NATURE_PHASE);
        ((MoveMotherNaturePhase)phase).setNumberOfSteps(1);
        game.setCurrentPhase(phase);

        character = cf.createCharacter(2);
        try {
            ((ActionPhase)game.getCurrentPhase()).playCharacter(character);
        } catch (NoAvailableBanCardsException | OutOfBoundException | NoAvailableColorException | NotEnoughMoneyException e) {
            e.printStackTrace();
        }

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

        try {
            character.effect();
        } catch (NoAvailableBanCardsException | OutOfBoundException | NoAvailableColorException e) {
            e.printStackTrace();
        }

        assertEquals(1, p2.getCoins());

        assertEquals(expectedProf1, p1.getBoard().getProfessors().stream()
                                            .map(Creature::getColor).collect(Collectors.toList()));

        assertEquals(expectedProf2, p2.getBoard().getProfessors().stream()
                                             .map(Creature::getColor).collect(Collectors.toList()));
    }
}