package it.polimi.ingsw.exceptions;

/**
 * Class NicknameTakenException is thrown when a nickname has already been chosen by another player.
 */
public class NicknameTakenException extends Exception {

    /**
     * @return the message (type String) of this NicknameTakenException object.
     */
    @Override
    public String getMessage() {
        return ("Error: this nickname has already been chosen.");
    }
}
