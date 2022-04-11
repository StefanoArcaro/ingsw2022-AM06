package it.polimi.ingsw.exceptions;

/**
 * Class ExceededStepsException is thrown when more steps are taken than allowed.
 */
public class ExceededStepsException extends Exception {

    /**
     * @return the message (type String) of this ExceededStepsException object.
     */
    @Override
    public String getMessage() {
        return ("Error: you can't do so many steps!");
    }
}
