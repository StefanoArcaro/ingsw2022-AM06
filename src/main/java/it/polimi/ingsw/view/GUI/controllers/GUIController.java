package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;

public interface GUIController {

    /**
     * Sets the GUI.
     * @param gui to set.
     */
    void setGUI(GUI gui);

    /**
     * Closes the application when the "Quit" button is pressed.
     **/
    void quit();
}
