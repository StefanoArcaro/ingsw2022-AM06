package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;

public interface GUIController {

    /**
     * @return the GUI.
     */
    GUI getGUI();

    /**
     * Sets the GUI.
     * @param gui to set.
     */
    void setGUI(GUI gui);

    /**
     * Initializes the scene.
     */
    void init();

    /**
     * Closes the application when the "Quit" button is pressed.
     **/
    void quit();

}
