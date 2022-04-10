package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {

    Game game;
    Assistant assistant;

    @BeforeEach
    void setUp() {
        game = new Game();
        assistant = new Assistant(2, 1);
    }

    @AfterEach
    void tearDown() {
        game = null;
        assistant = null;
    }

    @Test
    void getMaxSteps() {
        assertEquals(1, assistant.getMaxSteps());
    }

    @Test
    void getMaxSteps_Character() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character = cf.createCharacter(4);
        assertEquals(3, assistant.getMaxSteps(character));
    }
}