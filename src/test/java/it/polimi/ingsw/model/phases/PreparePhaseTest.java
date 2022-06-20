package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.InvalidWizardException;
import it.polimi.ingsw.exceptions.WizardTakenException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PreparePhaseTest {

    private Game game;
    private Phase phase;
    private ArrayList<String> nicknames;
    private int wizardID;

    /** Tests the setup of the phase */
    @BeforeEach
    void setUp() {
        game = new Game();
        nicknames = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");
        nicknames.add("Nick");
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
    }

    /** Tests the prepare-phase of the game with easy mode and two players */
    @Test
    void play_easy_2P() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EASY);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

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

        phase = game.getCurrentPhase();

        // Player one chooses invalid wizard (lower bound)
        wizardID = -1;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses invalid wizard (upper bound)
        wizardID = 4;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses DRUID
        wizardID = 0;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two tries to choose DRUID (already taken)
        wizardID = 0;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two chooses WITCH
        wizardID = 2;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Wizards check
        assertEquals(WizardName.DRUID, game.getPlayers().get(0).getWizard().getName());
        assertEquals(WizardName.WITCH, game.getPlayers().get(1).getWizard().getName());

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
        assertEquals(2, game.getClouds().size());

        // Number of professors
        assertEquals(5, game.getProfessors().size());

        // Number of towers for each player
        for(Player player : game.getPlayers()) {
            assertEquals(8, player.getBoard().getTowers());
        }

        // Number of students in each player's board entrance
        assertEquals(7, game.getPlayers().get(0).getBoard().getEntrance().getStudents().size());
        assertEquals(7, game.getPlayers().get(1).getBoard().getEntrance().getStudents().size());

        // CHeck each Hall's table is empty
        for(CreatureColor color : CreatureColor.values()) {
            assertEquals(0, game.getPlayers().get(0).getBoard().getHall().getTableByColor(color).getLength());
            assertEquals(0, game.getPlayers().get(1).getBoard().getHall().getTableByColor(color).getLength());
        }

        // Check that only the default character was drawn
        assertEquals(1, game.getDrawnCharacters().size());

        // Check that no coins were distributed
        for(Player player : game.getPlayers()) {
            assertEquals(0, player.getCoins());
        }
        assertEquals(0, game.getTreasury());

        // Check updated game state
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());
    }

    /** Tests the prepare-phase of the game with easy mode and three players */
    @Test
    void play_easy_3P() {
        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EASY);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

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

        phase = game.getCurrentPhase();

        // Player one chooses invalid wizard (lower bound)
        wizardID = -1;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses invalid wizard (upper bound)
        wizardID = 4;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses DRUID
        wizardID = 0;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two chooses DRUID (already taken)
        wizardID = 0;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two chooses KING
        wizardID = 1;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player three tries to choose DRUID (already taken)
        wizardID = 0;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player three tries to choose KING (already taken)
        wizardID = 1;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player three chooses SENSEI
        wizardID = 3;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Wizards check
        assertEquals(WizardName.DRUID, game.getPlayers().get(0).getWizard().getName());
        assertEquals(WizardName.KING, game.getPlayers().get(1).getWizard().getName());
        assertEquals(WizardName.SENSEI, game.getPlayers().get(2).getWizard().getName());

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

        // Number of students in each player's board entrance
        assertEquals(9, game.getPlayers().get(0).getBoard().getEntrance().getStudents().size());
        assertEquals(9, game.getPlayers().get(1).getBoard().getEntrance().getStudents().size());
        assertEquals(9, game.getPlayers().get(2).getBoard().getEntrance().getStudents().size());

        // CHeck each Hall's table is empty
        for(CreatureColor color : CreatureColor.values()) {
            assertEquals(0, game.getPlayers().get(0).getBoard().getHall().getTableByColor(color).getLength());
            assertEquals(0, game.getPlayers().get(1).getBoard().getHall().getTableByColor(color).getLength());
            assertEquals(0, game.getPlayers().get(2).getBoard().getHall().getTableByColor(color).getLength());
        }

        // Check that only the default character was drawn
        assertEquals(1, game.getDrawnCharacters().size());

        // Check that no coins were distributed
        for(Player player : game.getPlayers()) {
            assertEquals(0, player.getCoins());
        }
        assertEquals(0, game.getTreasury());

        // Check updated game state
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());
    }

    /** Tests the prepare-phase of the game with expert mode and two players */
    @Test
    void play_expert_2P() {
        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

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

        phase = game.getCurrentPhase();

        // Player one chooses invalid wizard (lower bound)
        wizardID = -1;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses invalid wizard (upper bound)
        wizardID = 4;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses DRUID
        wizardID = 0;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two tries to choose DRUID (already taken)
        wizardID = 0;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two chooses WITCH
        wizardID = 2;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Wizards check
        assertEquals(WizardName.DRUID, game.getPlayers().get(0).getWizard().getName());
        assertEquals(WizardName.WITCH, game.getPlayers().get(1).getWizard().getName());

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
        assertEquals(2, game.getClouds().size());

        // Number of professors
        assertEquals(5, game.getProfessors().size());

        // Number of towers for each player
        for(Player player : game.getPlayers()) {
            assertEquals(8, player.getBoard().getTowers());
        }

        // Number of students in each player's board entrance
        assertEquals(7, game.getPlayers().get(0).getBoard().getEntrance().getStudents().size());
        assertEquals(7, game.getPlayers().get(1).getBoard().getEntrance().getStudents().size());

        // CHeck each Hall's table is empty
        for(CreatureColor color : CreatureColor.values()) {
            assertEquals(0, game.getPlayers().get(0).getBoard().getHall().getTableByColor(color).getLength());
            assertEquals(0, game.getPlayers().get(1).getBoard().getHall().getTableByColor(color).getLength());
        }

        // Check drawn characters
        assertEquals(4, game.getDrawnCharacters().size());
        assertNotEquals(game.getDrawnCharacters().get(0).getCharacterID(), game.getDrawnCharacters().get(1).getCharacterID());
        assertNotEquals(game.getDrawnCharacters().get(0).getCharacterID(), game.getDrawnCharacters().get(2).getCharacterID());
        assertNotEquals(game.getDrawnCharacters().get(1).getCharacterID(), game.getDrawnCharacters().get(2).getCharacterID());

        // Check coin distribution
        for(Player player : game.getPlayers()) {
            assertEquals(1, player.getCoins());
        }
        assertEquals(18, game.getTreasury());

        // Check updated game state
        assertEquals(GameState.PLANNING_PHASE, game.getGameState());
    }

    /** Tests the prepare-phase of the game with expert mode and three players */
    @Test
    void play_expert_3P() {
        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        assertEquals(GameState.LOBBY_PHASE, game.getGameState());

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

        phase = game.getCurrentPhase();

        // Player one chooses invalid wizard (lower bound)
        wizardID = -1;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses invalid wizard (upper bound)
        wizardID = 4;
        phase.setWizardID(wizardID);

        assertThrows(InvalidWizardException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player one chooses DRUID
        wizardID = 0;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two chooses DRUID (already taken)
        wizardID = 0;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player two chooses KING
        wizardID = 1;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player three tries to choose DRUID (already taken)
        wizardID = 0;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player three tries to choose KING (already taken)
        wizardID = 1;
        phase.setWizardID(wizardID);

        assertThrows(WizardTakenException.class, () -> phase.play());
        assertEquals(GameState.PREPARE_PHASE, game.getGameState());

        // Player three chooses SENSEI
        wizardID = 3;
        phase.setWizardID(wizardID);
        try {
            phase.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Wizards check
        assertEquals(WizardName.DRUID, game.getPlayers().get(0).getWizard().getName());
        assertEquals(WizardName.KING, game.getPlayers().get(1).getWizard().getName());
        assertEquals(WizardName.SENSEI, game.getPlayers().get(2).getWizard().getName());

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

        // Number of students in each player's board entrance
        assertEquals(9, game.getPlayers().get(0).getBoard().getEntrance().getStudents().size());
        assertEquals(9, game.getPlayers().get(1).getBoard().getEntrance().getStudents().size());
        assertEquals(9, game.getPlayers().get(2).getBoard().getEntrance().getStudents().size());

        // CHeck each Hall's table is empty
        for(CreatureColor color : CreatureColor.values()) {
            assertEquals(0, game.getPlayers().get(0).getBoard().getHall().getTableByColor(color).getLength());
            assertEquals(0, game.getPlayers().get(1).getBoard().getHall().getTableByColor(color).getLength());
            assertEquals(0, game.getPlayers().get(2).getBoard().getHall().getTableByColor(color).getLength());
        }

        // Check drawn characters
        assertEquals(4, game.getDrawnCharacters().size());
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