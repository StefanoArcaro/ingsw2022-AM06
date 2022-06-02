package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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

    //TODO check
    public void updateAvailable(ArrayList<Assistant> assistants) {
        Scene scene = gui.getSceneByName(Constants.ASSISTANTS);

        ArrayList<Integer> priorities = new ArrayList<>();
        for(int i = 1; i < 11; i++) {
            priorities.add(i);
        }

        List<Integer> prioritiesAvailable = assistants.stream().map(Assistant::getPriority).toList();

        priorities.removeAll(prioritiesAvailable);

        for(Integer priority : priorities) {
            String assistantID = "#assistant_" + (priority);
            Button assistantButton = (Button) scene.lookup(assistantID);
            assistantButton.setDisable(true);
        }


    }

    @Override
    public void quit() {

    }
}
