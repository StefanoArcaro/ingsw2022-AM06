package it.polimi.ingsw;

/**
 * Enumeration for the possible game modes
 */
public enum GameMode {
    EASY (0),
    EXPERT (1);

    private final int mode;

    GameMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }
}
