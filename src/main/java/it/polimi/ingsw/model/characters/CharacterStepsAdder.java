package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.Constants;

public class CharacterStepsAdder extends Character {

    /**
     * Default constructor.
     * @param characterID id of the character to create.
     */
    public CharacterStepsAdder(Game game, int characterID) {
        this.characterID = characterID;
        this.game = game;
        this.cost = 1;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 1;
        this.numberOfIterations = 0;
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
        this.moreSteps = 2;
    }

    @Override
    public void effect() {
        game.getListeners().firePropertyChange(Constants.CHARACTER_PLAYED_LISTENER, null, this);
    }
}
