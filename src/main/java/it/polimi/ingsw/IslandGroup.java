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
        ArrayList<Island> result = new ArrayList<Island>(islands);
        return result;
    }

    /**
     * @return the number of islands which make up the group
     */
    public int getNumberOfIsland() {
        return getIslands().size();
    }

    /**
     * Add an island in the isalnd group
     * @param island to add to the island group
     */
    public boolean addIsland (Island island){
        int IDIslandToAdd;


        if(island == null)
            return false;

        IDIslandToAdd = island.getIslandID();

        if(this.getNumberOfIsland() == 0){
            islands.add(island);
            return true;
        }

        for (int i=0; i<this.getNumberOfIsland(); i++){
            if((this.getIslands().get(i).getIslandID()%12) + 1 == IDIslandToAdd){
                if(this.getIslands().get(i).getIslandID()<IDIslandToAdd) {
                    islands.add(i + 1, island);
                }else{
                    islands.add(i, island);
                }
                return true;
            }else
                if(this.getIslands().get(i).getIslandID() - 1 == IDIslandToAdd%12){
                    if(this.getIslands().get(i).getIslandID()<IDIslandToAdd) {
                        islands.add(i + this.getNumberOfIsland(), island);
                    }else{
                        islands.add(i, island);
                    }


                    return true;
                }
        }

        return false;
    }

    /**
     * @return wether the ban card is present
     */
    public boolean isBanCardPresent() {
        return banCardPresent;
    }

    /**
     * Due to character effect: a ban card is added to the island group
     */
    public boolean addBanCard(){
        if(!banCardPresent){
            banCardPresent = true;
            return true;
        }
        return false;
    }

    /**
     * Merge island groups together
     * @param islandGroup to join this.island group
     */
    public boolean connectIslandGroup(IslandGroup islandGroup) {
        ArrayList<Island> islandsInGroupToAdd = islandGroup.getIslands();
        int lenGroupToAdd = islandGroup.getNumberOfIsland();
        int islandAdded = 0;

        for (int j=0; j<lenGroupToAdd; j++) {
            for (int i=0; i<this.getNumberOfIsland(); i++) {
                if(this.getIslands().get(i).getIslandID() + 1 == islandsInGroupToAdd.get(j).getIslandID()){
                    this.addIsland(islandsInGroupToAdd.get(j));
                    islandAdded ++;
                    break;
                }
            }
        }

        if (islandAdded == lenGroupToAdd) {return true;}

        for (int j=lenGroupToAdd-1; j>=0; j--) {
            for (int i=0; i<this.getNumberOfIsland(); i++) {
                if(this.getIslands().get(i).getIslandID() - 1 == (islandsInGroupToAdd.get(j).getIslandID()%12)){
                    this.addIsland(islandsInGroupToAdd.get(j));
                    islandAdded ++;
                    break;
                }
            }
        }

        if (islandAdded == lenGroupToAdd) {return true;}

        if(islandAdded >= 0 ) {return false;}

        return false;
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




