package it.polimi.ingsw.exceptions;

/**
 * Class TooManyIterationsException is thrown when the effect of a character is tried to be used
 * more times than allowed
 */

public class TooManyIterationsException extends Exception{

    /**
     * @return the message (type String) of this TooManyIterationsException object.
     */
    @Override
    public String getMessage() {
        return ("Error: you can't use this effect so many times!");
    }

}
