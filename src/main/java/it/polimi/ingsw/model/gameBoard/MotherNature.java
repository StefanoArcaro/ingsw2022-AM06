package it.polimi.ingsw.model.gameBoard;

public class MotherNature {

    private static MotherNature motherNature = null;
    private IslandGroup currentIslandGroup;

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

    /**
     * Sets the island group Mother Nature ends up at
     * @param islandGroup to be set
     */

    /**
     * changes the current island group MotherNature refers to
     * @param islandGroup
     */
   public void setCurrentIslandGroup(IslandGroup islandGroup){
        this.currentIslandGroup=islandGroup;
   }

    public IslandGroup getCurrentIslandGroup() {
        return currentIslandGroup;
    }
}
