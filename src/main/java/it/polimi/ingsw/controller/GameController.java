package it.polimi.ingsw.controller;

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


    //todo: sistemare l'invio dei messaggi
    //todo: in risposta al messaggio del client, se serve mandare una risposta del server Answer

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
        Message message = (Message) evt.getNewValue();

        if(!inputController.checkOnMessageReceived(message)) {
            return;
        }

        switch(model.getGameState()) {
            case PREPARE_PHASE:
                if(checkUser(message)) {
                    prepareState(message);
                }
                break;
            case PLANNING_PHASE:
                if(checkUser(message)) {
                    planningState(message);
                }
                break;
            case MOVE_STUDENT_PHASE:
                if(checkUser(message)) {
                    moveStudentPhase(message);
                }
                break;
            case MOVE_MOTHER_NATURE_PHASE:
                if(checkUser(message)) {
                    moveMotherNaturePhase(message);
                }
                break;
            case PICK_CLOUD_PHASE:
                if(checkUser(message)) {
                    pickCloudPhase(message);
                }
                break;
            default: //end game states and lobby
                //todo: clientsView.get(message.getNickname()).showErrorMessage("The game is over, you can't send a message.");
        }
    }

    /**
     * Check if the message is sent by the current player
     * @param message message sent by client
     * @return whether the message is sent by the current player or not
     */
    private boolean checkUser(Message message) {
        ClientHandler clientHandler = clients.get(message.getClientID());

        boolean checkUser = message.getNickname().equalsIgnoreCase(model.getCurrentPlayer().getNickname());

        if(!checkUser){
            clientHandler.sendMessage(new ErrorMessage("You aren't the current player!"));
            return false;
        }

        return true;
    }

    /**
     * Assigns a wizard to the current player.
     * @param message the message received by the current player.
     */
    private void prepareState(Message message) {
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(message.getMessageType() == MessageType.WIZARD_REQUEST_MESSAGE) {
            WizardName wizardName = ((WizardRequestMessage)message).getWizardName();
            model.getCurrentPhase().setWizardID(wizardName.getId());

            try {
                model.getCurrentPhase().play();
            } catch (Exception e) {
                clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
            }
        }else if(message.getMessageType() == MessageType.CHARACTER_INFO_REQUEST_MESSAGE) {
            characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to play an assistant.
     * @param message the message sent by the current player.
     */
    private void planningState(Message message) {
        ClientHandler clientHandler = clients.get(message.getClientID());

        if(message.getMessageType() == MessageType.ASSISTANT_REQUEST_MESSAGE) {
            int assistantID = ((AssistantRequestMessage)message).getAssistantID();
            model.getCurrentPhase().setPriority(assistantID);

            try {
                model.getCurrentPhase().play();
            } catch (Exception e) {
                clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
            }
        }else if(message.getMessageType() == MessageType.CHARACTER_INFO_REQUEST_MESSAGE) {
            characterController.onMessageReceived(message);
        }
    }

    /**
     * Switch on type messages that can be received in move student phase: move a student or play a character
     * @param message the message sent by the current player.
     */
    private void moveStudentPhase(Message message) {
        if (message.getMessageType() == MessageType.MOVE_STUDENT_MESSAGE) {
            doMoveStudent((MoveStudentMessage) message);
        } else {
            characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to move a student to an island or to his hall.
     * @param message the message sent by the current player.
     */
    private void doMoveStudent(MoveStudentMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        CreatureColor color = message.getColor();
        int destination = message.getDestination();

        model.getCurrentPhase().setCreatureColorIndex(color.getIndex());
        model.getCurrentPhase().setStudentDestinationIndex(destination);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Switch on type messages that can be received in move mother nature phase:
     * move mother nature or play a character
     * @param message the message sent by the current player.
     */
    private void moveMotherNaturePhase(Message message) {
        if (message.getMessageType() == MessageType.MOVE_MOTHER_NATURE_MESSAGE) {
            doMoveMotherNature((MoveMotherNatureMessage) message);
        } else {
            characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to move mother nature.
     * @param message the message sent by the current player.
     */
    private void doMoveMotherNature(MoveMotherNatureMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int numberOfSteps = message.getNumberOfSteps();

        model.getCurrentPhase().setNumberOfSteps(numberOfSteps);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
        checkEndGame();
    }

    /**
     * Switch on type messages that can be received in move pick cloud phase:
     * choose a cloud or play a character
     * @param message the message sent by the current player.
     */
    private void pickCloudPhase(Message message) {
        if (message.getMessageType() == MessageType.PICK_CLOUD_MESSAGE) {
            doPickCloud((PickCloudMessage) message);
        } else {
            characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to choose an island to pick.
     * @param message the message sent by the current player.
     */
    private void doPickCloud(PickCloudMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int cloudID = message.getCloudID();

        model.getCurrentPhase().setCloudID(cloudID);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
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
