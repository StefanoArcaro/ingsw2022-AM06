package it.polimi.ingsw;

public class CharacterStepsAdder extends Character{

    public CharacterStepsAdder (int characterID){
        this.characterID = characterID;
        this.cost = 1;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
    }

    @Override
    public void effect() {

    }
}
