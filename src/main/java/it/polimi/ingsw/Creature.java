package it.polimi.ingsw;

public class Creature {
    private CreatureColor color;

    /**
     * Default constructor
     * @param color of the creauture
     */
    public Creature(CreatureColor color) {
        this.color = color;
    }

    /**
     * @return color of the creauture
     */
    public CreatureColor getColor() {
        return color;
    }

}
