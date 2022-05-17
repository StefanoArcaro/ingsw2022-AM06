package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration of the possible players' colors.
 */
public enum PlayerColor {

    BLACK (1),
    WHITE (2),
    GRAY (3);

    private final int index;

    PlayerColor(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * @return the string of the color.
     */
    public String getColorName() {
        return switch (index) {
            case 1 -> "Black";
            case 2 -> "White";
            case 3 -> "Grey";
            default -> "";
        };
    }
}
