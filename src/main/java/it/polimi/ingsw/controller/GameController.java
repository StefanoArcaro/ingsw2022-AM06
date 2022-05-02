package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.phases.EndgamePhase;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.ErrorMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;
import it.polimi.ingsw.network.message.serverToclient.WinnerMessage;
import it.polimi.ingsw.network.server.ClientHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * Controller class that manages the evolution of the game.
 */
public class GameController implements PropertyChangeListener {

    private final GameManager gameManager;

    private final Game model;
    private final Map<Integer, ClientHandler> clients;

    private final InputController inputController;
    private final CharacterController characterController;


    //todo: sistemare l'invio dei messaggi - in risposta al messaggio del client, se serve mandare una risposta del server Answer

    /**
     * Default constructor.
     * @param model the game.
     */
    public GameController(GameManager gameManager, Game model) {
        this.gameManager = gameManager;
        this.model = model;
        this.clients = gameManager.getClients();
        this.inputController = new InputController(model, gameManager.getClients());
        this.characterController = new CharacterController(model, clients);
    }

    /**
     * Determines the action to make based on the game state and the message received by the client.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Map.Entry<String, Message> pair = (Map.Entry<String, Message>) evt.getNewValue();

        String input = pair.getKey();
        Message msg = pair.getValue();

        if(checkUser(msg)) {
            if (!inputController.checkOnMessageReceived(pair)) {
                return;
            }
        } else {
            return;
        }

        switch(model.getGameState()) {
            case PREPARE_PHASE:
                if(checkUser(msg)) {
                    prepareState(input, msg);
                }
                break;
            case PLANNING_PHASE:
                if(checkUser(msg)) {
                    planningState(input, msg);
                }
                break;
            case MOVE_STUDENT_PHASE:
                if(checkUser(msg)) {
                    moveStudentState(input, msg);
                }
                break;
            case MOVE_MOTHER_NATURE_PHASE:
                if(checkUser(msg)) {
                    moveMotherNatureState(input, msg);
                }
                break;
            case PICK_CLOUD_PHASE:
                if(checkUser(msg)) {
                    pickCloudState(input, msg);
                }
                break;
            default: //end game states and lobby
                //todo: clientsView.get(message.getNickname()).showErrorMessage("The game is over, you can't send a message.");
        }
    }

    /**
     * Check if the message is sent by the current player.
     * @param message message sent by client.
     * @return whether the message is sent by the current player or not.
     */
    private boolean checkUser(Message message) {
        ClientHandler clientHandler = clients.get(message.getClientID());

        String nickname = getNicknameByClientId(message.getClientID());

        if(nickname != null) {
            boolean checkUser = nickname.equalsIgnoreCase(model.getCurrentPlayer().getNickname());

            if (!checkUser) {
                clientHandler.sendMessage(new ErrorMessage("You aren't the current player!"));
                return false;
            }
            return true;
        }

        clientHandler.sendMessage(new ErrorMessage("Nickname doesn't exist."));
        return false;
    }

    /**
     * Looks in the client map for the nickname that matches the supplied client id.
     * @param clientID the client id to match.
     * @return the nickname that matches the client id given.
     */
    private String getNicknameByClientId(int clientID) {
        for(String name : gameManager.getNicknameToId().keySet()) {
            if(gameManager.getNicknameToId().get(name) == clientID) {
                return name;
            }
        }
        return null;
    }

    /**
     * Assigns a wizard to the current player.
     * @param input JSON of the message.
     * @param msg the message received by the current player.
     */
    private void prepareState(String input, Message msg) {
        Gson gson = new Gson();
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(msg.getMessageType() == MessageType.WIZARD_REQUEST_MESSAGE) {
            WizardRequestMessage message = gson.fromJson(input, WizardRequestMessage.class);

            WizardName wizardName = message.getWizardName();
            model.getCurrentPhase().setWizardID(wizardName.getId());

            try {
                model.getCurrentPhase().play();
            } catch (Exception e) {
                clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
            }

        }else if(msg.getMessageType() == MessageType.CHARACTER_INFO_REQUEST_MESSAGE) {
            characterController.onMessageReceived(input, msg);
        }
    }

