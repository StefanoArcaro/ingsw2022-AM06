package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;

public class CharacterInfluenceCalculator extends Character {

    private int islandGroupIndex;

    /**
     * Default constructor
     * @param game game played
     * @param characterID id of the character to create
     */
    public CharacterInfluenceCalculator(Game game, int characterID) {
        this.characterID = characterID;
        this.game = game;
        this.cost = 3;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 1;
        this.numberOfIterations = 0;
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
        this.moreSteps = 0;
    }

    /**
     * Set the index of island group where to calculate the influence
     * @param islandGroupIndex where calculate the influence
     */
    public void setIslandGroupIndex(int islandGroupIndex) {
        this.islandGroupIndex = islandGroupIndex; // TODO: input
    }

    /**
     * @return the index of island group where to calculate the influence
     */
    public int getIslandGroupIndex() {
        return islandGroupIndex;
    }

    /**
     * Calculates the influence on the island group chosen
     * @throws OutOfBoundException when the index of the island group chosen doesn't exist
     */
    public void effect() throws OutOfBoundException {
        int numberOfIslandGroups = game.getIslandGroups().size();

        if(islandGroupIndex >= 0 && islandGroupIndex < numberOfIslandGroups) {
            game.calculateInfluence(this.islandGroupIndex, this);
        } else {
            throw new OutOfBoundException();
        }
    }
}
