package it.polimi.ingsw.model;

public enum PlayerColor {
    BLACK(0),
    WHITE(1),
    GRAY(2);
    private final int id;

    /**
     * Default constructor
     * @param id
     */
    PlayerColor(int id) {
        this.id = id;
    }

    /**
     * @return id
     */
    public int getId() {
        return this.id;
    }
}
