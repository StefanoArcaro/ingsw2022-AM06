package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.phases.EndgamePhase;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class that manages the evolution of the game.
 */
public class GameController implements Observer {

    private final Game model;
    private final Map<String, View> clientsView; //todo
    private final InputController inputController;
    private final CharacterController characterController;

    /**
     * Default constructor.
     * @param model the game.
     */
    public GameController(Game model) {
        this.model = model;
        this.clientsView = new HashMap<>(); //todo
        this.inputController = new InputController(model, clientsView);
        this.characterController = new CharacterController(model, clientsView);
    }

    /**
     * Determines the action to make based on the game state and the message received.
     * @param message the message sent by the current player.
     */
    public void onReceivedMessage(Message message) {

        if(!inputController.checkOnMessageReceived(message)){
            return;
        }

        switch(model.getGameState()) {
            case LOBBY_PHASE:
                loginState(message);
                break;
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
            default: //end game states
                clientsView.get(message.getNickname()).showErrorMessage("The game is over, you can't send a message.");
        }
    }

    /**
     * Check if the message is sent by the current player
     * @param message message sent by client
     * @return whether the message is sent by the current player or not
     */
    private boolean checkUser(Message message) {
        View view = clientsView.get(message.getNickname());
        boolean checkUser = message.getNickname().equalsIgnoreCase(model.getCurrentPlayer().getNickname());

        if(!checkUser){
            view.showErrorMessage("You aren't the current player!");
            return false;
        }

        return true;
    }


    /**
     * Adds a player to the game.
     * @param message the message received by the current client.
     */
    private void loginState(Message message) {
        View view = clientsView.get(message.getNickname());

        if(message.getMessageType() == MessageType.LOGIN_REQUEST_MESSAGE) {
            String nickname = message.getNickname();

            //if first player: set number of player and game mode
            if(model.getPlayers().size() == 0) {
                int numberOfPlayers = ((LoginRequestMessage)message).getNumberOfPlayers();
                GameMode gameMode = ((LoginRequestMessage)message).getGameMode();
                model.setNumberOfPlayers(numberOfPlayers);
                model.setGameMode(gameMode);
            }

            model.getCurrentPhase().setPlayerNickname(nickname);

            try{
                model.getCurrentPhase().play();
            } catch(Exception e) {
                view.showErrorMessage(e.getMessage());
            }

            //todo: gestione multi partita
        }
    }

    /**
     * Assigns a wizard to the current player.
     * @param message the message received by the current player.
     */
    private void prepareState(Message message) {
        View view = clientsView.get(message.getNickname());

        if(message.getMessageType() == MessageType.WIZARD_REQUEST_MESSAGE) {
            WizardName wizardName = ((WizardRequestMessage)message).getWizardName();
            model.getCurrentPhase().setWizardID(wizardName.getId());

            try {
                model.getCurrentPhase().play();
            } catch (Exception e) {
                view.showErrorMessage(e.getMessage());
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
        View view = clientsView.get(message.getNickname());

        if(message.getMessageType() == MessageType.ASSISTANT_REQUEST_MESSAGE) {
            int assistantID = ((AssistantRequestMessage)message).getAssistantID();
            model.getCurrentPhase().setPriority(assistantID);

            try {
                model.getCurrentPhase().play();
            } catch (Exception e) {
                view.showErrorMessage(e.getMessage());
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
        switch (message.getMessageType()) {
            case MOVE_STUDENT_MESSAGE -> doMoveStudent((MoveStudentMessage) message);
            case CHARACTER_INFO_REQUEST_MESSAGE, CHARACTER_MESSAGE, CHARACTER_COLOR_MESSAGE,
                    CHARACTER_DOUBLE_COLOR_MESSAGE, CHARACTER_DESTINATION_MESSAGE,
                    CHARACTER_COLOR_DESTINATION_MESSAGE -> characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to move a student to an island or to his hall.
     * @param message the message sent by the current player.
     */
    private void doMoveStudent(MoveStudentMessage message) {
        View view = clientsView.get(message.getNickname());
        CreatureColor color = message.getColor();
        int destination = message.getDestination();

        model.getCurrentPhase().setCreatureColorIndex(color.getIndex());
        model.getCurrentPhase().setStudentDestinationIndex(destination);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Switch on type messages that can be received in move mother nature phase:
     * move mother nature or play a character
     * @param message the message sent by the current player.
     */
    private void moveMotherNaturePhase(Message message) {
        switch (message.getMessageType()) {
            case MOVE_MOTHER_NATURE_MESSAGE -> doMoveMotherNature((MoveMotherNatureMessage) message);
            case CHARACTER_INFO_REQUEST_MESSAGE, CHARACTER_MESSAGE, CHARACTER_COLOR_MESSAGE,
                    CHARACTER_DOUBLE_COLOR_MESSAGE, CHARACTER_DESTINATION_MESSAGE,
                    CHARACTER_COLOR_DESTINATION_MESSAGE -> characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to move mother nature.
     * @param message the message sent by the current player.
     */
    private void doMoveMotherNature(MoveMotherNatureMessage message) {
        View view = clientsView.get(message.getNickname());
        int numberOfSteps = message.getNumberOfSteps();

        model.getCurrentPhase().setNumberOfSteps(numberOfSteps);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
        }
        checkEndGame();
    }

    /**
     * Switch on type messages that can be received in move pick cloud phase:
     * choose a cloud or play a character
     * @param message the message sent by the current player.
     */
    private void pickCloudPhase(Message message) {
        switch (message.getMessageType()) {
            case PICK_CLOUD_MESSAGE -> doPickCloud((PickCloudMessage) message);
            case CHARACTER_INFO_REQUEST_MESSAGE, CHARACTER_MESSAGE, CHARACTER_COLOR_MESSAGE,
                    CHARACTER_DOUBLE_COLOR_MESSAGE, CHARACTER_DESTINATION_MESSAGE,
                    CHARACTER_COLOR_DESTINATION_MESSAGE -> characterController.onMessageReceived(message);
        }
    }

    /**
     * Allows the current player to choose an island to pick.
     * @param message the message sent by the current player.
     */
    private void doPickCloud(PickCloudMessage message) {
        View view = clientsView.get(message.getNickname());
        int cloudID = message.getCloudID();

        model.getCurrentPhase().setCloudID(cloudID);

        try {
            model.getCurrentPhase().play();
        } catch (Exception e) {
            view.showErrorMessage(e.getMessage());
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

            for(Player player : model.getPlayers()) {
                View view = clientsView.get(player.getNickname());
                view.showWinMessage(winner.getNickname());
            }

            //end of the game todo
        }
    }

    /**
     * Receives an update message from the view.
     * @param message the update message
     */
    @Override
    public void update(Message message) {
        //todo
    }
}
