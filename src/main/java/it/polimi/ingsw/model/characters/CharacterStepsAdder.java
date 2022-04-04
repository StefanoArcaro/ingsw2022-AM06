package it.polimi.ingsw.model.characters;

public class CharacterStepsAdder extends Character {

    int moreSteps;

    public CharacterStepsAdder(int characterID) {
        this.characterID = characterID;
        this.cost = 1;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.moreSteps = 2;
        this.toDoNow = 0;
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
    }

    @Override
    public void effect() {
        // do nothing
    }

    /**
     * @return number of steps added
     */
    public int getMoreSteps() {
        return moreSteps;
    }
}
