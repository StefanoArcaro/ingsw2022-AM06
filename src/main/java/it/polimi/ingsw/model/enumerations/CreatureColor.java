package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration of the valid colors.
 */
public enum CreatureColor {

    GREEN(0),
    RED(1),
    YELLOW(2),
    PINK(3),
    BLUE(4);

    private final int index;

    CreatureColor(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static CreatureColor getColorByIndex(int index){
        return switch (index) {
            case 0 -> GREEN;
            case 1 -> RED;
            case 2 -> YELLOW;
            case 3 -> PINK;
            case 4 -> BLUE;
            default -> null;
        };
    }

    /**
     * @return the string of the color.
     */
    public String getColorName() {
        return switch (index) {
            case 0 -> "Green";
            case 1 -> "Red";
            case 2 -> "Yellow";
            case 3 -> "Pink";
            case 4 -> "Blue";
            default -> "";
        };
    }
}
