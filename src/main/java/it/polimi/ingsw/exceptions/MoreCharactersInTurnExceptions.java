package it.polimi.ingsw.exceptions;

/**
 * Class MoreCharactersInTurnException is thrown when an invalid priority is passed as a parameter.
 */
public class MoreCharactersInTurnExceptions extends Exception {

    /**
     * @return the message (type String) of this MoreCharactersInTurnException object.
     */
    @Override
    public String getMessage() {
        return ("Error: you can't play different characters in one turn!");
    }
}

