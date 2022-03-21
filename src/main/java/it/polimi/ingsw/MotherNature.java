package it.polimi.ingsw;

public class MotherNature {
    private static MotherNature motherNature = null;
    private IslandGroup islandGroup;

    private MotherNature(IslandGroup islandGroup) {
        this.islandGroup = islandGroup;
    }

    public static synchronized MotherNature getMotherNature(){
        if(motherNature == null){
            motherNature = new MotherNature();
        }
        return motherNature;
    }

}
