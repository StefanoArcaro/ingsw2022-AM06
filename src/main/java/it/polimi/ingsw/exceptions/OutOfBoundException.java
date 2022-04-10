package it.polimi.ingsw.exceptions;

/**
 * Class OutOfBoundException is thrown when the limits of an object are trying being exceeded.
 */

public class OutOfBoundException extends Exception{

    /**
     * @return the message (type String) of this OutOfBoundException object.
     */
    @Override
    public String getMessage() {
        return ("Error: id/index inserted doesn't exists!");
    }
}
