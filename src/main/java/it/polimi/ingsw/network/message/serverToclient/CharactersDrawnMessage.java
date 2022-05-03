package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.util.Constants;

import java.util.ArrayList;

/**
 * Message used by the server to display the available characters.
 */
public class CharactersDrawnMessage extends Answer {

    private final String characters;

    public CharactersDrawnMessage() {
        super(MessageType.CHARACTERS_DRAWN_MESSAGE);
        this.characters = Constants.getDrawnCharacters();

    }

    public String getCharacters() {
        return characters;
    }
}
