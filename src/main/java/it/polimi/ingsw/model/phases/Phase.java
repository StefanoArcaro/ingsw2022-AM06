package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.Character;

public abstract class Phase {

    protected Game game;
    protected PhaseFactory phaseFactory;
    protected Character activatedCharacter;
    protected String playerNickname;
    protected int wizardID;

    /**
     * Method to be implemented by the different phases of the game
     */
    public abstract void play() throws ExceededStepsException, NoAvailableCloudException, NicknameTakenException, MaxPlayersReachedException, WizardTakenException, InvalidWizardException;

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

    /**
     * Sets the ID of the wizard to assign to the player that chose it
     * @param wizardID to set
     */
    public void setWizardID(int wizardID) {
        this.wizardID = wizardID;
    }
}
