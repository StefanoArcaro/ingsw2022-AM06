package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;

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
