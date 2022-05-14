package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.phases.PhaseFactory;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.server.ClientHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InputControllerTest {

    static class ClientHandlerTest implements ClientHandler {

        @Override
        public void sendMessage(Answer message) {

        }

        @Override
        public void disconnect() {

        }
    }

    InputController inputController;
    Game game;
    Player chiara;
    Player stefano;

    @BeforeEach
    void setUp() {

        Map<Integer, ClientHandler> clients = new HashMap<>();
        clients.put(0, new InputControllerTest.ClientHandlerTest());
        clients.put(1, new InputControllerTest.ClientHandlerTest());
        game = new Game();

        inputController = new InputController(game, clients);

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
    }

    @AfterEach
    void tearDown() {
        game = null;
        inputController = null;
    }

    @Test
    void checkOnMessageReceived_WrongPhaseWizard() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        WizardRequestMessage message = new WizardRequestMessage(WizardName.DRUID);
        String input = "wizard druid";
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(input, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_WrongPhaseAssistant() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        AssistantRequestMessage message = new AssistantRequestMessage(5);
        String input = "assistant 5";
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(input, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_WrongPhaseMoveStudent() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        MoveStudentMessage message = new MoveStudentMessage(CreatureColor.RED, 1);
        String input = "movestudent red 1";
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(input, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_WrongPhaseMoveMotherNature() {
        game.setGameState(GameState.PICK_CLOUD_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        MoveMotherNatureMessage message = new MoveMotherNatureMessage(3);
        String input = "movemothernature 3";
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(input, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_WrongPhasePickCloud() {
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        PickCloudMessage message = new PickCloudMessage(1);
        String input = "pickcloud 1";
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(input, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_WrongPhaseCharacter() {
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        CharacterMessage message = new CharacterMessage(4);
        String inputSer = new Gson().toJson(message);
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(inputSer, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_CharacterInfoNoCharacter() {
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        CharacterInfoRequestMessage message = new CharacterInfoRequestMessage(4);
        String inputSer = new Gson().toJson(message);
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(inputSer, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_CharacterInfoEasyMode() {
        game.setGameMode(GameMode.EASY);
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        CharacterInfoRequestMessage message = new CharacterInfoRequestMessage(4);
        String inputSer = new Gson().toJson(message);
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(inputSer, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_CharacterInfoWrongPhase() {
        game.setGameState(GameState.LOBBY_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        CharacterInfoRequestMessage message = new CharacterInfoRequestMessage(4);
        String inputSer = new Gson().toJson(message);
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(inputSer, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_WrongMessageReceivedByServer() {
        game.setGameState(GameState.PREPARE_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        LoginRequestMessage message = new LoginRequestMessage("pippo", NumberOfPlayers.THREE_PLAYERS, GameMode.EXPERT);
        String inputSer = new Gson().toJson(message);
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(inputSer, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }

    @Test
    void checkOnMessageReceived_CharacterToPlayEasyMode() {
        game.setGameMode(GameMode.EASY);
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
        game.setCurrentPhase(new PhaseFactory(game).createPhase(game.getGameState()));
        game.setCurrentPlayer(chiara);

        CharacterMessage message = new CharacterMessage(4);
        String inputSer = new Gson().toJson(message);
        message.setClientID(0);
        Map.Entry<String, Message> pair = new AbstractMap.SimpleEntry<>(inputSer, message);

        assertFalse(inputController.checkOnMessageReceived(pair));
    }







}