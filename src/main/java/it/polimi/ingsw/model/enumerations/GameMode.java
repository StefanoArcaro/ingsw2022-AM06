package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration for the possible game modes.
 */
public enum GameMode {

    EASY (0),
    EXPERT (1);

    private final int mode;

    GameMode(int mode) {
        this.mode = mode;
    }

}
