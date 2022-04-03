package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Character;

public abstract class Phase {

    protected Game game;
    protected Character activatedCharacter;

    /**
     * Method to be implemented by the different phases of the game
     */
    public abstract void play();

    public Character getActivatedCharacter() {
        return activatedCharacter;
    }
}
