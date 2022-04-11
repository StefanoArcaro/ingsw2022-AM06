package it.polimi.ingsw.exceptions;

/**
 * Class InvalidWizardException is thrown when an incorrect wizard ID is passed as a parameter.
 */
public class InvalidWizardException extends Exception {

    /**
     * @return the message (type String) of this InvalidWizardException object.
     */
    @Override
    public String getMessage() {
        return ("Error: the selected wizard does not exist.");
    }

}
