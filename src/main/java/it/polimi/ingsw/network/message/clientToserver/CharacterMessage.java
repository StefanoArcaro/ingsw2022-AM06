package it.polimi.ingsw.network.message.clientToserver;

import it.polimi.ingsw.network.message.MessageType;

/**
 * Message used by the client to play a character which doesn't need any parameters.
 */
public class CharacterMessage extends Message {

    private int characterID;

    public CharacterMessage(String nickname, int characterID) {
        super(nickname, MessageType.CHARACTER_MESSAGE);
        this.characterID = characterID;
    }

    @Override
    public String toString() {
        return "CharacterMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                '}';
    }

    public int getCharacterID() {
        return characterID;
    }
}
