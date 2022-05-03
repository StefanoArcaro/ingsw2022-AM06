package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.util.Constants;

/**
 * Message sent to the client to give information about a character's effect.
 */
public class CharacterInfoMessage extends Answer {

    private final int characterID;
    private final String description;

    public CharacterInfoMessage(Character character) {
        super(MessageType.CHARACTER_INFO_MESSAGE);
        this.characterID = character.getCharacterID();
        this.description = Constants.getCharacterInformation(character.getCharacterID());
    }

    public int getCharacterID() {
        return characterID;
    }

    public String getDescription() {
        return description;
    }
}
