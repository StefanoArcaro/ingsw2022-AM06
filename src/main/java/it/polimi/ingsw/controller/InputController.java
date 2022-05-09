package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.ErrorMessage;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.Map;

/**
 * Controller Class that has the task of checking that the messages received by a client
 * are well-formed and consistent with the current game state.
 */
public class InputController {

    private final Game game;
    private final Map<Integer, ClientHandler> clients;

    /**
     * Default constructor.
     * @param game the game.
     * @param clients the map of client IDs to their client handlers.
     */
    public InputController(Game game, Map<Integer, ClientHandler> clients) {
        this.game = game;
        this.clients = clients;
    }

    /**
     * Checks that the message sent from client to server contains valid information.
     * @param pair JSON of the message and message received by server.
     * @return whether the message is correct or not.
     */
    public boolean checkOnMessageReceived(Map.Entry<String, Message> pair) {
        String input = pair.getKey();
        Message msg = pair.getValue();

        return switch (msg.getMessageType()) {
            case WIZARD_REQUEST_MESSAGE -> checkWizard(msg);
            case ASSISTANT_REQUEST_MESSAGE -> checkAssistant(msg);
            case MOVE_STUDENT_MESSAGE -> checkMoveStudent(msg);
            case MOVE_MOTHER_NATURE_MESSAGE -> checkMoveMotherNature(msg);
            case PICK_CLOUD_MESSAGE -> checkPickCloud(msg);
            case CHARACTER_INFO_REQUEST_MESSAGE -> checkCharacterInfo(input, msg);
            case CHARACTER_MESSAGE, CHARACTER_COLOR_MESSAGE,
                    CHARACTER_DOUBLE_COLOR_MESSAGE, CHARACTER_DESTINATION_MESSAGE,
                    CHARACTER_COLOR_DESTINATION_MESSAGE -> checkCharacter(input, msg);
            default -> false; //server shouldn't receive other type of messages
        };
    }

    /**
     * Checks if the wizard request is valid or not.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkWizard(Message msg) {
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if (game.getGameState() == GameState.PREPARE_PHASE) {
            return true;
        }

        clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        return false;
    }

    /**
     * Checks if the assistant request is valid or not.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkAssistant(Message msg) {
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(game.getGameState() == GameState.PLANNING_PHASE) {
            return true;
        }

        clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        return false;
    }

    /**
     * Checks if the move student request is valid or not.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkMoveStudent(Message msg) {
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(game.getGameState() == GameState.MOVE_STUDENT_PHASE) {
            return true;
        }

        clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        return false;
    }

    /**
     * Checks if the move mother nature request is valid or not.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkMoveMotherNature(Message msg) {
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(game.getGameState() == GameState.MOVE_MOTHER_NATURE_PHASE) {
            return true;
        }

        clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        return false;
    }

    /**
     * Checks if the pick cloud request is valid or not.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkPickCloud(Message msg) {
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(game.getGameState() == GameState.PICK_CLOUD_PHASE) {
            return true;
        }

        clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        return false;
    }

    /**
     * Checks if the character info request is valid or not.
     * @param input JSON of the message.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacterInfo(String input, Message msg) {
        Gson gson = new Gson();
        CharacterInfoRequestMessage message = gson.fromJson(input, CharacterInfoRequestMessage.class);

        int characterID = message.getCharacterID();
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(game.getGameMode() == GameMode.EXPERT) {
            if(game.getGameState() != GameState.LOBBY_PHASE) {
                return checkCharacterID(clientHandler, characterID);
            }
            clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        } else {
            clientHandler.sendMessage(new ErrorMessage("You have chosen the easy mode, there are no characters."));
        }
        return false;
    }

    /**
     * Checks if the request for the character to play is valid or not.
     * @param input JSON of the message.
     * @param msg the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacter(String input, Message msg) {
        Gson gson = new Gson();
        CharacterMessage message = gson.fromJson(input, CharacterMessage.class);

        int characterID = message.getCharacterID();
        ClientHandler clientHandler = clients.get(msg.getClientID());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(game.getGameMode() == GameMode.EXPERT) {
            if (correctPhase) {
                return checkCharacterID(clientHandler, characterID);
            }
            clientHandler.sendMessage(new ErrorMessage("The message you sent is not consistent with the current phase."));
        } else {
            clientHandler.sendMessage(new ErrorMessage("You have chosen the easy mode, there are no characters."));
        }
        return false;
    }

    /**
     * Checks if the character ID is valid or not.
     * @param clientHandler that received the message.
     * @param characterID the ID of the character to check.
     * @return whether the ID is valid or not.
     */
    private boolean checkCharacterID(ClientHandler clientHandler, int characterID) {
        if(game.getCharacterByID(characterID) != null) {
            return true;
        }
        clientHandler.sendMessage(new ErrorMessage("The character you asked for was not drawn in this game."));
        return false;
    }

}
