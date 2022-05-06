package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character that needs two students color as a parameter.
 */
public class CharacterDoubleColorMessage extends Message {

    private final int characterID;
    private final CreatureColor firstColor;
    private final CreatureColor secondColor;

    public CharacterDoubleColorMessage(int characterID, CreatureColor firstColor, CreatureColor secondColor) {
        super(MessageType.CHARACTER_DOUBLE_COLOR_MESSAGE);
        this.characterID = characterID;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public int getCharacterID() {
        return characterID;
    }

    public CreatureColor getFirstColor() {
        return firstColor;
    }

    public CreatureColor getSecondColor() {
        return secondColor;
    }
}
