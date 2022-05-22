package it.polimi.ingsw.view.GUI.controllers;

public class MainController implements GUIController {

    /** Method quit kills the application when the "Quit" button is pressed. */
    public void quit() {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }


}
