package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.Phase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfluenceCalculatorTest {

    private Game game;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;
    private Character character;
    private ConcreteCharacterFactory cf;

    @BeforeEach
    void setUp() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();
        cf = new ConcreteCharacterFactory(game);

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(1);
        priority.add(2);

        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        // Lobby phase
        Phase phase = game.getCurrentPhase();

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
    }

    @Test
    void effect(){
        Player p1 = game.getPlayingOrder().get(0);
        Player p2 = game.getPlayingOrder().get(1);

        character = cf.createCharacter(3);
        game.setActivatedCharacter(character);

        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().winProfessor(new Professor(CreatureColor.RED));

        p1.getBoard().addStudentToHall(CreatureColor.PINK);
        p1.getBoard().addStudentToHall(CreatureColor.PINK);
        p1.getBoard().winProfessor(new Professor(CreatureColor.PINK));

        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().winProfessor(new Professor(CreatureColor.GREEN));

        IslandGroup islandGroup1 = game.getIslandGroups().get(1);
        IslandGroup islandGroup2 = game.getIslandGroups().get(2);

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).addTower(game, p2.getColor());
        islandGroup1.setConquerorColor(p2.getColor());

        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup2.getIslands().get(0).addTower(game, p1.getColor());
        islandGroup2.setConquerorColor(p1.getColor());

        ((CharacterInfluenceCalculator)character).setIslandGroupIndex(12);
        assertThrows(OutOfBoundException.class, ()->character.effect());

        ((CharacterInfluenceCalculator)character).setIslandGroupIndex(1);

        try {
            character.effect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(p1.getColor(), islandGroup1.getConquerorColor());
        assertEquals(p1.getColor(), islandGroup2.getConquerorColor());

        assertEquals(6, p1.getBoard().getTowers());
        assertEquals(8, p2.getBoard().getTowers());
    }
}