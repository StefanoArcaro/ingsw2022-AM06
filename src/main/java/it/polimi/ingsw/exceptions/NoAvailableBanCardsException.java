package it.polimi.ingsw.exceptions;

/**
 * Class NoAvailableBanCardsException is thrown when there are no more ban cards and
 * a player tries to play one.
 */
public class NoAvailableBanCardsException extends Exception {

    /**
     * @return the message (type String) of this NoAvailableBanCardsException object.
     */
    @Override
    public String getMessage() {
        return ("Error: no more ban cards available on this card");
    }
}
