package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Bag;
import it.polimi.ingsw.model.gameBoard.Professor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveMotherNaturePhaseTest {

    private Game game;
    private ArrayList<String> nicknames;
    private ArrayList<Integer> wizardIDs;
    private ArrayList<Integer> priority;


    @BeforeEach
    void setUp() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(2);
        priority.add(1);

        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EXPERT);

        // Lobby phase
        Phase phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

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
    }

    @AfterEach
    void tearDown() {
        game = null;
        nicknames = null;
        wizardIDs = null;
        priority = null;
    }

    /**
     * Tests:
     * the movement of mother nature;
     * the calculation of the influence on the island where mother nature arrives;
     * the replacement of the towers;
     * whether adjacent islands need to be merged.
     */
    @Test
    void play() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(1);

        int previousMNIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        int expectedMNIndex = (previousMNIndex + 1) % game.getIslandGroups().size();

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedMNIndex, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));
        assertEquals(GameState.PICK_CLOUD_PHASE, game.getGameState());
    }

    @Test
    void play_exceededSteps() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(3);

        assertThrows(ExceededStepsException.class, ()->game.getCurrentPhase().play());
        assertEquals(GameState.MOVE_MOTHER_NATURE_PHASE, game.getGameState());

        game.getCurrentPhase().setNumberOfSteps(1);

        int previousMNIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        int expectedMNIndex = (previousMNIndex + 1) % game.getIslandGroups().size();

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedMNIndex, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));
        assertEquals(GameState.PICK_CLOUD_PHASE, game.getGameState());
    }

    /** Tests the play method ending with no tower left */
    @Test
    void play_EndTower() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));
        game.getCurrentPhase().setNumberOfSteps(1);

        Player p1 = game.getPlayingOrder().get(0);
        Player p2 = game.getPlayingOrder().get(1);

        //connect some island groups: 0-1-2-3
        game.getIslandGroups().get(0).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(0).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(1).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p1.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));
        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));
        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));

        //connect some island groups: 4-5-6-7
        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(4).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(1).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(4).setConquerorColor(p1.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));
        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));
        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));

        //connect some island groups: 8-9-10-11
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(4).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(5).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(4).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(5).setConquerorColor(p2.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));
        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));
        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));

        assertEquals(3, game.getIslandGroups().size());

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(GameState.ENDED_TOWER, game.getGameState());

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, game.getCurrentPhase().getWinner());
    }

    /** Tests the play method ending with three island groups left */
    @Test
    void play_EndIsland() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");
        nicknames.add("Nick");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH
        wizardIDs.add(1);

        priority.add(2);
        priority.add(1);
        priority.add(3);

        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        // Lobby phase
        Phase phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

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

        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));
        game.getCurrentPhase().setNumberOfSteps(1);

        Player p1 = game.getPlayingOrder().get(0);
        Player p2 = game.getPlayingOrder().get(1);
        Player p3 = game.getPlayingOrder().get(2);

        p1.getBoard().winProfessor(new Professor(CreatureColor.GREEN));

        //connect some island groups
        game.getIslandGroups().get(0).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p1.getColor());
        game.getIslandGroups().get(0).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(1).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p1.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p1.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));
        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));
        game.connectIslandGroups(game.getIslandGroups().get(0), game.getIslandGroups().get(1));

        game.getIslandGroups().get(1).getIslands().get(0).addTower(game, p3.getColor());
        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p3.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p3.getColor());
        game.getIslandGroups().get(4).getIslands().get(0).addTower(game, p3.getColor());
        game.getIslandGroups().get(1).setConquerorColor(p3.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p3.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p3.getColor());
        game.getIslandGroups().get(4).setConquerorColor(p3.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));
        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));
        game.connectIslandGroups(game.getIslandGroups().get(1), game.getIslandGroups().get(2));

        game.getIslandGroups().get(2).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(3).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(4).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(5).getIslands().get(0).addTower(game, p2.getColor());
        game.getIslandGroups().get(2).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(3).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(4).setConquerorColor(p2.getColor());
        game.getIslandGroups().get(5).setConquerorColor(p2.getColor());

        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));
        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));
        game.connectIslandGroups(game.getIslandGroups().get(2), game.getIslandGroups().get(3));

        assertEquals(3, game.getIslandGroups().size());

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(GameState.ENDED_ISLAND, game.getGameState());

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, game.getCurrentPhase().getWinner());
    }

    /** Tests the play method in case of an island with a ban card */
    @Test
    void play_islandWithBanCard() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(2);
        priority.add(1);

        game.setNumberOfPlayers(2);
        game.setGameMode(GameMode.EASY);

        // Lobby phase
        Phase phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

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

        Character activatedCharacter = new ConcreteCharacterFactory(game).createCharacter(5);
        game.addDrawnCharacter(activatedCharacter);
        game.setActivatedCharacter(activatedCharacter);
        activatedCharacter.setIslandGroupIndex(1);

        try {
            activatedCharacter.effect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(1, game.getIslandGroupByIndex(1).getNumberOfBanCardPresent());
        assertEquals(3, activatedCharacter.getNumberOfBanCards());

        game.getMotherNature().setCurrentIslandGroup(game.getIslandGroupByIndex(0));

        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(1);

        int expectedMNIndex = 1;

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedMNIndex, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));
        assertEquals(0, game.getIslandGroupByIndex(1).getNumberOfBanCardPresent());
        assertEquals(4, activatedCharacter.getNumberOfBanCards());

        assertEquals(GameState.PICK_CLOUD_PHASE, game.getGameState());
    }

    /** Tests the play method ending with no students left */
    @Test
    void play_skipPickCloud() {
        game = new Game();
        nicknames = new ArrayList<>();
        wizardIDs = new ArrayList<>();
        priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");
        nicknames.add("Nick");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH
        wizardIDs.add(1);

        priority.add(2);
        priority.add(1);
        priority.add(3);

        game.setNumberOfPlayers(3);
        game.setGameMode(GameMode.EXPERT);

        // Lobby phase
        Phase phase = game.getCurrentPhase();

        for(String nickname : nicknames) {
            try {
                phase.setPlayerNickname(nickname);
                phase.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

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

        Bag bag = game.getBag();
        int lenBag = bag.size();
        for(int i = 0; i < lenBag; i++){
            bag.drawStudent();
        }

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

        game.setCurrentPlayer(game.getPlayingOrder().get(2));
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));
        game.getCurrentPhase().setNumberOfSteps(1);

        try{
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(GameState.ENDED_STUDENTS, game.getGameState());


    }
}