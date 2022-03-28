package it.polimi.ingsw.model.characters;

/**
 * Declares the factory that will have the task of returning the appropriate object
 */
public abstract class CharacterFactory {

    protected abstract Character createCharacter(int characterID);

}
