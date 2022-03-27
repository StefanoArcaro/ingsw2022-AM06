package it.polimi.ingsw;

public class CharacterStepsAdder extends Character{

    public CharacterStepsAdder (int characterID){
        this.characterID = characterID;
        this.cost = 1;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.moreSteps = 2;
    }

    @Override
    public void effect() {
        // do nothing messo in asssistant
        /* non serve
        Player currentPlayer = Game.getCurrentRound().getCurrentPlayer();
        int maxSteps = Game.getCurrentRound().getPlayerPriority().get(currentPlayer).getMaxSteps();
        Game.getCurrentRound().getPlayerPriority().get(currentPlayer).setMaxSteps(maxSteps + moreSteps);
        */
    }
}
