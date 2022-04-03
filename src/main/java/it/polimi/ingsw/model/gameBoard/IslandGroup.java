package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterInfluenceModifier;

import java.util.ArrayList;

public class IslandGroup {
    private ArrayList<Island> islands;
    private int numberOfBanCardPresent;
    private PlayerColor conquerorColor;

    /**
     * Default constructor.
     */
    public IslandGroup() {
        this.islands = new ArrayList<Island>();
        this.numberOfBanCardPresent = 0;
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
     * @return the number of ban card present
     */
    public int getNumberOfBanCardPresent() {
        return numberOfBanCardPresent;
    }

    /**
     * Due to character effect: a ban card is added to the island group
     */
    public void addBanCard(){
        this.numberOfBanCardPresent ++;
    }

    public boolean removeBanCard() {
        if(numberOfBanCardPresent > 0) {
            numberOfBanCardPresent --;
            return true;
        }
        return false;
    }

    /**
     * @return the islands that make up the island group
     */
    public ArrayList<Island> getIslands() {
        return new ArrayList<Island>(islands);
    }

    /**
     * @return the number of islands which make up the group
     */
    public int getNumberOfIsland() {
        return getIslands().size();
    }

    /**
     * Add an island in the island group
     * @param island to add to the island group
     */
    public boolean addIsland(Island island) throws NullPointerException {
        int IDIslandToAdd;
        PlayerColor playerColorIslandToAdd;

        try {
            IDIslandToAdd = island.getIslandID();
            playerColorIslandToAdd = island.getTower();
        } catch(NullPointerException e) {
            return false;
        }

        if(this.getNumberOfIsland() == 0) {
            islands.add(island);
            this.conquerorColor = playerColorIslandToAdd;
            return true;
        }

        if(playerColorIslandToAdd != this.conquerorColor) {
            return false;
        }

        for(int i = 0; i < this.getNumberOfIsland(); i++) {
            if((this.getIslands().get(i).getIslandID() % 12) + 1 == IDIslandToAdd) {
                if(this.getIslands().get(i).getIslandID() < IDIslandToAdd) {
                    islands.add(i + 1, island);
                } else {
                    islands.add(i, island);
                }
                return true;
            } else {
                if (this.getIslands().get(i).getIslandID() - 1 == IDIslandToAdd % 12) {
                    if (this.getIslands().get(i).getIslandID() < IDIslandToAdd) {
                        islands.add(i + this.getNumberOfIsland(), island);
                    } else {
                        islands.add(i, island);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Merge island groups together
     * @param islandGroup to join this.islandGroup
     */
    public boolean connectIslandGroup(IslandGroup islandGroup) throws NullPointerException {
        ArrayList<Island> islandsInGroupToAdd;
        int lenGroupToAdd;
        PlayerColor playerColorIslandGroupToAdd;
        int islandAdded = 0;

        try {
            islandsInGroupToAdd = islandGroup.getIslands();
            lenGroupToAdd = islandGroup.getNumberOfIsland();
            playerColorIslandGroupToAdd = islandGroup.getConquerorColor();
        } catch(NullPointerException e) {
            return false;
        }

        if(this.conquerorColor != playerColorIslandGroupToAdd) {
            return false;
        }

        for(int j = 0; j < lenGroupToAdd; j++) {
            for (int i = 0; i < this.getNumberOfIsland(); i++) {
                if(this.getIslands().get(i).getIslandID() + 1 == islandsInGroupToAdd.get(j).getIslandID()) {
                    this.addIsland(islandsInGroupToAdd.get(j));
                    islandAdded ++;
                    break;
                }
            }
        }

        if(islandAdded == lenGroupToAdd) { return true; }

        for(int j = lenGroupToAdd - 1; j >= 0; j--) {
            for(int i = 0; i < this.getNumberOfIsland(); i++) {
                if(this.getIslands().get(i).getIslandID() - 1 == (islandsInGroupToAdd.get(j).getIslandID() % 12)) {
                    this.addIsland(islandsInGroupToAdd.get(j));
                    islandAdded++;
                    break;
                }
            }
        }

        if(islandAdded == lenGroupToAdd) { return true; }

        return false;

        /* IDE says these two lines can be collapsed into 'return false'
        if(islandAdded >= 0 ) { return false; }
        return false;
        * */
    }

    /**
     * Calculates the influence of the island group as the
     * sum of the influences of the islands that are part of it
     * @param player to calculate the influence for
     * @return the influence for the player on the island group
     */
    public int calculateInfluence(Player player) {
        int influence = 0;

        for(Island island : islands) {
            influence = influence + island.calculateInfluence(player);
        }

        return influence;
    }

    /**
     * Calculates the influence of the island group as the
     * sum of the influences of the islands that are part of it
     * Overloading of calculateInfluence with character
     * @param player to calculate the influence for
     * @param activatedCharacter active character which modifies influence
     * @return the influence for the player on the island group
     */
    public int calculateInfluence(Player player, Character activatedCharacter) {
        int influence = 0;
        int extraPoints = 0;

        for(Island island : islands) {
            influence = influence + island.calculateInfluence(player, activatedCharacter);
        }

        Player currentPlayer = Game.getGame().getCurrentPlayer();

        if(currentPlayer.equals(player) && activatedCharacter!=null) {
            extraPoints = activatedCharacter.getExtraPoints();
        }

        return influence + extraPoints;
    }
}




