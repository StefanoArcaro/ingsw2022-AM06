package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PlayAssistantController implements GUIController {

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

    public void onPlayAssistant(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String assistantIDString = button.getId();
        String id = assistantIDString.substring(10);

        String message = "ASSISTANT " + id;
        gui.getMessageParser().parseInput(message);

        Stage stage = (Stage)(button.getScene().getWindow());
        stage.close();
    }

    @Override
    public void quit() {
        //TODO ?
    }
}
