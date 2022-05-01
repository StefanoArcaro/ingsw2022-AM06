package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.ErrorMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.View;

import java.util.Map;

/**
 * Controller class used to manage the messages about the use of characters.
 */
public class CharacterController {

    private final Game game;
    private final Map<Integer, ClientHandler> clients;

    /**
     * Default constructor.
     * @param game the game.
     * @param clients the map of clients to their views. //todo
     */
    public CharacterController(Game game, Map<Integer, ClientHandler> clients) {
        this.game = game;
        this.clients = clients;
    }

    /**
     * Determines the action to do based on the message received from the client.
     * @param message the message received.
     */
    public void onMessageReceived(Message message) {
        if(game.getGameMode() == GameMode.EASY) {
            ClientHandler clientHandler = clients.get(message.getClientID());
            clientHandler.sendMessage(new ErrorMessage("You have chosen the easy mode, there are no characters."));
            return;
        }

        switch (message.getMessageType()) {
            case CHARACTER_INFO_REQUEST_MESSAGE -> infoAboutCharacter((CharacterInfoRequestMessage) message);
            case CHARACTER_MESSAGE -> doCharacterEffect((CharacterMessage) message);
            case CHARACTER_COLOR_MESSAGE -> doCharacterEffect((CharacterColorMessage) message);
            case CHARACTER_DOUBLE_COLOR_MESSAGE -> doCharacterEffect((CharacterDoubleColorMessage) message);
            case CHARACTER_DESTINATION_MESSAGE -> doCharacterEffect((CharacterDestinationMessage) message);
            case CHARACTER_COLOR_DESTINATION_MESSAGE -> doCharacterEffect((CharacterColorDestinationMessage) message);
        }
    }

    /**
     * Sends a message which contains the info about the character chosen.
     * @param message the message received.
     */
    private void infoAboutCharacter(CharacterInfoRequestMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int characterID = message.getCharacterID();
        String description = null; //= classe da cui prendere descrizione in base all'id


        //si crea answer
        //si prende game manager corrispondente
        //gameManager.signleSend(answer, client a cui mandare)

        //view.showCharacterInfoReply(characterID, description); non così
    }

    /**
     * Plays the character chosen if this one doesn't need any parameters.
     * @param message the message received.
     */
    private void doCharacterEffect(CharacterMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int characterID = message.getCharacterID();

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs a color as parameter.
     * @param message the message received.
     */
    private void doCharacterEffect(CharacterColorMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int characterID = message.getCharacterID();
        CreatureColor color = message.getColor();

        if(characterID == CharacterID.CHARACTER_NINE.getID()) {
            game.getCharacterByID(characterID).setColorNoPoints(color);
        } else {
            game.getCharacterByID(characterID).setFirstColor(color);
        }

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs two colors as parameters.
     * @param message the message received.
     */
    private void doCharacterEffect(CharacterDoubleColorMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int characterID = message.getCharacterID();
        CreatureColor firstColor = message.getFirstColor();
        CreatureColor secondColor = message.getSecondColor();

        game.getCharacterByID(characterID).setFirstColor(firstColor);
        game.getCharacterByID(characterID).setSecondColor(secondColor);

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs the destination as parameter.
     * @param message the message received.
     */
    private void doCharacterEffect(CharacterDestinationMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int characterID = message.getCharacterID();
        int destination = message.getDestination();

        if(characterID == CharacterID.CHARACTER_FIVE.getID()) {
            game.getCharacterByID(characterID).setIslandID(destination);
        }else if(characterID == CharacterID.CHARACTER_THREE.getID()) {
            game.getCharacterByID(characterID).setIslandGroupIndex(destination);
        }

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs a color and a destination as parameters.
     * @param message the message received.
     */
    private void doCharacterEffect(CharacterColorDestinationMessage message) {
        ClientHandler clientHandler = clients.get(message.getClientID());
        int characterID = message.getCharacterID();
        CreatureColor firstColor = message.getColor();
        int destination = message.getDestination();

        game.getCharacterByID(characterID).setFirstColor(firstColor);
        game.getCharacterByID(characterID).setIslandID(destination);

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }
}
