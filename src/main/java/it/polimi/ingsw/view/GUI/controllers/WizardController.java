package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.ConfirmationBox;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class WizardController implements GUIController {

    private GUI gui;

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void init() {

    }

    /**
     * Handles the wizard choice.
     * @param event event to handle (button).
     */
    public void chooseWizard(ActionEvent event) {
        Button button = (Button) event.getSource();
        String wizardName = button.getId();

        String message = "WIZARD " + wizardName;

        gui.getMessageParser().parseInput(message);
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getStage(),"Are you sure you want to quit?"));

        String message = "QUIT";
        gui.getMessageParser().parseInput(message);
    }

    @Override
    public GUI getGUI() {
        return gui;
    }
}
