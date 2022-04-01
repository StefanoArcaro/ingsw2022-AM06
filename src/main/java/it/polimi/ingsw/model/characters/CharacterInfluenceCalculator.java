package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

public class CharacterInfluenceCalculator extends Character {

    private int islandGroupIndex;

    public CharacterInfluenceCalculator(int characterID) {
        this.characterID = characterID;
        this.cost = 3;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 1;
    }

    /**
     * Set the index of island group where to calculate the influence
     * @param islandGroupIndex where calculate the influence
     */
    public void setIslandGroupIndex(int islandGroupIndex){
        this.islandGroupIndex=islandGroupIndex;
    }

    /**
     * @return the index of island group where to calculate the influence
     */
    public int getIslandGroupIndex() {
        return islandGroupIndex;
    }

    /**
     * Calculates the influence on the island group chosen
     */
    public void effect() {
        Game.getGame().calculateInfluence(this.islandGroupIndex);
    }
}
