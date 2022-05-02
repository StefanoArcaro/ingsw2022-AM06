package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message sent to the client to give information about a character's effect.
 */
public class CharacterInfoMessage extends Answer {

    private int characterID;
    private String description;

    public CharacterInfoMessage(Character character) {
        super(MessageType.CHARACTER_INFO_MESSAGE);
        this.characterID = character.getCharacterID();
        //this.description = character.getDescription(); todo constants
    }

    @Override
    public String toString() {
        return "CharacterInfoMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                ", description='" + description + '\'' +
                '}';
    }

    public int getCharacterID() {
        return characterID;
    }

    public String getDescription() {
        return description;
    }
}
