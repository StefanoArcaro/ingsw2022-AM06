package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;

public interface GUIController {

    void setGUI(GUI gui);

    /**
     * Closes the application when the "Quit" button is pressed.
     **/
    void quit();
}
