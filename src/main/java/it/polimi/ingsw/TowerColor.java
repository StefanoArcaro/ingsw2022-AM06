package it.polimi.ingsw;

public enum TowerColor {
    BLACK(0),WHITE(1),GRAY(2);
    private final int index;

    TowerColor(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
