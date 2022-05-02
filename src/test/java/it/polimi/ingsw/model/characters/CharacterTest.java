package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    Game game;
    ConcreteCharacterFactory cf;
    Character character;

    @BeforeEach
    void setUp() {
        game = new Game();
        cf = new ConcreteCharacterFactory(game);
        character = cf.createCharacter(8);
    }

    @AfterEach
    void tearDown() {
        cf = null;
        character = null;
    }

    @Test
    void getCost() {
        assertEquals(2, character.getCost());
        character.setUsed();
        assertEquals(3, character.getCost());
        character.setUsed();
        assertEquals(3, character.getCost());
    }

    @Test
    void getCharacterID() {
        assertEquals(8, character.getCharacterID());
    }
}