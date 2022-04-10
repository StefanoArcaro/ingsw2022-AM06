package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.ExceededStepsException;
import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Character;

public abstract class Phase {

    protected Game game;
    protected Character activatedCharacter;

    /**
     * Method to be implemented by the different phases of the game
     */
    public abstract void play() throws ExceededStepsException, NoAvailableCloudException;

    /**
     * @return the activated character
     */
    public Character getActivatedCharacter() {
        return activatedCharacter;
    }
}
