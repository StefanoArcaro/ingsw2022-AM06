package it.polimi.ingsw.model;

public enum PlayerColor {

    BLACK(0),
    WHITE(1),
    GRAY(2);

    private final int id;

    PlayerColor(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
