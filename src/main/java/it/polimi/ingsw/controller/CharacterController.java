package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.phases.ActionPhase;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.network.message.serverToclient.CharacterInfoMessage;
import it.polimi.ingsw.network.message.serverToclient.ErrorMessage;
import it.polimi.ingsw.network.server.ClientHandler;

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
     * @param clients the map of client IDs to their client handlers.
     */
    public CharacterController(Game game, Map<Integer, ClientHandler> clients) {
        this.game = game;
        this.clients = clients;
    }

    /**
     * Determines the action to do based on the message received from the client.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    public void onMessageReceived(String input, Message msg) {
        switch (msg.getMessageType()) {
            case CHARACTER_INFO_REQUEST_MESSAGE -> infoAboutCharacter(input, msg);
            case CHARACTER_MESSAGE -> doCharacterEffect(input, msg);
            case CHARACTER_COLOR_MESSAGE -> doCharacterColorEffect(input, msg);
            case CHARACTER_DOUBLE_COLOR_MESSAGE -> doCharacterDoubleColorEffect(input, msg);
            case CHARACTER_DESTINATION_MESSAGE -> doCharacterDestinationEffect(input, msg);
            case CHARACTER_COLOR_DESTINATION_MESSAGE -> doCharacterColorDestinationEffect(input, msg);
        }
    }

    /**
     * Sends a message which contains the info about the character chosen.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    private void infoAboutCharacter(String input, Message msg) {
        Gson gson = new Gson();
        CharacterInfoRequestMessage message = gson.fromJson(input, CharacterInfoRequestMessage.class);

        ClientHandler clientHandler = clients.get(msg.getClientID());
        int characterID = message.getCharacterID();

        clientHandler.sendMessage(new CharacterInfoMessage(characterID));
    }

    /**
     * Plays the character chosen if this one doesn't need any parameters.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    private void doCharacterEffect(String input, Message msg) {
        Gson gson = new Gson();
        CharacterMessage message = gson.fromJson(input, CharacterMessage.class);

        int characterID = message.getCharacterID();

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs a color as parameter.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    private void doCharacterColorEffect(String input, Message msg) {
        Gson gson = new Gson();
        CharacterColorMessage message = gson.fromJson(input, CharacterColorMessage.class);

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
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs two colors as parameters.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    private void doCharacterDoubleColorEffect(String input, Message msg) {
        Gson gson = new Gson();
        CharacterDoubleColorMessage message = gson.fromJson(input, CharacterDoubleColorMessage.class);

        int characterID = message.getCharacterID();
        CreatureColor firstColor = message.getFirstColor();
        CreatureColor secondColor = message.getSecondColor();

        game.getCharacterByID(characterID).setFirstColor(firstColor);
        game.getCharacterByID(characterID).setSecondColor(secondColor);

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs the destination as parameter.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    private void doCharacterDestinationEffect(String input, Message msg) {
        Gson gson = new Gson();
        CharacterDestinationMessage message = gson.fromJson(input, CharacterDestinationMessage.class);

        int characterID = message.getCharacterID();
        int destination = message.getDestination();

        game.getCharacterByID(characterID).setIslandGroupIndex(destination - 1);

        /*
        if(characterID == CharacterID.CHARACTER_FIVE.getID()) {
            game.getCharacterByID(characterID).setIslandID(destination);
        }else if(characterID == CharacterID.CHARACTER_THREE.getID()) {
            game.getCharacterByID(characterID).setIslandGroupIndex(destination);
        }*/

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }

    /**
     * Plays the character chosen if this one only needs a color and a destination as parameters.
     * @param input JSON of the message.
     * @param msg the message received.
     */
    private void doCharacterColorDestinationEffect(String input, Message msg) {
        Gson gson = new Gson();
        CharacterColorDestinationMessage message = gson.fromJson(input, CharacterColorDestinationMessage.class);

        int characterID = message.getCharacterID();
        CreatureColor firstColor = message.getColor();
        int destination = message.getDestination();

        game.getCharacterByID(characterID).setFirstColor(firstColor);
        game.getCharacterByID(characterID).setIslandID(destination);

        try {
            ((ActionPhase) (game.getCurrentPhase())).playCharacter(characterID);
        } catch (Exception e) {
            ClientHandler clientHandler = clients.get(msg.getClientID());
            clientHandler.sendMessage(new ErrorMessage(e.getMessage()));
        }
    }
}
