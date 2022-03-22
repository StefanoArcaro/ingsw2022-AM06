package it.polimi.ingsw.characters;

/**
 * Abstract class used to implement the Decorator Pattern for character cards.
 */
public abstract class Character {
    protected int characterID;
    protected String name;
    protected int cost;
    protected boolean used;

    /**
     * Default constructor.
     */
    public Character (){
        used = false;
    }

    /**
     * Modify the game setup in order to use this character.
     */
    public abstract void initialPreparation();

    /**
     * Implements the character effect.
     */
    public abstract void effect ();

    /**
     * Return the ID of the character.
     * @return int which represents the character ID
     */
    public int getCharacterID() {
        return characterID;
    }

    /**
     * Return the character name
     * @return String which represents the name of the character
     */
    public String getName() {
        return name;
    }

    /**
     * Return a boolean that specifies whether the character has been used during the game
     * @return boolean which is true only if the character has been used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Return the cost of the character, increased by one in case of use
     * @return int which represents the cost for using the character
     */
    public int getCost() {
        if (isUsed()) {return cost+1;}
        return cost;
    }
}
