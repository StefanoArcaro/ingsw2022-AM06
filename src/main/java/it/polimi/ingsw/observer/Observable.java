package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.clientToserver.Message;

import java.util.ArrayList;

/**
 * Generic observable class.
 * It will be extended by the model which is observed by the view.
 * It will be extended by the view which is observed by controller.
 */

public class Observable {

    private boolean changed = false;
    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer to the internal list of observers.
     * @param obs the observer to add.
     */
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     * Deletes an observer from the internal list of observers.
     * @param obs the observer to remove.
     */
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * Passes the message specified in the parameter list to the update() method of the observer.
     * @param message the message to be passed to the observers.
     */
    public void notifyObservers(Message message) {
        for(Observer obs : observers) {
            obs.update(message);
        }
    }

    /**
     * Marks this object as having been changed.
     */
    protected void setChanged() {
        changed = true;
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its observers of its most recent change.
     */
    protected synchronized void clearChanged() {
        changed = false;
    }

    /**
     * Tests if this object has changed.
     * @return  {@code true} if and only if the {@code setChanged}
     *          method has been called more recently than the
     *          {@code clearChanged} method on this object;
     *          {@code false} otherwise.
     */
    public synchronized boolean hasChanged() {
        return changed;
    }
}


