package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gameBoard.Bag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreparePhaseTest {

    Game game;
    PhaseFactory phaseFactory;
    Phase phase;

    @Test
    void play() {
        game = Game.getGame();
        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

        phaseFactory = new PhaseFactory();
        phase = phaseFactory.createPhase(GameState.LOBBY_PHASE);
        phase.play();

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        phase = phaseFactory.createPhase(GameState.PREPARE_PHASE);
        phase.play();

        // Island groups and island numbers check
        assertEquals(12, game.getIslandGroups().size());

        for(int i = 0; i < 12; i++) {
            assertEquals(1, game.getIslandGroups().get(i).getIslands().size());
        }

        // Mother Nature and initial student placement
        int motherNaturePosition = game.getMotherNature().getCurrentIslandGroup().getIslands().get(0).getIslandID() - 1;

        assertEquals(0, game.getIslandGroups().get(motherNaturePosition).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 1) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 2) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 3) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 4) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 5) % 12).getIslands().get(0).getStudents().size());
        assertEquals(0, game.getIslandGroups().get((motherNaturePosition + 6) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 7) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 8) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 9) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 10) % 12).getIslands().get(0).getStudents().size());
        assertEquals(1, game.getIslandGroups().get((motherNaturePosition + 11) % 12).getIslands().get(0).getStudents().size());

        // Number of cloud cards instantiated
        assertEquals(3, game.getClouds().size());

        // Number of professors
        assertEquals(5, game.getProfessors().size());

        // Number of towers for each player
        for(Player player : game.getPlayers()) {
            assertEquals(6, player.getBoard().getTowers());
        }

        // Wizards check
        assertEquals(WizardName.DRUID, game.getPlayers().get(0).getWizard().getName());
        assertEquals(WizardName.KING, game.getPlayers().get(1).getWizard().getName());
        assertEquals(WizardName.SENSEI, game.getPlayers().get(2).getWizard().getName());

        // Number of students in each player's board entrance
        assertEquals(9, game.getPlayers().get(0).getBoard().getEntrance().getStudents().size());
        assertEquals(9, game.getPlayers().get(1).getBoard().getEntrance().getStudents().size());
        assertEquals(9, game.getPlayers().get(2).getBoard().getEntrance().getStudents().size());

        // Bag check (again)
        assertEquals(93, Bag.getBag().size());

        // Check drawn characters
        assertEquals(3, game.getDrawnCharacters().size());
        assertNotEquals(game.getDrawnCharacters().get(0).getCharacterID(), game.getDrawnCharacters().get(1).getCharacterID());
        assertNotEquals(game.getDrawnCharacters().get(0).getCharacterID(), game.getDrawnCharacters().get(2).getCharacterID());
        assertNotEquals(game.getDrawnCharacters().get(1).getCharacterID(), game.getDrawnCharacters().get(2).getCharacterID());

        // Check coin distribution
        for(Player player : game.getPlayers()) {
            assertEquals(1, player.getCoins());
        }
        assertEquals(17, game.getTreasury());

        // Check updated game state
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());
    }
}