    /**
     * Allows the current player to play an assistant.
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void planningState(String input, Message msg) {
        Gson gson = new Gson();
        ClientHandler clientHandler = clients.get(msg.getClientID());

        if(msg.getMessageType() == MessageType.ASSISTANT_REQUEST_MESSAGE) {
            AssistantRequestMessage message = gson.fromJson(input, AssistantRequestMessage.class);

            int assistantID = message.getAssistantID();
            model.getCurrentPhase().setPriority(assistantID);

            try {
                model.getCurrentPhase().play();
            } catch (Exception e) {
                clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
            }

        }else if(msg.getMessageType() == MessageType.CHARACTER_INFO_REQUEST_MESSAGE) {
            characterController.onMessageReceived(input, msg);
        }
    }

    /**
     * Switch on type messages that can be received in move student phase: move a student or play a character
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void moveStudentState(String input, Message msg) {
        if (msg.getMessageType() == MessageType.MOVE_STUDENT_MESSAGE) {
            doMoveStudent(input, msg);
        } else {
            characterController.onMessageReceived(input, msg);
        }
    }

    /**
     * Allows the current player to move a student to an island or to his hall.
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void doMoveStudent(String input, Message msg) {
        Gson gson = new Gson();
        MoveStudentMessage message = gson.fromJson(input, MoveStudentMessage.class);

        CreatureColor color = message.getColor();
        int destination = message.getDestination();

        model.getCurrentPhase().setCreatureColorIndex(color.getIndex());
        model.getCurrentPhase().setStudentDestinationIndex(destination);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Switch on type messages that can be received in move mother nature phase:
     * move mother nature or play a character
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void moveMotherNatureState(String input, Message msg) {
        if (msg.getMessageType() == MessageType.MOVE_MOTHER_NATURE_MESSAGE) {
            doMoveMotherNature(input, msg);
        } else {
            characterController.onMessageReceived(input, msg);
        }
    }

    /**
     * Allows the current player to move mother nature.
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void doMoveMotherNature(String input, Message msg) {
        Gson gson = new Gson();
        MoveMotherNatureMessage message = gson.fromJson(input, MoveMotherNatureMessage.class);

        int numberOfSteps = message.getNumberOfSteps();

        model.getCurrentPhase().setNumberOfSteps(numberOfSteps);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
        checkEndGame();
    }

    /**
     * Switch on type messages that can be received in move pick cloud phase:
     * choose a cloud or play a character
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void pickCloudState(String input, Message msg) {
        if (msg.getMessageType() == MessageType.PICK_CLOUD_MESSAGE) {
            doPickCloud(input, msg);
        } else {
            characterController.onMessageReceived(input, msg);
        }
    }

    /**
     * Allows the current player to choose an island to pick.
     * @param input JSON of the message.
     * @param msg the message sent by the current player.
     */
    private void doPickCloud(String input, Message msg) {
        Gson gson = new Gson();
        PickCloudMessage message = gson.fromJson(input, PickCloudMessage.class);

        int cloudID = message.getCloudID();

        model.getCurrentPhase().setCloudID(cloudID);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
        checkEndGame();
    }

    /**
     * Checks if the game has to end.
     * This method is called after move mother nature phase and pick cloud phase:
     * these are the only moments in which the game could end.
     */
    private void checkEndGame() {
        int END_GAME = 6;

        if(model.getGameState().getCode() >= END_GAME) {
            ((EndgamePhase)model.getCurrentPhase()).play();
            Player winner = model.getCurrentPhase().getWinner();

            gameManager.singleSend(new GenericMessage("You won! Congratulations!"), winner.getNickname());
            gameManager.sendAllExcept(new WinnerMessage(winner.getNickname()), winner.getNickname());

            //todo end of the game
        }
    }
}