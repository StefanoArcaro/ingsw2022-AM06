package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.model.phases.Phase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CharacterMoverTest {

    private Game game;
    private Bag bag;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;
    private Character character;
    private ConcreteCharacterFactory cf;

    @BeforeEach
    void setUp() {
        game = new Game();
        bag = game.getBag();
        cf = new ConcreteCharacterFactory(game);
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
        cf = null;
    }

    @Test
    void initialPreparation() {
        character = cf.createCharacter(1);
        character.initialPreparation();
        assertEquals(4, character.getStudents().size());
        character = cf.createCharacter(7);
        character.initialPreparation();
        assertEquals(6, character.getStudents().size());
    }

    @Test
    void effect1_OK() {
        character = cf.createCharacter(1);

        bag.receiveStudent(CreatureColor.RED);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        game.getIslandByID(1).receiveStudent(CreatureColor.GREEN);
        game.getIslandByID(1).receiveStudent(CreatureColor.PINK);

        ((CharacterMover) character).setFirstColor(CreatureColor.RED);
        ((CharacterMover) character).setIslandID(1);
        try {
            character.effect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(4, character.getStudents().size());

        List<CreatureColor> expectedColor = new ArrayList<>(Arrays.asList(CreatureColor.GREEN, CreatureColor.PINK, CreatureColor.RED));

        assertTrue(game.getIslandByID(1).getStudents().stream().map(Creature::getColor).toList().containsAll(expectedColor));

        assertEquals(4, character.getStudents().size());
    }

    @Test
    void effect1_KO() {
        character = cf.createCharacter(1);

        bag.receiveStudent(CreatureColor.RED);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        Island island = new Island(1);
        island.receiveStudent(CreatureColor.GREEN);
        island.receiveStudent(CreatureColor.PINK);

        ((CharacterMover) character).setFirstColor(CreatureColor.YELLOW);
        ((CharacterMover) character).setIslandID(1);

        assertThrows(NoAvailableColorException.class, ()->character.effect());

        assertEquals(CreatureColor.RED, character.getStudents().get(0).getColor());
        assertEquals(CreatureColor.RED, character.getStudents().get(1).getColor());
        assertEquals(CreatureColor.GREEN, character.getStudents().get(2).getColor());
        assertEquals(CreatureColor.BLUE, character.getStudents().get(3).getColor());
        assertEquals(4, character.getStudents().size());

        List<CreatureColor> expectedColor = new ArrayList<>(Arrays.asList(CreatureColor.GREEN, CreatureColor.PINK));

        assertTrue(island.getStudents().stream().map(Creature::getColor).toList().containsAll(expectedColor));
        assertEquals(2, island.getStudents().size());

        ((CharacterMover) character).setFirstColor(CreatureColor.RED);
        ((CharacterMover) character).setIslandID(13);

        assertThrows(OutOfBoundException.class, ()->character.effect());

        assertEquals(CreatureColor.RED, character.getStudents().get(0).getColor());
        assertEquals(CreatureColor.RED, character.getStudents().get(1).getColor());
        assertEquals(CreatureColor.GREEN, character.getStudents().get(2).getColor());
        assertEquals(CreatureColor.BLUE, character.getStudents().get(3).getColor());
        assertEquals(4, character.getStudents().size());

    }

    @Test
    void effect7_OK() {
        character = cf.createCharacter(7);
        //game.setActivatedCharacter(character);

        game.addDrawnCharacter(character);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.BLUE);
        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.PINK);

        ((CharacterMover) character).setFirstColor(CreatureColor.RED);
        ((CharacterMover) character).setSecondColor(CreatureColor.BLUE);

        try{
            ((ActionPhase)(game.getCurrentPhase())).playCharacter(character.getCharacterID());
        }catch(Exception e){
            e.printStackTrace();
        }

        assertEquals(6, character.getStudents().size());
        assertEquals(9, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
    }

    @Test
    void effect7_KO() {
        character = cf.createCharacter(7);
        game.setActivatedCharacter(character);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.BLUE);
        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.PINK);

        ((CharacterMover) character).setFirstColor(CreatureColor.PINK);
        ((CharacterMover) character).setSecondColor(CreatureColor.BLUE);

        ArrayList<CreatureColor> expectedStudents = new ArrayList<>(Arrays.asList(CreatureColor.RED, CreatureColor.RED,
                CreatureColor.GREEN,CreatureColor.BLUE, CreatureColor.GREEN, CreatureColor.BLUE));

        assertThrows(NoAvailableColorException.class, ()-> character.effect());

        assertEquals(6, character.getStudents().size());
        assertEquals(expectedStudents, character.students.stream().map(Creature::getColor).collect(Collectors.toList()));
        assertEquals(9, game.getCurrentPlayer().getBoard().getEntrance().getStudents().size());
    }

    @Test
    void effect10_OK() {
        character = cf.createCharacter(10);

        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.BLUE);
        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.PINK);

        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.PINK);
        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.RED);

        ((CharacterMover) character).setFirstColor(CreatureColor.PINK);
        ((CharacterMover) character).setSecondColor(CreatureColor.BLUE);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0,1,0,0,1));

        ArrayList<CreatureColor> expectedEntrance = new ArrayList<>(Arrays.asList(CreatureColor.PINK, CreatureColor.PINK));

        try {
            character.effect();
        } catch (NoAvailableBanCardsException | OutOfBoundException | NoAvailableColorException e) {
            e.printStackTrace();
        }

        assertTrue(game.getCurrentPlayer().getBoard().getEntrance().getStudents()
                .stream().map(Creature::getColor).toList().containsAll(expectedEntrance));
        assertEquals(expectedHall, game.getCurrentPlayer().getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
    }

    @Test
    void effect10_KO() {
        character = cf.createCharacter(10);
        game.setActivatedCharacter(character);

        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.BLUE);
        game.getCurrentPlayer().getBoard().addStudentToEntrance(CreatureColor.PINK);

        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.PINK);
        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.RED);

        ((CharacterMover) character).setFirstColor(CreatureColor.GREEN);
        ((CharacterMover) character).setSecondColor(CreatureColor.BLUE);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0,1,0,1,0));

        List<CreatureColor> expectedEntrance = game.getCurrentPlayer().getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList();

        assertThrows(NoAvailableColorException.class, ()->character.effect());

        assertTrue(game.getCurrentPlayer().getBoard().getEntrance().getStudents()
                .stream().map(Creature::getColor).toList().containsAll(expectedEntrance));
        assertEquals(expectedHall, game.getCurrentPlayer().getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
    }

    @Test
    void effect11_OK() {
        character = cf.createCharacter(11);

        bag.receiveStudent(CreatureColor.RED);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.RED);
        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.PINK);

        ((CharacterMover)character).setFirstColor(CreatureColor.RED);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0, 2, 0, 1, 0));

        try {
            character.effect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedHall, game.getCurrentPlayer().getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(4, character.getStudents().size());
    }


    @Test
    void effect11_KO() {
        character = cf.createCharacter(11);

        bag.receiveStudent(CreatureColor.RED);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.RED);
        game.getCurrentPlayer().getBoard().addStudentToHall(CreatureColor.PINK);

        ((CharacterMover)character).setFirstColor(CreatureColor.PINK);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0,1,0,1,0));

        assertThrows(NoAvailableColorException.class, ()->character.effect());

        assertEquals(expectedHall, game.getCurrentPlayer().getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(4, character.getStudents().size());
    }

    @Test
    void effect12_OK() {
        Player p1 = game.getPlayingOrder().get(0);
        Player p2 = game.getPlayingOrder().get(1);

        character = cf.createCharacter(12);

        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);

        ArrayList<Integer> expectedHall1 = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

        p2.getBoard().addStudentToHall(CreatureColor.RED);
        p2.getBoard().addStudentToHall(CreatureColor.RED);
        p2.getBoard().addStudentToHall(CreatureColor.RED);
        p2.getBoard().addStudentToHall(CreatureColor.RED);

        ArrayList<Integer> expectedHall2 = new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0));

        ((CharacterMover)character).setFirstColor(CreatureColor.RED);
        try {
            character.effect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedHall1, p1.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(expectedHall2, p2.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
    }

    @Test
    void effect12_KO() {
        Player p1 = game.getPlayingOrder().get(0);
        Player p2 = game.getPlayingOrder().get(1);

        character = cf.createCharacter(12);

        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.GREEN);
        p1.getBoard().addStudentToHall(CreatureColor.PINK);

        ArrayList<Integer> expectedHall1 = new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0));

        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().addStudentToHall(CreatureColor.GREEN);

        ArrayList<Integer> expectedHall2 = new ArrayList<>(Arrays.asList(2, 0, 0, 0, 0));

        ((CharacterMover)character).setFirstColor(CreatureColor.RED);

        try {
            character.effect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedHall1, p1.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(expectedHall2, p2.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
    }
}