package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.model.phases.MoveMotherNaturePhase;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterInfluenceModifierTest {

    Game game;
    ConcreteCharacterFactory cf;
    Character character;
    PhaseFactory phaseFactory;
    Phase phase;

    @BeforeEach
    void setUp() {
        game = new Game();
        cf = new ConcreteCharacterFactory(game);
        character = cf.createCharacter(3);
    }

    @Test
    void effect() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        phaseFactory = new PhaseFactory(game);
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        phase = phaseFactory.createPhase(GameState.PREPARE_PHASE);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        phase = phaseFactory.createPhase(GameState.PLANNING_PHASE);
        try {
            phase.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Player p1 = game.getPlayers().get(1);
        Player p2 = game.getPlayers().get(0);

        p1.receiveCoin();
        p1.receiveCoin();
        p1.receiveCoin();
        p1.receiveCoin();

        game.setCurrentPlayer(p1);

        phase = phaseFactory.createPhase(GameState.MOVE_MOTHER_NATURE_PHASE);
        ((MoveMotherNaturePhase)phase).setNumberOfSteps(1);
        game.setCurrentPhase(phase);

        character = cf.createCharacter(5);

        game.setActivatedCharacter(5);

        ((CharacterInfluenceModifier)character).setIslandGroupIndex(1);

        try {
            ((ActionPhase)game.getCurrentPhase()).playCharacter(character);
        } catch (NoAvailableBanCardsException | OutOfBoundException | NoAvailableColorException | NotEnoughMoneyException | TooManyIterationsException e) {
            e.printStackTrace();
        }

        assertEquals(3, p1.getCoins());
        assertEquals(3, character.getNumberOfBanCards());
        assertEquals(1, game.getIslandGroupByIndex(1).getNumberOfBanCardPresent());

        ((CharacterInfluenceModifier)character).setIslandGroupIndex(2);

        assertThrows(TooManyIterationsException.class, ()->((ActionPhase)game.getCurrentPhase()).playCharacter(character));

        assertEquals(3, p1.getCoins());
        assertEquals(3, character.getNumberOfBanCards());
        assertEquals(0, game.getIslandGroupByIndex(2).getNumberOfBanCardPresent());

    }
}