package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.ErrorMessage;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.Map;

//TODO: fix comments

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
     * @param clients the map of clients to their views. //todo change
     */
    public InputController(Game game, Map<Integer, ClientHandler> clients) {
        this.game = game;
        this.clients = clients;
    }

    /**
     * Checks that the message sent from client to server contains valid information.
     * @param message the message received by server.
     * @return whether the message is correct or not.
     */
    public boolean checkOnMessageReceived(Message message) {
        return switch (message.getMessageType()) {
            case WIZARD_REQUEST_MESSAGE -> checkWizard((WizardRequestMessage) message);
            case ASSISTANT_REQUEST_MESSAGE -> checkAssistant((AssistantRequestMessage) message);
            case MOVE_STUDENT_MESSAGE -> checkMoveStudent((MoveStudentMessage) message);
            case MOVE_MOTHER_NATURE_MESSAGE -> checkMoveMotherNature((MoveMotherNatureMessage) message);
            case PICK_CLOUD_MESSAGE -> checkPickCloud((PickCloudMessage) message);
            case CHARACTER_INFO_REQUEST_MESSAGE -> checkCharacterInfo((CharacterInfoRequestMessage) message);
            case CHARACTER_MESSAGE -> checkCharacter((CharacterMessage) message);
            case CHARACTER_COLOR_MESSAGE -> checkCharacterColor((CharacterColorMessage) message);
            case CHARACTER_DOUBLE_COLOR_MESSAGE -> checkCharacterDoubleColor((CharacterDoubleColorMessage) message);
            case CHARACTER_DESTINATION_MESSAGE -> checkCharacterDestination((CharacterDestinationMessage) message);
            case CHARACTER_COLOR_DESTINATION_MESSAGE -> checkCharacterColorDestination((CharacterColorDestinationMessage) message);
            default -> false; //server shouldn't receive other type of messages
        };
    }

    /**
     * Checks if the wizard request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkWizard(WizardRequestMessage message) {
        int wizardID = message.getWizardName().getId();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());

        if (game.getGameState() == GameState.PREPARE_PHASE) {
            if (wizardID >= 0 && wizardID < 4) {
                return true;
            } else {
                error = "The wizard selected doesn't exist";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }

        clientHandler.sendMessage(new ErrorMessage(error));

        return false;
    }

    /**
     * Checks if the assistant request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkAssistant(AssistantRequestMessage message) {
        int priority = message.getAssistantID();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(game.getGameState() == GameState.PLANNING_PHASE) {
            if (priority >= 1 && priority <= 10) {
                return true;
            } else {
                error = "The assistant chosen doesn't exist.";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        clientHandler.sendMessage(new ErrorMessage(error));
        return false;
    }

    /**
     * Checks if the move student request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkMoveStudent(MoveStudentMessage message) {
        int color = message.getColor().getIndex();
        int destination = message.getDestination();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(game.getGameState() == GameState.MOVE_STUDENT_PHASE) {
            return checkColor(clientHandler, color) && checkDestination(clientHandler, destination);
        } else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return false;
    }

    /**
     * Checks if the move mother nature request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkMoveMotherNature(MoveMotherNatureMessage message) {
        int steps = message.getNumberOfSteps();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(game.getGameState() == GameState.MOVE_MOTHER_NATURE_PHASE) {
            if(steps > 0 && steps < 6) {
                return true;
            } else {
                error = "This isn't a valid number for steps that mother nature has to take";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        clientHandler.sendMessage(new ErrorMessage(error));
        return false;
    }

    /**
     * Checks if the pick cloud request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkPickCloud(PickCloudMessage message) {
        int cloudID = message.getCloudID();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(game.getGameState() == GameState.PICK_CLOUD_PHASE) {
            if (cloudID >= 1 && cloudID <= 3) {
                return true;
            } else {
                error = "The cloud chosen doesn't exist.";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        clientHandler.sendMessage(new ErrorMessage(error));
        return false;
    }

    /**
     * Checks if the character info request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacterInfo(CharacterInfoRequestMessage message) {
        int characterID = message.getCharacterID();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(game.getGameState() != GameState.LOBBY_PHASE) {
            return checkCharacterID(clientHandler, characterID);
        } else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return  false;
    }

    /**
     * Checks if the request for the character to play is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacter(CharacterMessage message) {
        int characterID = message.getCharacterID();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(clientHandler, characterID);
        } else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return  false;
    }

    /**
     * Checks if the request for the character to play is valid or not.
     * The character chosen needs a color as parameter.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacterColor(CharacterColorMessage message) {
        int characterID = message.getCharacterID();
        int color = message.getColor().getIndex();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(clientHandler, characterID) && checkColor(clientHandler, color);
        } else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return  false;
    }

    /**
     * Checks if the request for the character to play is valid or not.
     * The character chosen needs two colors as parameters.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacterDoubleColor(CharacterDoubleColorMessage message) {
        int firstColor = message.getFirstColor().getIndex();
        int secondColor = message.getSecondColor().getIndex();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkColor(clientHandler, firstColor) && checkColor(clientHandler, secondColor);
        } else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return  false;
    }


    /**
     * Checks if the request for the character to play is valid or not.
     * The character chosen needs a destination as parameter.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacterDestination(CharacterDestinationMessage message) {
        int characterID = message.getCharacterID();
        int destination = message.getDestination();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(clientHandler, characterID) && checkDestination(clientHandler, destination);
        }else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return  false;
    }

    /**
     * Checks if the request for the character to play is valid or not.
     * The character chosen needs a color and a destination as parameters.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkCharacterColorDestination(CharacterColorDestinationMessage message) {
        int characterID = message.getCharacterID();
        int color = message.getColor().getIndex();
        int destination = message.getDestination();
        String error;
        ClientHandler clientHandler = clients.get(message.getClientID());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(clientHandler, characterID) && checkColor(clientHandler, color)
                    && checkDestination(clientHandler, destination);
        } else {
            error = "The message sent is not consistent with the phase playing";
            clientHandler.sendMessage(new ErrorMessage(error));
        }
        return  false;
    }

    /**
     * Checks if the character ID is valid or not.
     * @param clientHandler view of the client who sent the message. //todo
     * @param characterID the ID of the character to check.
     * @return whether the ID is valid or not.
     */
    private boolean checkCharacterID(ClientHandler clientHandler, int characterID) {
        if(game.getCharacterByID(characterID) != null) {
            return true;
        }
        clientHandler.sendMessage(new ErrorMessage("The character ID chosen doesn't exist."));
        return false;
    }

    /**
     * Checks if the color chosen is valid or not.
     * @param clientHandler view of the client who sent the message. //todo
     * @param color the color to check.
     * @return whether the color is valid or not.
     */
    private boolean checkColor(ClientHandler clientHandler, int color) {
        if(color >= 0 && color <= 4) {
            return true;
        }
        clientHandler.sendMessage(new ErrorMessage("The color chosen doesn't exist."));
        return false;
    }

    /**
     * Checks if the destination chosen is valid or not.
     * @param clientHandler view of the client who sent the message. //todo
     * @param destination the destination to check.
     * @return whether the destination is valid or not.
     */
    private boolean checkDestination(ClientHandler clientHandler, int destination) {
        if(destination >= 0 && destination <= 12) {
            return true;
        }
        clientHandler.sendMessage(new ErrorMessage("The destination chosen doesn't exist."));
        return false;
    }
}
