package it.polimi.ingsw.exceptions;

/**
 * Class AssistantTakenException is thrown when an assistant has already been chosen by another player.
 */
public class AssistantTakenException extends Exception {

    /**
     * @return the message (type String) of this AssistantTakenException object.
     */
    @Override
    public String getMessage() {
        return ("Error: this assistant has already been played by another player.");
    }
}
