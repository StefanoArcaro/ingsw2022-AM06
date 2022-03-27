package it.polimi.ingsw;

public class CharacterInfluenceCalculator extends Character{

    public CharacterInfluenceCalculator(int characterID) {
        this.characterID = characterID;
        this.cost = 3;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.moreSteps = 0;
    }

    @Override
    public void effect() {

    }
}
