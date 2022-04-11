package it.polimi.ingsw.exceptions;

/**
 * Class InvalidCharacterIDException is thrown when an invalid character ID is passed as a parameter.
 */
public class InvalidCharacterIDException extends Exception {

    /**
     * @return the message (type String) of this InvalidCharacterIDException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the selected character is not available.");
    }
}
