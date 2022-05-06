package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character that needs a destination as a parameter.
 */
public class CharacterDestinationMessage extends Message {

    private final int characterID;
    private final int destination;

    public CharacterDestinationMessage(int characterID, int destination) {
        super(MessageType.CHARACTER_DESTINATION_MESSAGE);
        this.characterID = characterID;
        this.destination = destination;
    }

    public int getCharacterID() {
        return characterID;
    }

    public int getDestination() {
        return destination;
    }
}
