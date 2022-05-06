package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character which doesn't need any parameters.
 */
public class CharacterMessage extends Message {

    private final int characterID;

    public CharacterMessage(int characterID) {
        super(MessageType.CHARACTER_MESSAGE);
        this.characterID = characterID;
    }

    public int getCharacterID() {
        return characterID;
    }
}
