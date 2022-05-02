package it.polimi.ingsw.model.characters;

public class CharacterStepsAdder extends Character {

    /**
     * Default constructor.
     * @param characterID id of the character to create.
     */
    public CharacterStepsAdder(int characterID) {
        this.characterID = characterID;
        this.cost = 1;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 0;
        this.numberOfIterations = 0;
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
        this.moreSteps = 2;
    }

    @Override
    public void effect() {
        // do nothing
    }
}
