package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.ExceededStepsException;
import it.polimi.ingsw.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.exceptions.NicknameTakenException;
import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Character;

public abstract class Phase {

    protected Game game;
    protected PhaseFactory phaseFactory;
    protected Character activatedCharacter;
    protected String playerNickname;

    /**
     * Method to be implemented by the different phases of the game
     */
    public abstract void play() throws ExceededStepsException, NoAvailableCloudException, NicknameTakenException, MaxPlayersReachedException;

    /**
     * @return the activated character
     */
    public Character getActivatedCharacter() {
        return activatedCharacter;
    }

    /**
     * Sets the nickname of the player to add
     * @param playerNickname to set
     */
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }
}
