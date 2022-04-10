package it.polimi.ingsw.exceptions;

/**
 * Class NoAvailableBanCardsException is thrown when more ban cards than the available are trying to be used.
 */

public class NoAvailableBanCardsException extends Exception{

    /**
     * @return the message (type String) of this NoAvailableBanCardsException object.
     */
    @Override
    public String getMessage() {
        return ("Error: no more ban cards available on this card");
    }
}
