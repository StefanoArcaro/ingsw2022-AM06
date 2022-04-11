package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.enumerations.CreatureColor;
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

    private Game game;
    private Phase phase;
    private PhaseFactory phaseFactory;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;
    private ConcreteCharacterFactory cf;

    @BeforeEach
    void setUp() {
        game = new Game();
        cf = new ConcreteCharacterFactory(game);
        phaseFactory = new PhaseFactory(game);
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(2);
        priority.add(1);

        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EASY);

        // Lobby phase
        phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // Prepare phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setWizardID(wizardIDs.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // Planning phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setPriority(priority.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
        wizardIDs = null;
        priority = null;
        cf = null;
    }

    @Test
    void effect() {
        game.getCurrentPlayer().receiveCoin();
        game.getCurrentPlayer().receiveCoin();
        game.getCurrentPlayer().receiveCoin();

        Player p2 = game.getPlayingOrder().get(0);  //current player
        Player p1 = game.getPlayingOrder().get(1);

        phase = phaseFactory.createPhase(GameState.MOVE_MOTHER_NATURE_PHASE);
        phase.setNumberOfSteps(1);
        game.setCurrentPhase(phase);

        Character character = cf.createCharacter(2);
        game.addDrawnCharacter(character);

        try {
            ((ActionPhase)game.getCurrentPhase()).playCharacter(2);
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(1, p2.getCoins());

        assertEquals(expectedProf1, p1.getBoard().getProfessors().stream()
                                            .map(Creature::getColor).collect(Collectors.toList()));

        assertEquals(expectedProf2, p2.getBoard().getProfessors().stream()
                                             .map(Creature::getColor).collect(Collectors.toList()));
    }
}