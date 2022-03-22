package it.polimi.ingsw.characters;

/**
 * Abstract class used to add custom effects to a BaseCharacter.
 */

public abstract class CharacterDecorator extends Character {

    /**
     * Reference to the Character object.
     */
    protected Character character;

    /**
     * Default constructor.
     */
    public CharacterDecorator() {
        super();
    }

    @Override
    public void initialPreparation(){
        //do nothing
    }

    @Override
    public abstract void effect();
}
