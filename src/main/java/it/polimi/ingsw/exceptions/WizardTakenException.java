package it.polimi.ingsw.exceptions;

/**
 * Class WizardTakenException is thrown when a wizard has already been chosen by another player.
 */
public class WizardTakenException extends Exception {

    /**
     * @return the message (type String) of this WizardTakenException object.
     */
    @Override
    public String getMessage() {
        return ("Error: this wizard has already been chosen.");
    }
}
