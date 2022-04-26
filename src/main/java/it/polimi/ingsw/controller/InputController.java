package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.view.View;

import java.util.Map;

/**
 * Controller Class that has the task of checking that the messages received by a client
 * are well-formed and consistent with the current game state.
 */
public class InputController {

    private final Game game;
    private final Map<String, View> clientsView;

    /**
     * Default constructor.
     * @param game the game.
     * @param clientsView the map of clients to their views.
     */
    public InputController(Game game, Map<String, View> clientsView) {
        this.game = game;
        this.clientsView = clientsView;
    }

    /**
     * Checks that the message sent from client to server contains valid information.
     * @param message the message received by server.
     * @return whether the message is correct or not.
     */
    public boolean checkOnMessageReceived(Message message) {
        return switch (message.getMessageType()) {
            case LOGIN_REQUEST_MESSAGE -> checkLogin((LoginRequestMessage) message);
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
     * Checks if the login request is valid or not.
     * @param message the message sent by the client.
     * @return whether the message contains valid information, and it's
     *            consistent with the current game state.
     */
    private boolean checkLogin(LoginRequestMessage message) {
        int numberOfPlayers = message.getNumberOfPlayers();
        int gameMode = message.getGameMode().getMode();
        String error;
        View view = clientsView.get(message.getNickname());

        if(game.getGameState() == GameState.LOBBY_PHASE) {
            if(numberOfPlayers >= 0 && numberOfPlayers <= 3) {
                if(gameMode == 0 || gameMode == 1) {
                    return true;
                } else {
                    error = "The game mode selected doesn't exist. Choose Easy or Expert";
                }
            } else {
                error = "Choose a number of player between 2 and 3";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        view.showErrorMessage(error);
        return false;
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
        View view = clientsView.get(message.getNickname());

        if (game.getGameState() == GameState.PREPARE_PHASE) {
            if (wizardID >= 0 && wizardID < 4) {
                return true;
            } else {
                error = "The wizard selected doesn't exist";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());

        if(game.getGameState() == GameState.PLANNING_PHASE) {
            if (priority >= 1 && priority <= 10) {
                return true;
            } else {
                error = "The assistant chosen doesn't exist.";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());

        if(game.getGameState() == GameState.MOVE_STUDENT_PHASE) {
            return checkColor(view, color) && checkDestination(view, destination);
        } else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());

        if(game.getGameState() == GameState.MOVE_MOTHER_NATURE_PHASE) {
            if(steps > 0 && steps < 6) {
                return true;
            } else {
                error = "This isn't a valid number for steps that mother nature has to take";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());

        if(game.getGameState() == GameState.PICK_CLOUD_PHASE) {
            if (cloudID >= 1 && cloudID <= 3) {
                return true;
            } else {
                error = "The cloud chosen doesn't exist.";
            }
        } else {
            error = "The message sent is not consistent with the phase playing";
        }
        view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());

        if(game.getGameState() != GameState.LOBBY_PHASE) {
            return checkCharacterID(view, characterID);
        } else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(view, characterID);
        } else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(view, characterID) && checkColor(view, color);
        } else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkColor(view, firstColor) && checkColor(view, secondColor);
        } else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(view, characterID) && checkDestination(view, destination);
        }else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
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
        View view = clientsView.get(message.getNickname());
        boolean correctPhase = game.getGameState() != GameState.LOBBY_PHASE &&
                game.getGameState() != GameState.PREPARE_PHASE &&
                game.getGameState() != GameState.PLANNING_PHASE;

        if(correctPhase) {
            return checkCharacterID(view, characterID) && checkColor(view, color)
                    && checkDestination(view, destination);
        } else {
            error = "The message sent is not consistent with the phase playing";
            view.showErrorMessage(error);
        }
        return  false;
    }

    /**
     * Checks if the character ID is valid or not.
     * @param view view of the client who sent the message.
     * @param characterID the ID of the character to check.
     * @return whether the ID is valid or not.
     */
    private boolean checkCharacterID(View view, int characterID) {
        if(game.getCharacterByID(characterID) != null) {
            return true;
        }
        view.showErrorMessage("The character ID chosen doesn't exist.");
        return false;
    }

    /**
     * Checks if the color chosen is valid or not.
     * @param view view of the client who sent the message.
     * @param color the color to check.
     * @return whether the color is valid or not.
     */
    private boolean checkColor(View view, int color) {
        if(color >= 0 && color <= 4) {
            return true;
        }
        view.showErrorMessage("The color chosen doesn't exist.");
        return false;
    }

    /**
     * Checks if the destination chosen is valid or not.
     * @param view view of the client who sent the message.
     * @param destination the destination to check.
     * @return whether the destination is valid or not.
     */
    private boolean checkDestination(View view, int destination) {
        if(destination >= 0 && destination <= 12) {
            return true;
        }
        view.showErrorMessage("The destination chosen doesn't exist.");
        return false;
    }
}
