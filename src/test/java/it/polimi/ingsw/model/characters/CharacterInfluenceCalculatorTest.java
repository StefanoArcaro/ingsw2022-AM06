package it.polimi.ingsw.model.characters;

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
    @Test
    void effect(){

    }


}