package it.polimi.ingsw.exceptions;

/**
 * Class MaxPlayersReachedException is thrown when the number of players has reached the chosen number
 * and another player tries to enter the game.
 */
public class MaxPlayersReachedException extends Exception {

    /**
     * @return the message (type String) of this MaxPlayersReachedException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the maximum number of players for this game has been reached.");
    }
}
