package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.Phase;
import it.polimi.ingsw.model.phases.PhaseFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();

        for(int i = 1; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Test
    void calculateInfluence_Conquer() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character = cf.createCharacter(0);
        game.setActivatedCharacter(character);

        Player player1 = new Player(game, "X", PlayerColor.BLACK);
        game.addPlayer(player1);
        player1.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player1.getBoard().winProfessor(new Professor(CreatureColor.GREEN));
        player1.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));
        player1.getBoard().setTowers(5);

        Player player2 = new Player(game, "Y", PlayerColor.WHITE);
        game.addPlayer(player2);
        player2.getBoard().winProfessor(new Professor(CreatureColor.PINK));
        player2.getBoard().setTowers(5);

        game.setCurrentPlayer(player1);

        IslandGroup islandGroup1 = game.getIslandGroups().get(1);
        IslandGroup islandGroup2 = game.getIslandGroups().get(2);
        IslandGroup islandGroup3 = game.getIslandGroups().get(3);
        IslandGroup islandGroup4 = game.getIslandGroups().get(4);

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).addTower(game, player1.getColor());
        islandGroup1.setConquerorColor(player1.getColor());

        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup2.getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        islandGroup2.getIslands().get(0).addTower(game, player1.getColor());
        islandGroup2.setConquerorColor(player1.getColor());

        game.connectIslandGroups(game.getIslandGroupByIndex(1), game.getIslandGroupByIndex(2));

        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup3.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup3.getIslands().get(0).addTower(game, player2.getColor());
        islandGroup3.setConquerorColor(player2.getColor());

        islandGroup4.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup4.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup4.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup4.getIslands().get(0).addTower(game, player2.getColor());
        islandGroup4.setConquerorColor(player2.getColor());

        game.calculateInfluence(2);

        assertEquals(10, game.getIslandGroups().size());
        assertEquals(PlayerColor.BLACK, game.getIslandGroups().get(1).getConquerorColor());
        assertEquals(3, game.getIslandGroups().get(1).getNumberOfIslands());
        assertEquals(2, player1.getBoard().getTowers());
        assertEquals(4, player2.getBoard().getTowers());
    }

    @Test
    void calculateInfluence_WithCharacterNoTower() {
        ConcreteCharacterFactory cf = new ConcreteCharacterFactory(game);
        Character character = cf.createCharacter(6);
        game.setActivatedCharacter(character);

        Player player1 = new Player(game, "X", PlayerColor.BLACK);
        game.addPlayer(player1);
        player1.getBoard().winProfessor(new Professor(CreatureColor.RED));
        player1.getBoard().winProfessor(new Professor(CreatureColor.GREEN));
        player1.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));
        player1.getBoard().setTowers(5);

        Player player2 = new Player(game, "Y", PlayerColor.WHITE);
        game.addPlayer(player2);
        player2.getBoard().winProfessor(new Professor(CreatureColor.PINK));
        player2.getBoard().setTowers(5);

        game.setCurrentPlayer(player1);

        IslandGroup islandGroup1 = game.getIslandGroups().get(1);

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.RED);
        islandGroup1.getIslands().get(0).addTower(game, player1.getColor());
        islandGroup1.setConquerorColor(player1.getColor());

        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);
        islandGroup1.getIslands().get(0).receiveStudent(CreatureColor.PINK);

        game.calculateInfluence(1);

        assertEquals(12, game.getIslandGroups().size());
        assertEquals(PlayerColor.WHITE, game.getIslandGroups().get(1).getConquerorColor());
        assertEquals(1, game.getIslandGroups().get(1).getNumberOfIslands());
        assertEquals(5, player1.getBoard().getTowers());
        assertEquals(4, player2.getBoard().getTowers());
    }

    @Test
    void calculateInfluence_connectIslandGroups() {
        int newIndexMotherNature;
        ArrayList<Integer> expectedIDIslands;

        game = new Game();
        ArrayList<String> nicknames = new ArrayList<>();
        ArrayList<Integer> wizardIDs = new ArrayList<>();
        ArrayList<Integer> priority = new ArrayList<>();

        nicknames.add("Stefano");
        nicknames.add("Chiara");

        wizardIDs.add(3); // SENSEI
        wizardIDs.add(2); // WITCH

        priority.add(10);
        priority.add(9);

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

        Player firstPlayer = game.getPlayingOrder().get(0);
        Player lastPlayer = game.getPlayingOrder().get(1);

        //first player: professors green, blue, yellow
        firstPlayer.getBoard().addStudentToHall(CreatureColor.GREEN);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.GREEN);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.GREEN);
        firstPlayer.getBoard().winProfessor(new Professor(CreatureColor.GREEN));

        firstPlayer.getBoard().addStudentToHall(CreatureColor.BLUE);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.BLUE);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.BLUE);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.BLUE);
        firstPlayer.getBoard().winProfessor(new Professor(CreatureColor.BLUE));

        firstPlayer.getBoard().addStudentToHall(CreatureColor.YELLOW);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.YELLOW);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.YELLOW);
        firstPlayer.getBoard().addStudentToHall(CreatureColor.YELLOW);
        firstPlayer.getBoard().winProfessor(new Professor(CreatureColor.YELLOW));

        //last player: professors red, pink
        lastPlayer.getBoard().addStudentToHall(CreatureColor.RED);
        lastPlayer.getBoard().addStudentToHall(CreatureColor.RED);
        lastPlayer.getBoard().addStudentToHall(CreatureColor.RED);
        lastPlayer.getBoard().addStudentToHall(CreatureColor.RED);
        lastPlayer.getBoard().winProfessor(new Professor(CreatureColor.RED));

        lastPlayer.getBoard().addStudentToHall(CreatureColor.PINK);
        lastPlayer.getBoard().addStudentToHall(CreatureColor.PINK);
        lastPlayer.getBoard().addStudentToHall(CreatureColor.PINK);
        lastPlayer.getBoard().addStudentToHall(CreatureColor.PINK);
        lastPlayer.getBoard().winProfessor(new Professor(CreatureColor.PINK));

        //fill the islands so that the calculation of the influence is not random
        //and place some towers

        //islandGroup 0: green
        game.getIslandGroupByIndex(0).getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        game.getIslandGroupByIndex(0).getIslands().get(0).receiveStudent(CreatureColor.GREEN);
        game.getIslandGroupByIndex(0).getIslands().get(0).receiveStudent(CreatureColor.GREEN);

        //islandGroup 1: blue - firstPlayer
        game.getIslandGroupByIndex(1).getIslands().get(0).receiveStudent(CreatureColor.BLUE);
        game.getIslandGroupByIndex(1).getIslands().get(0).receiveStudent(CreatureColor.BLUE);
        game.getIslandGroupByIndex(1).getIslands().get(0).receiveStudent(CreatureColor.BLUE);
        game.getIslandGroupByIndex(1).getIslands().get(0).addTower(game, firstPlayer.getColor());
        game.getIslandGroupByIndex(1).setConquerorColor(firstPlayer.getColor());

        //islandGroup 2: red - lastPlayer
        game.getIslandGroupByIndex(2).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(2).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(2).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(2).getIslands().get(0).addTower(game, lastPlayer.getColor());
        game.getIslandGroupByIndex(2).setConquerorColor(lastPlayer.getColor());

        //islandGroup 3: pink
        game.getIslandGroupByIndex(3).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(3).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(3).getIslands().get(0).receiveStudent(CreatureColor.PINK);

        //islandGroup 4: red
        game.getIslandGroupByIndex(4).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(4).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(4).getIslands().get(0).receiveStudent(CreatureColor.RED);

        //islandGroup 5: pink - lastPlayer
        game.getIslandGroupByIndex(5).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(5).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(5).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(5).getIslands().get(0).addTower(game, lastPlayer.getColor());
        game.getIslandGroupByIndex(5).setConquerorColor(lastPlayer.getColor());

        //islandGroup 6: yellow
        game.getIslandGroupByIndex(6).getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        game.getIslandGroupByIndex(6).getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        game.getIslandGroupByIndex(6).getIslands().get(0).receiveStudent(CreatureColor.YELLOW);

        //islandGroup 7: pink
        game.getIslandGroupByIndex(7).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(7).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(7).getIslands().get(0).receiveStudent(CreatureColor.PINK);

        //islandGroup 8: pink
        game.getIslandGroupByIndex(8).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(8).getIslands().get(0).receiveStudent(CreatureColor.PINK);
        game.getIslandGroupByIndex(8).getIslands().get(0).receiveStudent(CreatureColor.PINK);

        //islandGroup 9: red
        game.getIslandGroupByIndex(9).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(9).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(9).getIslands().get(0).receiveStudent(CreatureColor.RED);

        //islandGroup 10: red
        game.getIslandGroupByIndex(10).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(10).getIslands().get(0).receiveStudent(CreatureColor.RED);
        game.getIslandGroupByIndex(10).getIslands().get(0).receiveStudent(CreatureColor.RED);

        //islandGroup 11: yellow - firstPlayer
        game.getIslandGroupByIndex(11).getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        game.getIslandGroupByIndex(11).getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        game.getIslandGroupByIndex(11).getIslands().get(0).receiveStudent(CreatureColor.YELLOW);
        game.getIslandGroupByIndex(11).getIslands().get(0).addTower(game, firstPlayer.getColor());
        game.getIslandGroupByIndex(11).setConquerorColor(firstPlayer.getColor());

        assertEquals(12, game.getIslandGroups().size());

        //move mother nature to island group with index 10
        game.getMotherNature().setCurrentIslandGroup(game.getIslandGroupByIndex(10));

        //start mother nature phase for firstPlayer
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(2);

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //firstPlayer conquered islandGroup (index 0)
        //and created an archipelago (id 1, 2, 12)
        newIndexMotherNature = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        assertEquals(0, newIndexMotherNature);
        assertEquals(firstPlayer.getColor(), game.getMotherNature().getCurrentIslandGroup().getConquerorColor());
        assertEquals(3, game.getIslandGroups().get(0).getIslands().size());
        expectedIDIslands = new ArrayList<>(Arrays.asList(1, 2, 12));
        assertEquals(expectedIDIslands, game.getIslandGroups().get(0).getIslands().stream().map(Island::getIslandID).toList());
        assertEquals(10, game.getIslandGroups().size());

        //start mother nature phase for firstPlayer
        //mother nature is on island group (index 0)
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(lastPlayer);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(2);

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //lastPlayer conquered islandGroup (index 2)
        //and created an archipelago (id 3, 4)
        newIndexMotherNature = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        assertEquals(1, newIndexMotherNature);
        assertEquals(lastPlayer.getColor(), game.getMotherNature().getCurrentIslandGroup().getConquerorColor());
        assertEquals(2, game.getIslandGroups().get(1).getIslands().size());
        expectedIDIslands = new ArrayList<>(Arrays.asList(3, 4));
        assertEquals(expectedIDIslands, game.getIslandGroups().get(1).getIslands().stream().map(Island::getIslandID).toList());
        assertEquals(9, game.getIslandGroups().size());

        assertEquals(game.getIslandGroupByIndex(1), game.getIslandGroups().get(1));
        assertEquals(1, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));
        assertEquals(game.getIslandGroupByIndex(1), game.getMotherNature().getCurrentIslandGroup());

        //start mother nature phase for lastPlayer (again)
        //mother nature is on island group (index 1)
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(lastPlayer);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(1);

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //lastPlayer conquered islandGroup (index 2, which becomes index 1)
        //and created an archipelago (id 3, 4, 5, 6)
        newIndexMotherNature = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        assertEquals(1, newIndexMotherNature);
        assertEquals(lastPlayer.getColor(), game.getMotherNature().getCurrentIslandGroup().getConquerorColor());
        assertEquals(4, game.getIslandGroups().get(1).getIslands().size());
        expectedIDIslands = new ArrayList<>(Arrays.asList(3, 4, 5, 6));
        assertEquals(expectedIDIslands, game.getIslandGroups().get(1).getIslands().stream().map(Island::getIslandID).toList());
        assertEquals(7, game.getIslandGroups().size());

        assertEquals(1, game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup()));

        //start mother nature phase for firstPlayer
        //mother nature is on island group (index 1)
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(firstPlayer);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(4);

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        newIndexMotherNature = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        assertEquals(5, newIndexMotherNature);

        //start mother nature phase for firstPlayer
        //mother nature is on island group (index 5)
        //check steps on boundary
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(firstPlayer);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.getCurrentPhase().setNumberOfSteps(5);

        try {
            game.getCurrentPhase().play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        newIndexMotherNature = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());
        assertEquals(3, newIndexMotherNature);
    }
}