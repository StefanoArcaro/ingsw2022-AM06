package it.polimi.ingsw.model.characters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    ConcreteCharacterFactory cf;
    Character character;

    @BeforeEach
    void setUp() {
        cf = new ConcreteCharacterFactory();
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