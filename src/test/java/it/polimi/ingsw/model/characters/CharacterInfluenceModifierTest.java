package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfluenceModifierTest {

    Game game;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;
    ConcreteCharacterFactory cf;
    Character character;
    Phase phase;
    PhaseFactory phaseFactory;

    @BeforeEach
    void setUp() {
        game = new Game();
        cf = new ConcreteCharacterFactory(game);
        phaseFactory = new PhaseFactory(game);
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(2);
        priority.add(1);
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
        wizardIDs = null;
        priority = null;
    }


    @Test
    void effect() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EASY);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        // Lobby phase
        phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Prepare phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setWizardID(wizardIDs.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.PLANNING_PHASE, game.getGameState());

        // Planning phase
        phase = game.getCurrentPhase();

        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            try {
                phase.setPriority(priority.get(i));
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals(GameState.MOVE_STUDENT_PHASE, game.getGameState());

        Player p1 = game.getPlayingOrder().get(0);

        p1.receiveCoin();
        p1.receiveCoin();
        p1.receiveCoin();
        p1.receiveCoin();
        p1.receiveCoin();

        phase = phaseFactory.createPhase(GameState.MOVE_MOTHER_NATURE_PHASE);
        phase.setNumberOfSteps(1);
        game.setCurrentPhase(phase);

        character = cf.createCharacter(5);
        game.setActivatedCharacter(character);

        game.addDrawnCharacter(character);

        ((CharacterInfluenceModifier)character).setIslandGroupIndex(1);

        try {
            ((ActionPhase)game.getCurrentPhase()).playCharacter(5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(3, p1.getCoins());
        assertEquals(3, game.getActivatedCharacter().getNumberOfBanCards());
        assertEquals(1, game.getIslandGroupByIndex(1).getNumberOfBanCardPresent());

        ((CharacterInfluenceModifier)character).setIslandGroupIndex(2);

        assertThrows(TooManyIterationsException.class, ()->((ActionPhase)game.getCurrentPhase()).playCharacter(5));

        assertEquals(3, p1.getCoins());
        assertEquals(3, game.getActivatedCharacter().getNumberOfBanCards());
        assertEquals(0, game.getIslandGroupByIndex(2).getNumberOfBanCardPresent());
    }
}