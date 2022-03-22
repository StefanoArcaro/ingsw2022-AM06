package it.polimi.ingsw.characters;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CharacterOneDecoratorTest {
    private CharacterOneDecorator characterOne;

    @BeforeEach
    public void begin(){
        characterOne = new CharacterOneDecorator();
    }

    @AfterEach
    public void end(){
        characterOne = null;
    }

    @Test
    public void getCost_Test(){
        assertEquals(1, characterOne.getCost());
        characterOne.setUsed();
        assertEquals(2, characterOne.getCost());
    }

    @Test
    public void getStudents_Test(){
        assertEquals(null, characterOne.getStudents());
    }

    @Test
    public void initialPreparetion_Test (){
        characterOne.initialPreparation();
        assertEquals(4, characterOne.getSize());
        //la bag deve averne 4 in meno
    }

    @Test
    public void wrongStudent_effect_Test (){
        Island island = new Island (1);
        Student student = new Student (CreatureColor.GREEN);

        //serve lo scheletro di tutto il codice !!



    }


}
