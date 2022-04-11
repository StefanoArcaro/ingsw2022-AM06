package it.polimi.ingsw.exceptions;

/**
 * Class InvalidColorException is thrown when an invalid creature color is passed as a parameter.
 */
public class InvalidColorException extends Exception {

    /**
     * @return the message (type String) of this InvalidColorException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the selected color does not exist.");
    }
}
