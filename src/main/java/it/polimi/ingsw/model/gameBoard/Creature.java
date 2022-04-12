package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.enumerations.CreatureColor;

public abstract class Creature {

    private final CreatureColor color;

    /**
     * Default constructor.
     * @param color of the creature.
     */
    public Creature(CreatureColor color) {
        this.color = color;
    }

    /**
     * @return color of the creature.
     */
    public CreatureColor getColor() {
        return color;
    }
}
