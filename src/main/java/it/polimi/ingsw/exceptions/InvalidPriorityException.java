package it.polimi.ingsw.exceptions;

/**
 * Class InvalidPriorityException is thrown when an invalid priority is passed as a parameter.
 */
public class InvalidPriorityException extends Exception {

    /**
     * @return the message (type String) of this InvalidPriorityException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the selected priority is invalid (out of bounds or already played.");
    }
}
