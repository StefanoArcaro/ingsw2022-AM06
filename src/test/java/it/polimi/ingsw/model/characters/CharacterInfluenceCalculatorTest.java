package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfluenceCalculatorTest {

    Game game;
    ConcreteCharacterFactory cf;
    Character character;

    @BeforeEach
    void setUp() {
        game = new Game();
        cf = new ConcreteCharacterFactory(game);
        character = cf.createCharacter(3);

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
    }

    @Test
    void effect(){
        Player p1 = new Player("X", PlayerColor.WHITE);
        Player p2 = new Player("Y", PlayerColor.BLACK);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.setCurrentPlayer(p1);

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


        IslandGroup islandGroup1 = game.getIslandGroups().get(1);
        IslandGroup islandGroup2 = game.getIslandGroups().get(2);


        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup1.getIslands().get(0).addTower(game, p2.getColor());
        islandGroup1.setConquerorColor(p2.getColor());

        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup2.getIslands().get(0).addTower(game, p1.getColor());
        islandGroup2.setConquerorColor(p1.getColor());


        ((CharacterInfluenceCalculator)character).setIslandGroupIndex(1);
        character.effect();

        assertEquals(p1.getColor(), islandGroup1.getConquerorColor());
        assertEquals(p1.getColor(), islandGroup2.getConquerorColor());

        assertEquals(1, p1.getBoard().getTowers());
        assertEquals(2, p2.getBoard().getTowers());

        assertEquals(1, p1.getBoard().getTowers());
        assertEquals(2, p2.getBoard().getTowers());
    }
}