package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.characters.Character;

import java.util.ArrayList;
import java.util.Comparator;

public class IslandGroup {

    private ArrayList<Island> islands;
    private int numberOfBanCardPresent;
    private PlayerColor conquerorColor;

    /**
     * Default constructor.
     */
    public IslandGroup() {
        this.islands = new ArrayList<>();
        this.numberOfBanCardPresent = 0;
        this.conquerorColor = null;
    }

    /**
     * @return the color of the player who conquered the island.
     */
    public PlayerColor getConquerorColor() {
        return conquerorColor;
    }

    /**
     * Set the color of the player who conquered the island.
     * @param conquerorColor color of the player who conquered the island.
     */
    public void setConquerorColor(PlayerColor conquerorColor) {
        this.conquerorColor = conquerorColor;
    }

    /**
     * @return the number of ban card present.
     */
    public int getNumberOfBanCardPresent() {
        return numberOfBanCardPresent;
    }

    /**
     * Due to character effect: a ban card is added to the island group.
     */
    public void addBanCard(){
        this.numberOfBanCardPresent ++;
    }

    /**
     * Removes one of the ban cards that are on the island group (if there are any).
     * @return whether the removal is successful.
     */
    public boolean removeBanCard() {
        if(numberOfBanCardPresent > 0) {
            numberOfBanCardPresent -= 1;
            return true;
        }
        return false;
    }

    /**
     * @return the islands that make up the island group.
     */
    public ArrayList<Island> getIslands() {
        return new ArrayList<>(islands);
    }

    /**
     * Sets the islands to the given ones.
     * @param islands the group of islands to set.
     */
    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }


    /**
     * @return the number of islands that belong to the island group.
     */
    public int getNumberOfIslands() {
        return islands.size();
    }

    /**
     * Add an island to the island group.
     * @param island to add to the island group.
     */
    public boolean addIsland(Island island) {
        int IDIslandToAdd;
        PlayerColor playerColorIslandToAdd;

        try {
            IDIslandToAdd = island.getIslandID();
            playerColorIslandToAdd = island.getTower();
        } catch (NullPointerException e) {
            return false;
        }

        if(this.getNumberOfIslands() == 0) {
            islands.add(island);
            this.conquerorColor = playerColorIslandToAdd;
            return true;
        }

        if(playerColorIslandToAdd != this.conquerorColor) {
            return false;
        }

        /*for(int i = 0; i < this.getNumberOfIslands(); i++) {
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
                        islands.add(i + this.getNumberOfIslands(), island);
                    } else {
                        islands.add(i, island);
                    }
                    return true;
                }
            }
        }*/

        islands.add(island);
        ArrayList<Island> toSort = islands;
        toSort.sort(Comparator.comparingInt(Island::getIslandID));
        this.setIslands(toSort);


        return true;
    }

    /**
     * Merge island groups together.
     * @param islandGroup to join this.islandGroup.
     * @return the updated island group.
     */
    public IslandGroup connectIslandGroup(IslandGroup islandGroup) {
        ArrayList<Island> islandsInGroupToAdd;
        int lenGroupToAdd;
        PlayerColor playerColorIslandGroupToAdd;

        islandsInGroupToAdd = islandGroup.getIslands();
        lenGroupToAdd = islandGroup.getNumberOfIslands();
        playerColorIslandGroupToAdd = islandGroup.getConquerorColor();

        if(this.conquerorColor != playerColorIslandGroupToAdd) {
            return null;
        }

        for(int i = 0; i < lenGroupToAdd; i++) {
            int islandIdToAdd = islandsInGroupToAdd.get(i).getIslandID();

            for(int j = 0; j < islands.size(); j++) {
                int currentId = islands.get(j).getIslandID();

                boolean unionCondition = ((islandIdToAdd % 12) + 1 == currentId) ||
                        ((islandIdToAdd - 1) == currentId % 12);

                if(unionCondition) {
                    islands.addAll(islandsInGroupToAdd);
                    islands.sort(Comparator.comparingInt(Island::getIslandID));
                    return this;
                }
            }
        }

        return null;
    }

    /**
     * Calculates the influence of the island group as the
     * sum of the influences of the islands that are part of it.
     * Overloading of calculateInfluence with character.
     * @param game reference to the game.
     * @param player to calculate the influence for.
     * @param activatedCharacter active character which modifies influence.
     * @return the influence for the player on the island group.
     */
    public int calculateInfluence(Game game, Player player, Character activatedCharacter) {
        int influence = 0;
        int extraPoints = 0;

        for(Island island : islands) {
            influence = influence + island.calculateInfluence(player, activatedCharacter);
        }

        Player currentPlayer = game.getCurrentPlayer();

        if(currentPlayer.equals(player)) {
            extraPoints = activatedCharacter.getExtraPoints();
        }

        return influence + extraPoints;
    }
}
