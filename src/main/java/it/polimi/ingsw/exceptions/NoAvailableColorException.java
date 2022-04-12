package it.polimi.ingsw.exceptions;

/**
 * Class NoAvailableColorException is thrown when there are no students on a character
 * card of the specified color.
 */
public class NoAvailableColorException extends Exception {

    /**
     * @return the message (type String) of this NoAvailableColorException object.
     */
    @Override
    public String getMessage() {
        return ("Error: this color is not available, choose another one!");
    }
}
