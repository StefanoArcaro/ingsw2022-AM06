package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character that needs two students color as a parameter.
 */
public class CharacterDoubleColorMessage extends Message {

    private int characterID;
    private CreatureColor firstColor;
    private CreatureColor secondColor;

    public CharacterDoubleColorMessage(String nickname, int characterID,
                                       CreatureColor firstColor, CreatureColor secondColor) {
        super(nickname, MessageType.CHARACTER_DOUBLE_COLOR_MESSAGE);
        this.characterID = characterID;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    @Override
    public String toString() {
        return "CharacterDoubleColorMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                ", firstColor=" + firstColor +
                ", secondColor=" + secondColor +
                '}';
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
