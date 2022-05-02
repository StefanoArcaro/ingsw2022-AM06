package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character that needs a destination as a parameter.
 */
public class CharacterDestinationMessage extends Message {

    private int characterID;
    private int destination;

    public CharacterDestinationMessage(String nickname, int characterID, int destination) {
        super(nickname, MessageType.CHARACTER_DESTINATION_MESSAGE);
        this.characterID = characterID;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "CharacterDestinationMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                ", destination=" + destination +
                '}';
    }

    public int getCharacterID() {
        return characterID;
    }

    public int getDestination() {
        return destination;
    }
}
