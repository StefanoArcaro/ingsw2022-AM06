package it.polimi.ingsw;

import java.util.ArrayList;

public class IslandGroup {
    private ArrayList<Island> islands;
    private boolean banCardPresent;
    private PlayerColor conquerorColor;

    /**
     * Default constructor.
     */
    public IslandGroup() {
        this.islands = new ArrayList<Island>();
        this.banCardPresent = false;
        this.conquerorColor = null;
    }

    /**
     * @return the color of the player who conquered the island
     */
    public PlayerColor getConquerorColor() {
        return conquerorColor;
    }

    /**
     * Set the color of the player who conquered the island
     * @param conquerorColor color of the player who conquered the island
     */
    public void setConquerorColor(PlayerColor conquerorColor) {
        this.conquerorColor = conquerorColor;
    }

    /**
     * @return the islands that make up the island group
     */
    public ArrayList<Island> getIslands() {
        // TO DO
        return null;
    }

    /**
     * @return the number of islands which make up the group
     */
    public int getNumberOfIsland() {
        // TO DO
        return 0;
    }

    /**
     * Add an island in the isalnd group
     * @param island to add to the island group
     */
    public void addIsland (Island island){

    }

    /**
     * @return wether the ban card is present
     */
    public boolean isBanCardPresent() {
        // TO DO
        return false;
    }

    /**
     * Due to character effect: a ban card is added to the island group
     */
    public void addBanCard(){

    }

    /**
     * Merge island groups together
     * @param islandGroup to join this.island group
     */
    public void connectIslandGroup(IslandGroup islandGroup) {

    }

    // IL METODO CHECK VA MESSO NEL GAME, NON QUI (?)
    /**
     * Chech the number of island group: if 3 the game ends
     */
    private void checkNumberOfIslandGroups(){

    }

    /**
     * Calculates the influence of the island group as the
     * sum of the influences of the islands that are part of it
     * @param nickname of the player i'm calculating the influence for
     * @return the influence for the player (represented by its nickname) on the isalnd group
     */
    public int calculateInfluence(String nickname) {
        // TO DO
        return 0;
    }
}




