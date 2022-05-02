package it.polimi.ingsw.listeners;

import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeListener;

//interface used to communicate to View from Model.

public abstract class Listener implements PropertyChangeListener {

    protected final VirtualView virtualView;

    public Listener(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

}
