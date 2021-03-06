package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.network.server.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.model.phases.PhaseFactory;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.server.ClientHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    /** Class ClientHandlerTest defines a stub for ClientHandler class. */
    static class ClientHandlerTest implements ClientHandler {

        @Override
        public void sendMessage(Answer message) {

        }

        @Override
        public void disconnect() {

        }
    }

    GameManager gameManager;
    GameController gameController;
    Game game;
    Player chiara;
    Player stefano;

    /** Initializes values. */
    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
        gameController = gameManager.getGameController();
        gameManager.addClient(0, new ClientHandlerTest(), "Chiara");
        gameManager.addClient(1, new ClientHandlerTest(), "Stefano");

        game = gameManager.getGame();
        game.setNumberOfPlayers(2);
        chiara = new Player(game, "Chiara", PlayerColor.BLACK);
        game.addPlayer(chiara);
        stefano = new Player(game, "Stefano", PlayerColor.WHITE);
        game.addPlayer(stefano);

        ArrayList<Player> playingOrder = new ArrayList<>();
        playingOrder.add(chiara);
        playingOrder.add(stefano);
        game.setPlayingOrder(playingOrder);

        game.setGameMode(GameMode.EASY);
        game.setActivatedCharacter(new ConcreteCharacterFactory(game).createCharacter(0));
    }

    @AfterEach
    void tearDown() {
        gameManager = null;
        gameController = null;
        game = null;
    }

    @Test
    void propertyChange_PreparePhase() {
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.PREPARE_PHASE));

        WizardRequestMessage message = new WizardRequestMessage(WizardName.DRUID);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertEquals(WizardName.DRUID, chiara.getWizard().getName());
    }

    /** Tests the message received to choose an assistant */
    @Test
    void propertyChange_PlanningPhase() {
        chiara.setWizard(WizardName.DRUID);
        stefano.setWizard(WizardName.KING);

        game.setGameState(GameState.PLANNING_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        Gson gson = new Gson();

        AssistantRequestMessage messageC = new AssistantRequestMessage(5);
        String inputSerC = gson.toJson(messageC);
        messageC.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerC, messageC));
        gameController.propertyChange(evtC);

        game.setCurrentPlayer(stefano);
        AssistantRequestMessage messageS = new AssistantRequestMessage(7);
        String inputSerS = gson.toJson(messageS);
        messageS.setClientID(1);
        PropertyChangeEvent evtS = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerS, messageS));
        gameController.propertyChange(evtS);

        assertEquals(5, game.getPlayerPriority().get(chiara).getPriority());
        assertEquals(7, game.getPlayerPriority().get(stefano).getPriority());
    }

    /** Tests the message received to move a student from entrance to hall */
    @Test
    void propertyChange_MoveStudentPhase() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setActivatedCharacter(new ConcreteCharacterFactory(game).createCharacter(0));

        CreatureColor colorChosen = CreatureColor.RED;
        chiara.getBoard().addStudentToEntrance(colorChosen);

        MoveStudentMessage message = new MoveStudentMessage(colorChosen, 0);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertEquals(1, chiara.getBoard().getHall().getTableByColor(colorChosen).getLength());
    }

    /** Tests the message received to move mother nature */
    @Test
    void propertyChange_MoveMotherNaturePhase() {
        Map<Player, Assistant> playerPriority = new HashMap<>();
        playerPriority.put(chiara, new Assistant(10, 5));
        game.setPlayerPriority(playerPriority);

        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        //create island groups
        for(int i = 0; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
        game.setCurrentPlayer(chiara);
        game.setActivatedCharacter(new ConcreteCharacterFactory(game).createCharacter(0));
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        game.getMotherNature().setCurrentIslandGroup(game.getIslandGroupByIndex(5));

        MoveMotherNatureMessage message = new MoveMotherNatureMessage(3);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        int motherNatureNewIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());

        assertEquals(8,motherNatureNewIndex);
    }

    /** Tests the message received to pick a cloud */
    @Test
    void propertyChange_PickCloudPhase() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        //create clouds
        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            game.addCloud(new Cloud(i + 1));
        }
        //fill bag
        for(CreatureColor color : CreatureColor.values()) {
            for(int i = 0; i < 24; i++) {
                game.getBag().receiveStudent(color);
            }
        }
        //fill clouds
        for(Cloud cloud : game.getClouds()) {
            cloud.fill(game.getBag(), 2);
        }

        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.PICK_CLOUD_PHASE));

        List<CreatureColor> studentsOnCloudOne = game.getCloudByID(1).getStudents().stream().map(Creature::getColor).toList();

        PickCloudMessage message = new PickCloudMessage(1);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertEquals(3, chiara.getBoard().getEntrance().getStudents().size());
        assertTrue(chiara.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList().containsAll(studentsOnCloudOne));
    }

    /** Tests the end of the game due to the end of a player's towers */
    @Test
    void propertyChange_EndGamePhase() {
        Map<Player, Assistant> playerPriority = new HashMap<>();
        playerPriority.put(chiara, new Assistant(10, 5));
        game.setPlayerPriority(playerPriority);

        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        //create island groups
        for(int i = 0; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
        game.setCurrentPlayer(chiara);
        game.setActivatedCharacter(new ConcreteCharacterFactory(game).createCharacter(0));
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.MOVE_MOTHER_NATURE_PHASE));

        game.getMotherNature().setCurrentIslandGroup(game.getIslandGroupByIndex(5));
        chiara.getBoard().setTowers(0);

        MoveMotherNatureMessage message = new MoveMotherNatureMessage(3);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertEquals(chiara, game.getCurrentPhase().getWinner());
    }

    /** Tests the reception of a message sent by a player who is not the current one */
    @Test
    void propertyChange_WrongUser() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        PickCloudMessage message = new PickCloudMessage(1);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(1);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertEquals(chiara, game.getCurrentPlayer());
    }

    /** Tests the reception of a message to choose a wizard which is not available */
    @Test
    void propertyChange_PreparePhase_WrongWizard() {
        stefano.setWizard(WizardName.DRUID);
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.PREPARE_PHASE));

        WizardRequestMessage message = new WizardRequestMessage(WizardName.DRUID);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertNull(chiara.getWizard());
    }

    /** Tests the reception of a message to choose an assistant which is not available */
    @Test
    void propertyChange_PlanningPhase_WrongAssistant() {
        chiara.setWizard(WizardName.DRUID);
        stefano.setWizard(WizardName.KING);

        game.setGameState(GameState.PLANNING_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        Gson gson = new Gson();

        AssistantRequestMessage messageC = new AssistantRequestMessage(3);
        String inputSerC = gson.toJson(messageC);
        messageC.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerC, messageC));
        gameController.propertyChange(evtC);

        game.setCurrentPlayer(stefano);
        //tries to play the same assistant
        AssistantRequestMessage messageS = new AssistantRequestMessage(3);
        String inputSerS = gson.toJson(messageS);
        messageS.setClientID(1);
        PropertyChangeEvent evtS = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerS, messageS));
        gameController.propertyChange(evtS);

        //plays the correct assistant
        AssistantRequestMessage messageS2 = new AssistantRequestMessage(7);
        String inputSerS2 = gson.toJson(messageS2);
        messageS2.setClientID(1);
        PropertyChangeEvent evtS2 = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerS2, messageS2));
        gameController.propertyChange(evtS2);

        assertEquals(3, game.getPlayerPriority().get(chiara).getPriority());
        assertEquals(7, game.getPlayerPriority().get(stefano).getPriority());
    }

    /** Test receiving a message to move mother nature more steps than allowed */
    @Test
    void propertyChange_MoveMotherNaturePhase_TooManySteps() {
        Map<Player, Assistant> playerPriority = new HashMap<>();
        playerPriority.put(chiara, new Assistant(1, 1));
        game.setPlayerPriority(playerPriority);

        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        //create island groups
        for(int i = 0; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
        game.setCurrentPlayer(chiara);
        game.setActivatedCharacter(new ConcreteCharacterFactory(game).createCharacter(0));
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        game.getMotherNature().setCurrentIslandGroup(game.getIslandGroupByIndex(5));

        MoveMotherNatureMessage message = new MoveMotherNatureMessage(3);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        int motherNatureNewIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());

        assertEquals(5,motherNatureNewIndex);
    }

    /** Test receiving a message to choose a cloud which is not available */
    @Test
    void propertyChange_PickCloudPhase_WrongCloud() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        //create clouds
        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            game.addCloud(new Cloud(i + 1));
        }
        //fill bag
        for(CreatureColor color : CreatureColor.values()) {
            for(int i = 0; i < 24; i++) {
                game.getBag().receiveStudent(color);
            }
        }
        //fill clouds
        for(Cloud cloud : game.getClouds()) {
            cloud.fill(game.getBag(), 2);
        }

        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(GameState.PICK_CLOUD_PHASE));

        PickCloudMessage message = new PickCloudMessage(4);
        Gson gson = new Gson();
        String inputSer = gson.toJson(message);

        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        assertEquals(0, chiara.getBoard().getEntrance().getStudents().size());
    }

}