package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration of the possible players' colors.
 */
public enum PlayerColor {

    BLACK(0),
    WHITE(1),
    GRAY(2);

    private final int id;

    PlayerColor(int id) {
        this.id = id;
    }

}
