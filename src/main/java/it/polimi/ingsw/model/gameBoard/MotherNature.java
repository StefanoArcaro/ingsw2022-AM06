package it.polimi.ingsw.model.gameBoard;

public class MotherNature {

    private static MotherNature motherNature = null;
    private IslandGroup islandGroup;

    private MotherNature(IslandGroup islandGroup) {
        this.islandGroup = islandGroup;
    }

    public static MotherNature getMotherNature() {
        if(motherNature == null) {
            motherNature = new MotherNature(null);
        }
        return motherNature;
    }

}
