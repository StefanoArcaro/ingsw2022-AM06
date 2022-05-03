package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to request information about a character's effect.
 */
public class CharacterInfoRequestMessage extends Message {

    private final int characterID;

    public CharacterInfoRequestMessage(int characterID) {
        super(MessageType.CHARACTER_INFO_REQUEST_MESSAGE);
        this.characterID = characterID;
    }

    public int getCharacterID() {
        return characterID;
    }
}
