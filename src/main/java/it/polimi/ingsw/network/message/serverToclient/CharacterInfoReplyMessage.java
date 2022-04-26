package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.Message;

/**
 * Message sent to the client to give information about a character's effect.
 */
public class CharacterInfoReplyMessage extends Answer {

    private int characterID;
    private String description;

    public CharacterInfoReplyMessage(int characterID, String description) {
        super(MessageType.CHARACTER_INFO_REPLY_MESSAGE);
        this.characterID = characterID;
        this.description = description;
    }

    @Override
    public String toString() {
        return "CharacterInfoReplyMessage{" +
                "nickname=" + getNickname() +
                ", characterID=" + characterID +
                ", description='" + description + '\'' +
                '}';
    }

    public int getCharacterID() {
        return characterID;
    }

    public String getDescription() {
        return description;
    }
}
