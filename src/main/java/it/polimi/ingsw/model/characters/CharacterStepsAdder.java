package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class CharacterStepsAdder extends Character{

    public CharacterStepsAdder (int characterID){
        this.characterID = characterID;
        this.cost = 1;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.moreSteps = 2;
        this.toDoNow = 0;
    }

    @Override
    public void effect() {
        // do nothing
    }
}
