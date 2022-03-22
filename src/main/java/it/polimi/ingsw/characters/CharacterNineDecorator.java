package it.polimi.ingsw.characters;

public class CharacterNineDecorator extends CharacterDecorator {

    protected final int characterID = 9;
    protected final String name = "Character9";
    protected final int cost = 3;

    private CreatureColor color;

    /**
     * Set a color for using the effect.
     * @param color chosen
     */
    public void setColor(CreatureColor color) {
        this.color = color;
    }

    /**
     * Return the color chosen by the user.
     * @return CreatureColor a student color
     */
    public CreatureColor getColor() {
        return color;
    }

    @Override
    public void effect() {

    }
}
