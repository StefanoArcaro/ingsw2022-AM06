package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;
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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

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

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
        gameController = gameManager.getGameController();
        gameManager.addClient(0, new CharacterControllerTest.ClientHandlerTest(), "Chiara");
        gameManager.addClient(1, new CharacterControllerTest.ClientHandlerTest(), "Stefano");

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

        game.setGameMode(GameMode.EXPERT);
        //add default character
        ConcreteCharacterFactory characterFactory = new ConcreteCharacterFactory(game);
        Character defaultCharacter = characterFactory.createCharacter(CharacterID.CHARACTER_NONE.getID());

        game.addDrawnCharacter(defaultCharacter);
        game.setActivatedCharacter(defaultCharacter);
        //fill bag
        for(CreatureColor color : CreatureColor.values()) {
            for(int i = 0; i < 24; i++) {
                game.getBag().receiveStudent(color);
            }
        }
        //add all characters
        for(int i = 1; i <= 12; i++){
            Character character = new ConcreteCharacterFactory(game).createCharacter(i);
            character.initialPreparation();
            game.addDrawnCharacter(character);
        }
        //distribute coins
        for(int i = 0; i < 5; i++) {
            chiara.receiveCoin();
        }

    }

    @AfterEach
    void tearDown() {
        gameManager = null;
        gameController = null;
        game = null;
    }

    @Test
    void onMessageReceived_CharacterMessage() {
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
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        Gson gson = new Gson();

        CharacterMessage messageChar = new CharacterMessage(4);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(4), game.getActivatedCharacter());

        game.getMotherNature().setCurrentIslandGroup(game.getIslandGroupByIndex(2));

        MoveMotherNatureMessage message = new MoveMotherNatureMessage(7);
        String inputSer = gson.toJson(message);
        message.setClientID(0);
        PropertyChangeEvent evt = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSer, message));

        gameController.propertyChange(evt);

        int motherNatureNewIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());

        assertEquals(9,motherNatureNewIndex);
    }

    @Test
    void onMessageReceived_CharacterDestinationMessage() {
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
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        Gson gson = new Gson();

        CharacterDestinationMessage messageChar = new CharacterDestinationMessage(5, 1);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(5), game.getActivatedCharacter());
        assertEquals(1, game.getIslandGroupByIndex(0).getNumberOfBanCardPresent());
    }

    @Test
    void onMessageReceived_CharacterColorMessage() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        //instantiates professors
        for(CreatureColor color : CreatureColor.values()) {
            game.addProfessor(new Professor(color));
        }

        Gson gson = new Gson();

        CreatureColor colorChosen = game.getCharacterByID(11).getStudents().get(0).getColor();
        CharacterColorMessage messageChar = new CharacterColorMessage(11, colorChosen);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(11), game.getActivatedCharacter());
        assertEquals(1, chiara.getBoard().getHall().getTableByColor(colorChosen).getLength());
    }

    @Test
    void onMessageReceived_CharacterColorDestinationMessage() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        //create island groups
        for(int i = 0; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
        Gson gson = new Gson();

        CreatureColor colorChosen = game.getCharacterByID(1).getStudents().get(0).getColor();
        CharacterColorDestinationMessage messageChar = new CharacterColorDestinationMessage(1, colorChosen, 3);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(1), game.getActivatedCharacter());
        assertTrue(game.getIslandByID(3).getStudents().stream().map(Creature::getColor).toList().contains(colorChosen));
    }

    @Test
    void onMessageReceived_CharacterDoubleColorMessage() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        //instantiates professors
        for(CreatureColor color : CreatureColor.values()) {
            game.addProfessor(new Professor(color));
        }
        chiara.getBoard().addStudentToEntrance(CreatureColor.RED);
        Gson gson = new Gson();

        CreatureColor colorChosenFromCard = game.getCharacterByID(7).getStudents().get(0).getColor();
        CreatureColor colorChosenFromEntrance = CreatureColor.RED;
        CharacterDoubleColorMessage messageChar = new CharacterDoubleColorMessage(7, colorChosenFromCard, colorChosenFromEntrance);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(7), game.getActivatedCharacter());
        assertTrue(chiara.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList().contains(colorChosenFromCard));
        assertTrue(game.getCharacterByID(7).getStudents().stream().map(Creature::getColor).toList().contains(colorChosenFromEntrance));
    }

    @Test
    void onMessageReceived_CharacterDoubleColorMessage_NoMoney() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        //instantiates professors
        for(CreatureColor color : CreatureColor.values()) {
            game.addProfessor(new Professor(color));
        }

        //remove coins
        chiara.spendCoins(chiara.getCoins());

        Gson gson = new Gson();

        CreatureColor colorChosenFromCard = game.getCharacterByID(7).getStudents().get(0).getColor();
        CreatureColor colorChosenFromEntrance = CreatureColor.RED;
        CharacterDoubleColorMessage messageChar = new CharacterDoubleColorMessage(7, colorChosenFromCard, colorChosenFromEntrance);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(0), game.getActivatedCharacter());
        assertFalse(chiara.getBoard().getEntrance().getStudents().stream().map(Creature::getColor).toList().contains(colorChosenFromCard));
    }

    @Test
    void onMessageReceived_CharacterDestinationMessage_NoMoney() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        //remove coins
        chiara.spendCoins(chiara.getCoins());

        Gson gson = new Gson();

        CharacterDestinationMessage messageChar = new CharacterDestinationMessage(3, 4);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(0), game.getActivatedCharacter());
    }

    @Test
    void onMessageReceived_CharacterColorDestinationMessage_WrongDestination() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        //create island groups
        for(int i = 0; i <= 12; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
        Gson gson = new Gson();

        CreatureColor colorChosen = game.getCharacterByID(1).getStudents().get(0).getColor();
        CharacterColorDestinationMessage messageChar = new CharacterColorDestinationMessage(1, colorChosen, 20);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(0), game.getActivatedCharacter());
    }

    @Test
    void onMessageReceived_CharacterInfoMessage() {
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        Gson gson = new Gson();

        CharacterInfoRequestMessage messageChar = new CharacterInfoRequestMessage(1);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(0), game.getActivatedCharacter());
    }

    @Test
    void onMessageReceived_CharacterMessage_NoMoney() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        //remove coins
        chiara.spendCoins(chiara.getCoins());

        Gson gson = new Gson();

        CharacterMessage messageChar = new CharacterMessage(4);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(0), game.getActivatedCharacter());
    }

    @Test
    void onMessageReceived_CharacterColorMessage_NoMoney() {
        game.setGameState(GameState.MOVE_MOTHER_NATURE_PHASE);
        game.setCurrentPlayer(chiara);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));

        //remove coins
        chiara.spendCoins(chiara.getCoins());

        Gson gson = new Gson();

        CharacterColorMessage messageChar = new CharacterColorMessage(9, CreatureColor.BLUE);
        String inputSerChar = gson.toJson(messageChar);
        messageChar.setClientID(0);
        PropertyChangeEvent evtC = new PropertyChangeEvent(this, null, null, new AbstractMap.SimpleEntry<>(inputSerChar, messageChar));
        gameController.propertyChange(evtC);

        assertEquals(game.getCharacterByID(0), game.getActivatedCharacter());
    }



}