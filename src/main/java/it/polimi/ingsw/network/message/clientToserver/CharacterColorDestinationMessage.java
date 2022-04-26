package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character that needs student's color and destination as a parameter.
 */
public class CharacterColorDestinationMessage extends Message {

    private int characterID;
    private CreatureColor color;
    private int destination;

    public CharacterColorDestinationMessage(String nickname, int characterID, CreatureColor color, int destination) {
        super(nickname, MessageType.CHARACTER_COLOR_DESTINATION_MESSAGE);
        this.characterID = characterID;
        this.color = color;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "CharacterColorDestinationMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                ", color=" + color +
                ", destination=" + destination +
                '}';
    }

    public int getCharacterID() {
        return characterID;
    }

    public CreatureColor getColor() {
        return color;
    }

    public int getDestination() {
        return destination;
    }
}
