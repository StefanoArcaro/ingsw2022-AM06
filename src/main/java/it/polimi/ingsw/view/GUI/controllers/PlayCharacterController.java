package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.event.ActionEvent;

public class PlayCharacterController implements GUIController{

    GUI gui;

    @Override
    public GUI getGUI() {
        return gui;
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void init() {

    }

    @Override
    public void quit() {

    }

    public void onPlayCharacter(ActionEvent event) {
    }

    public void onCharacterInfo(ActionEvent event) {
    }
}
