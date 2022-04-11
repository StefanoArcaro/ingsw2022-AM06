package it.polimi.ingsw.exceptions;

/**
 * Class TableFullException is thrown when a player tries to place a student in their hall,
 * but the table of that student's color is already full.
 */
public class TableFullException extends Exception {

    /**
     * @return the message (type String) of this TableFullException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the table of the selected color is already full.");
    }
}
