package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the available characters.
 */
public class CharactersDrawnMessage extends Answer {

    private final ArrayList<Character> characters;

    public CharactersDrawnMessage(ArrayList<Character> characters) {
        super(MessageType.CHARACTERS_DRAWN_MESSAGE);
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "CharactersDrawnMessage{" +
                "nickname=" + getNickname() +
                ", characters=" + characters +
                '}';
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }
}
