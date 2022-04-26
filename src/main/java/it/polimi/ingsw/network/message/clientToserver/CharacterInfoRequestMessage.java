package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to request information about a character's effect.
 */
public class CharacterInfoRequestMessage extends Message {

    private int characterID;

    public CharacterInfoRequestMessage(String nickname, int characterID) {
        super(nickname, MessageType.CHARACTER_INFO_REQUEST_MESSAGE);
        this.characterID = characterID;
    }

    @Override
    public String toString() {
        return "CharacterInfoRequestMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                '}';
    }

    public int getCharacterID() {
        return characterID;
    }
}
