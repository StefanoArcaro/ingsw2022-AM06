package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.Constants;

public class CharacterInfluenceCalculator extends Character {

    /**
     * Default constructor.
     * @param game game played.
     * @param characterID id of the character to create.
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
     * Calculates the influence on the island group chosen.
     * @throws OutOfBoundException when the index of the island group chosen doesn't exist.
     */
    public void effect() throws OutOfBoundException {
        int numberOfIslandGroups = game.getIslandGroups().size();

        if(islandGroupIndex >= 0 && islandGroupIndex < numberOfIslandGroups) {
            game.calculateInfluence(this.islandGroupIndex);
        } else {
            throw new OutOfBoundException();
        }
    }
}
