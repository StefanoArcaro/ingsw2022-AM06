package it.polimi.ingsw.model.characters;

public class CharacterNone extends Character {

    public CharacterNone(int characterID) {
        this.characterID = characterID;
        this.cost = 0;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 0;
        this.numberOfIterations = 0;
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
        this.moreSteps = 0;
    }

    @Override
    public void effect() {
        // do nothing
    }
}
