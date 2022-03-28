package it.polimi.ingsw.model.characters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterProfessorUpdaterTest {

    ConcreteCharacterFactory cf;
    Character character;

    @BeforeEach
    void setUp() {
        cf = new ConcreteCharacterFactory();
    }

    @AfterEach
    void tearDown() {
        cf = null;
        character = null;
    }


    //need the professorUpdate method to be implemented in Round
    @Test
    void effect() {
    }
}