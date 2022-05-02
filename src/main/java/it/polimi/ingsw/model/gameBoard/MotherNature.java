package it.polimi.ingsw.model.gameBoard;

public class MotherNature {

    private IslandGroup currentIslandGroup;

    /**
     * Default constructor.
     */
    public MotherNature() {

    }

    /**
     * @return the island group Mother Nature is currently on.
     */
    public IslandGroup getCurrentIslandGroup() {
        return currentIslandGroup;
    }

    /**
     * Sets the island group Mother Nature ends up at.
     * @param islandGroup to be set.
     */
    public void setCurrentIslandGroup(IslandGroup islandGroup) {
        this.currentIslandGroup = islandGroup;
    }
}
