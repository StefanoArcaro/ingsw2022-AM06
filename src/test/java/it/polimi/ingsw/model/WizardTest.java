package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WizardTest {

    Game game;
    Wizard wizard;
    Player player;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = new Player(game, "Chiara", PlayerColor.BLACK);
        wizard = new Wizard(player, WizardName.DRUID);
    }

    @AfterEach
    void tearDown() {
        game = null;
        player = null;
        wizard = null;
    }

    @Test
    void wizard() {
        assertEquals(10, wizard.getAssistants().size());
        assertEquals(1, wizard.getAssistants().get(0).getPriority());
        assertEquals(10, wizard.getAssistants().get(9).getPriority());
        assertEquals(1, wizard.getAssistants().get(1).getMaxSteps());
        assertEquals(5, wizard.getAssistants().get(8).getMaxSteps());
    }

    @Test
    void playAssistant_OK() {
        assertEquals(4, wizard.playAssistant(4).getPriority());
        assertEquals(10, wizard.getAssistants().size());
    }

    @Test
    void playAssistant_KO() {
        wizard.removeAssistant(5);
        assertNull(wizard.playAssistant(5));
    }

    @Test
    void removeAssistant() {
        assertTrue(wizard.removeAssistant(3));
        assertFalse(wizard.removeAssistant(3));
    }

    @Test
    void checkIfNoAssistant(){
        for(int i=1; i<11; i++){
            assertFalse(wizard.checkIfNoAssistants());
            wizard.removeAssistant(i);
        }
        assertTrue(wizard.checkIfNoAssistants());
    }
}