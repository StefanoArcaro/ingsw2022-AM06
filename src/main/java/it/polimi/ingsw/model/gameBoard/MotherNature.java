package it.polimi.ingsw.model.gameBoard;

public class MotherNature {

    private static MotherNature motherNature = null;
    private IslandGroup currentIslandGroup;

    /**
     * private constructor used in getMotherNature
     * @param islandGroup where mother nature is
     */
    private MotherNature(IslandGroup islandGroup) {
        this.currentIslandGroup = islandGroup;
    }

    public static MotherNature getMotherNature() {
        if(motherNature == null) {
            motherNature = new MotherNature(null);
        }
        return motherNature;
    }

    /**
     * changes the current island group MotherNature refers to
     * @param islandGroup where to move mother nature
     */
   public void setCurrentIslandGroup(IslandGroup islandGroup){
        this.currentIslandGroup=islandGroup;
   }

    public IslandGroup getCurrentIslandGroup() {
        return currentIslandGroup;
    }
}
