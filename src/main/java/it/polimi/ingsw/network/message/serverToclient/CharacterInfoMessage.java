package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.util.Constants;

/**
 * Message sent to the client to give information about a character's effect.
 */
public class CharacterInfoMessage extends Answer {

    private final String description;

    public CharacterInfoMessage(int characterID) {
        super(MessageType.CHARACTER_INFO_MESSAGE);
        this.description = Constants.getCharacterInformation(characterID);
    }

    @Override
    public String getMessage() {
        return description;
    }

    public String getDescription() {
        return description;
    }
}
