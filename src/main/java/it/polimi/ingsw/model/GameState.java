package it.polimi.ingsw.model;

/**
 * Enumeration to keep track of the current state of the game
 */
public enum GameState {
    ONGOING (0),
    ENDED_TOWER (1),
    ENDED_ISLAND (2),
    ENDED_STUDENTS (3),
    ENDED_ASSISTANTS (4);

    private final int code;

    GameState(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
