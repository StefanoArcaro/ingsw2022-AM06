package it.polimi.ingsw.exceptions;

/**
 * Class NotEnoughMoneyException is thrown when a player tries to spend more money than he has.
 */

public class NotEnoughMoneyException extends Exception{

    /**
     * @return the message (type String) of this NotEnoughMoneyException object.
     */
    @Override
    public String getMessage() {
        return ("Error: you don't have enough money to buy this character");
    }
}
