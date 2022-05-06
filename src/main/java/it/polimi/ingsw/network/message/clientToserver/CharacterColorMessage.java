package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character that needs a student's color as a parameter.
 */
public class CharacterColorMessage extends Message {

    private final int characterID;
    private final CreatureColor color;

    public CharacterColorMessage(int characterID, CreatureColor color) {
        super(MessageType.CHARACTER_COLOR_MESSAGE);
        this.characterID = characterID;
        this.color = color;
    }

    public int getCharacterID() {
        return characterID;
    }

    public CreatureColor getColor() {
        return color;
    }
}
