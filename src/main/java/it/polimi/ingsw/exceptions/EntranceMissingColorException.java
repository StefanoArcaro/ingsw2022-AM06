package it.polimi.ingsw.exceptions;

/**
 * Class EntranceMissingColorException is thrown when a student of the selected color
 * is not present in the current player's entrance.
 */
public class EntranceMissingColorException extends Exception {

    /**
     * @return the message (type String) of this EntranceMissingColorException object.
     */
    @Override
    public String getMessage() {
        return ("Error: there are no students of this color in your entrance.");
    }
}
