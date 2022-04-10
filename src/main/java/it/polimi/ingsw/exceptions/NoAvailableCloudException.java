package it.polimi.ingsw.exceptions;

/**
 * Class NoAvailableCloudException is thrown when the cloud chosen is not available.
 */
public class NoAvailableCloudException extends Exception {

    /**
     * @return the message (type String) of this NoAvailableCloudException object.
     */
    @Override
    public String getMessage() {
        return ("Error: this cloud is not available, choose another one!");
    }
}
