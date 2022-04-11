package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration to keep track of the current state of the game
 */
public enum GameState {

    LOBBY_PHASE(0),
    PREPARE_PHASE(1),
    PLANNING_PHASE(2),
    MOVE_STUDENT_PHASE(3),
    MOVE_MOTHER_NATURE_PHASE(4),
    PICK_CLOUD_PHASE(5),
    ENDED_TOWER(6),
    ENDED_ISLAND(7),
    ENDED_STUDENTS(8),
    ENDED_ASSISTANTS(9);

    private final int code;

    GameState(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
