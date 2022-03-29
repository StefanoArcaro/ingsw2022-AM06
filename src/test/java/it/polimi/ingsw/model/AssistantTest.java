package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterStepsAdder;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantTest {

    Assistant assistant;

    @BeforeEach
    void setUp() {
        assistant = new Assistant(2, 1);
    }

    @AfterEach
    void tearDown() {
        assistant = null;
    }

    @Test
    void getMaxSteps() {
        assertEquals(1, assistant.getMaxSteps());
    }

    @Test
    void getMaxSteps_Character() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory();
        Character character = cf.createCharacter(4);
        assertEquals(3, assistant.getMaxSteps((CharacterStepsAdder) character));
    }


}