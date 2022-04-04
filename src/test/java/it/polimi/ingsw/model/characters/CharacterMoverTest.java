package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CharacterMoverTest {

    Game game;
    PhaseFactory phaseFactory;
    Phase phase;
    ConcreteCharacterFactory cf;
    Character character;
    Bag bag;

    @BeforeEach
    void setUp() {
        Game.getGame().resetGame();
        game = Game.getGame();

        cf = new ConcreteCharacterFactory();
        bag = Bag.getBag();

        for(int i=1; i<=12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
    }

    @AfterEach
    void tearDown() {
        cf = null;
        character = null;
        bag = null;
    }

    @Test
    void initialPreparation() {
        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);
        bag.receiveStudent(CreatureColor.YELLOW);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.GREEN);
        bag.receiveStudent(CreatureColor.RED);
        bag.receiveStudent(CreatureColor.PINK);
        bag.receiveStudent(CreatureColor.BLUE);
        bag.receiveStudent(CreatureColor.YELLOW);
        bag.receiveStudent(CreatureColor.RED);

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

        Game game = Game.getGame();

        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        phaseFactory = new PhaseFactory();

        // TODO you can remove this and add the players to the game yourself
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        phase.play();

        game.getIslandByID(1).receiveStudent(CreatureColor.GREEN);
        game.getIslandByID(1).receiveStudent(CreatureColor.PINK);


        ((CharacterMover) character).setFromColor(CreatureColor.RED);
        ((CharacterMover) character).setIslandID(1);
        character.effect();
        assertEquals(4, character.getStudents().size());

        assertEquals(CreatureColor.GREEN, game.getIslandByID(1).getStudents().get(0).getColor());
        assertEquals(CreatureColor.PINK, game.getIslandByID(1).getStudents().get(1).getColor());
        assertEquals(CreatureColor.RED, game.getIslandByID(1).getStudents().get(2).getColor());

        assertEquals(4, character.getStudents().size());
    }


    @Test
    void effect1_KO() {
        character = cf.createCharacter(1);

        Bag.getBag().receiveStudent(CreatureColor.RED);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        Island island = new Island(1);
        island.receiveStudent(CreatureColor.GREEN);
        island.receiveStudent(CreatureColor.PINK);

        ((CharacterMover) character).setFromColor(CreatureColor.YELLOW);
        character.effect();

        assertEquals(CreatureColor.RED, character.getStudents().get(0).getColor());
        assertEquals(CreatureColor.RED, character.getStudents().get(1).getColor());
        assertEquals(CreatureColor.GREEN, character.getStudents().get(2).getColor());
        assertEquals(CreatureColor.BLUE, character.getStudents().get(3).getColor());
        assertEquals(4, character.getStudents().size());

        assertEquals(CreatureColor.GREEN, island.getStudents().get(0).getColor());
        assertEquals(CreatureColor.PINK, island.getStudents().get(1).getColor());
        assertEquals(2, island.getStudents().size());
    }


    @Test
    void effect7_OK() {
        character = cf.createCharacter(7);

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.WHITE);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));


        player.getBoard().addStudentToEntrance(CreatureColor.BLUE);
        player.getBoard().addStudentToEntrance(CreatureColor.PINK);

        ((CharacterMover) character).setFromColor(CreatureColor.RED);
        ((CharacterMover) character).setToColor(CreatureColor.BLUE);

        ArrayList<CreatureColor> expectedStudents = new ArrayList<>(Arrays.asList(CreatureColor.RED,
                CreatureColor.GREEN,CreatureColor.BLUE, CreatureColor.GREEN, CreatureColor.BLUE, CreatureColor.BLUE));

        ArrayList<CreatureColor> expectedEntrance = new ArrayList<>(Arrays.asList(CreatureColor.PINK, CreatureColor.RED));

        character.effect();

        assertEquals(6, character.getStudents().size());
        assertEquals(expectedStudents, character.students.stream().map(Creature::getColor).collect(Collectors.toList()));
        assertEquals(2, player.getBoard().getEntrance().getStudents().size());
        assertEquals(expectedEntrance, player.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));
    }


    @Test
    void effect7_KO() {
        character = cf.createCharacter(7);

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.WHITE);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        player.getBoard().addStudentToEntrance(CreatureColor.BLUE);
        player.getBoard().addStudentToEntrance(CreatureColor.PINK);

        ((CharacterMover) character).setFromColor(CreatureColor.RED);
        ((CharacterMover) character).setToColor(CreatureColor.GREEN);

        ArrayList<CreatureColor> expectedStudents = new ArrayList<>(Arrays.asList(CreatureColor.RED, CreatureColor.RED,
                CreatureColor.GREEN,CreatureColor.BLUE, CreatureColor.GREEN, CreatureColor.BLUE));

        ArrayList<CreatureColor> expectedEntrance = new ArrayList<>(Arrays.asList(CreatureColor.BLUE, CreatureColor.PINK));

        character.effect();

        assertEquals(6, character.getStudents().size());
        assertEquals(expectedStudents, character.getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));
        assertEquals(2, player.getBoard().getEntrance().getStudents().size());
        assertEquals(expectedEntrance, player.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));

    }

    @Test
    void effect10_OK() {
        character = cf.createCharacter(10);

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.WHITE);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        player.getBoard().addStudentToEntrance(CreatureColor.BLUE);
        player.getBoard().addStudentToEntrance(CreatureColor.PINK);

        player.getBoard().addStudentToHall(CreatureColor.PINK);
        player.getBoard().addStudentToHall(CreatureColor.RED);

        ((CharacterMover) character).setFromColor(CreatureColor.PINK);
        ((CharacterMover) character).setToColor(CreatureColor.BLUE);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0,1,0,0,1));

        ArrayList<CreatureColor> expectedEntrance = new ArrayList<>(Arrays.asList(CreatureColor.PINK, CreatureColor.PINK));

        character.effect();

        assertEquals(expectedEntrance, player.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));

        assertEquals(expectedHall, player.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));

    }

    @Test
    void effect10_KO() {
        character = cf.createCharacter(10);

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.WHITE);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        player.getBoard().addStudentToEntrance(CreatureColor.BLUE);
        player.getBoard().addStudentToEntrance(CreatureColor.PINK);

        player.getBoard().addStudentToHall(CreatureColor.PINK);
        player.getBoard().addStudentToHall(CreatureColor.RED);

        ((CharacterMover) character).setFromColor(CreatureColor.GREEN);
        ((CharacterMover) character).setToColor(CreatureColor.RED);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0,1,0,1,0));

        ArrayList<CreatureColor> expectedEntrance = new ArrayList<>(Arrays.asList(CreatureColor.BLUE, CreatureColor.PINK));

        character.effect();

        assertEquals(expectedEntrance, player.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).collect(Collectors.toList()));

        assertEquals(expectedHall, player.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));


    }


    @Test
    void effect11_OK() {
        character = cf.createCharacter(11);

        Bag.getBag().receiveStudent(CreatureColor.RED);

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.WHITE);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.PINK);

        ((CharacterMover)character).setFromColor(CreatureColor.RED);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0, 2, 0, 1, 0));

        character.effect();

        assertEquals(expectedHall, player.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(4, character.getStudents().size());

    }


    @Test
    void effect11_KO() {
        character = cf.createCharacter(11);

        Bag.getBag().receiveStudent(CreatureColor.RED);

        Game game = Game.getGame();
        Player player = new Player("Chiara", PlayerColor.WHITE);
        game.addPlayer(player);
        player.getBoard().setTowers(5);
        game.setCurrentPlayer(player);

        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.RED));
        character.students.add(new Student(CreatureColor.GREEN));
        character.students.add(new Student(CreatureColor.BLUE));

        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.PINK);

        ((CharacterMover)character).setFromColor(CreatureColor.PINK);

        ArrayList<Integer> expectedHall = new ArrayList<>(Arrays.asList(0,1,0,1,0));

        character.effect();

        assertEquals(expectedHall, player.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(4, character.getStudents().size());

    }




    @Test
    void effect12_OK() {
        Game game = Game.getGame();
        Player p1 = new Player("X", PlayerColor.WHITE);
        Player p2 = new Player("Y", PlayerColor.BLACK);
        game.addPlayer(p1);
        game.addPlayer(p2);
        p1.getBoard().setTowers(5);
        p2.getBoard().setTowers(5);
        game.setCurrentPlayer(p1);

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

        ((CharacterMover)character).setColorToRemove(CreatureColor.RED);
        character.effect();

        assertEquals(expectedHall1, p1.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(expectedHall2, p2.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
    }

    @Test
    void effect12_KO() {
        Game game = Game.getGame();
        Player p1 = new Player("X", PlayerColor.WHITE);
        Player p2 = new Player("Y", PlayerColor.BLACK);
        game.addPlayer(p1);
        game.addPlayer(p2);
        p1.getBoard().setTowers(5);
        p2.getBoard().setTowers(5);
        game.setCurrentPlayer(p1);

        character = cf.createCharacter(12);

        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.RED);
        p1.getBoard().addStudentToHall(CreatureColor.GREEN);
        p1.getBoard().addStudentToHall(CreatureColor.PINK);

        ArrayList<Integer> expectedHall1 = new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0));


        p2.getBoard().addStudentToHall(CreatureColor.GREEN);
        p2.getBoard().addStudentToHall(CreatureColor.GREEN);

        ArrayList<Integer> expectedHall2 = new ArrayList<>(Arrays.asList(2, 0, 0, 0, 0));

        ((CharacterMover)character).setColorToRemove(CreatureColor.RED);
        character.effect();

        assertEquals(expectedHall1, p1.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));
        assertEquals(expectedHall2, p2.getBoard().getHall().getStudents().stream().map(Table::getLength).collect(Collectors.toList()));

    }

}