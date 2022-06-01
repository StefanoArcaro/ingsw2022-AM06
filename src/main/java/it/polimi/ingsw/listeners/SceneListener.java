package it.polimi.ingsw.listeners;

import it.polimi.ingsw.view.GUI.GUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SceneListener implements PropertyChangeListener {
    //TODO: delete?

    private final GUI gui;
    private String scene;

    public SceneListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Listener scene");
        scene = (String) evt.getNewValue();
        gui.changeStage(scene);
    }
}
