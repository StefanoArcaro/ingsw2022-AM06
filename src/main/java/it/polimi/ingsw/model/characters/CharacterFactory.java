package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

/**
 * Declares the factory that will have the task of returning the appropriate object
 */
public abstract class CharacterFactory {

    Game game;

    protected abstract Character createCharacter(int characterID);

}
