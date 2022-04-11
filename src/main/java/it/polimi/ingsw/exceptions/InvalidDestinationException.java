package it.polimi.ingsw.exceptions;

/**
 * Class InvalidDestinationException is thrown when an invalid destination is passed as a parameter.
 */
public class InvalidDestinationException extends Exception {

    /**
     * @return the message (type String) of this InvalidDestinationException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the selected destination is invalid (must be between 0 and 12).");
    }
}
