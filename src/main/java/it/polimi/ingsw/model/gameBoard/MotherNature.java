package it.polimi.ingsw.model.gameBoard;

public class MotherNature {

    private static MotherNature motherNature = null;
    private IslandGroup islandGroup;

    /**
     * Private constructor
     */
    private MotherNature() {}

    /**
     * Mother Nature is a Singleton
     * @return singleton instance of Mother Nature
     */
    public static MotherNature getMotherNature() {
        if(motherNature == null) {
            motherNature = new MotherNature();
        }
        return motherNature;
    }

    /**
     * @return the island group Mother Nature is currently on
     */
    public IslandGroup getIslandGroup() {
        return islandGroup;
    }

    /**
     * Sets the island group Mother Nature ends up at
     * @param islandGroup to be set
     */
    public void setIslandGroup(IslandGroup islandGroup) {
        this.islandGroup = islandGroup;
    }
}
