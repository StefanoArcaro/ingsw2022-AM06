package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MoveMotherNatureController implements GUIController {

    GUI gui;

    @FXML
    private ChoiceBox<Integer> numberOfSteps;

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
        int maxSteps = gui.getModelView().getMaxSteps();
        ObservableList<Integer> numberOfStepsList = FXCollections.observableArrayList();

        for(int i = 1; i <= maxSteps; i++) {
            numberOfStepsList.add(i);
        }

        numberOfSteps.setItems(numberOfStepsList);
        numberOfSteps.getSelectionModel().selectFirst();
    }

    /**
     * Creates the MoveMotherNatureMessage to send to the server.
     * @param event the event fired by the OK button.
     */
    public void onSubmitSteps(ActionEvent event) {
        Button button = (Button) event.getSource();
        int steps = numberOfSteps.getSelectionModel().getSelectedItem();

        String message = "MOVEMOTHERNATURE " + steps;
        gui.getMessageParser().parseInput(message);

        Stage stage = (Stage)(button.getScene().getWindow());
        stage.close();
    }

    @Override
    public void quit() {

    }

}
