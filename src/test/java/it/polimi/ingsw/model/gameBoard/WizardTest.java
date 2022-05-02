package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.enumerations.WizardName;
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
    void getPlayer() {
        assertEquals(player, wizard.getPlayer());
    }

    @Test
    void wizard() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character = cf.createCharacter(0);

        assertEquals(10, wizard.getAssistants().size());
        assertEquals(1, wizard.getAssistants().get(0).getPriority());
        assertEquals(10, wizard.getAssistants().get(9).getPriority());
        assertEquals(1, wizard.getAssistants().get(1).getMaxSteps(character));
        assertEquals(5, wizard.getAssistants().get(8).getMaxSteps(character));
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