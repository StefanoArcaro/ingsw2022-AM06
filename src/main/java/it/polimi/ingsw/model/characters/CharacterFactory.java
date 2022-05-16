package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

/**
 * Declares the factory that will have the task of returning the appropriate object.
 */
public abstract class CharacterFactory {

    Game game;

    /**
     * Instantiates a character based on the specified character ID.
     * @param characterID the ID of the character to instantiate.
     * @return the instantiated character.
     */
    protected abstract Character createCharacter(int characterID);
}
