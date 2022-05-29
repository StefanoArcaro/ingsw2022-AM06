package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.ConfirmationBox;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class WizardController implements GUIController {

    private GUI gui;
    MessageParser messageParser;


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * Handles the wizard choice.
     * @param event event to handle (button).
     */
    public void chooseWizard(ActionEvent event) {
        messageParser = new MessageParser(gui.getSocketClient());

        Button button = (Button) event.getSource();
        String wizardName = button.getId();

        String message = "WIZARD " + wizardName;

        messageParser.parseInput(message);
        gui.changeStage(Constants.BOARD_AND_ISLANDS);
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getStage(),"Are you sure you want to quit?"));

        messageParser = new MessageParser(gui.getSocketClient());
        String message = "QUIT";
        messageParser.parseInput(message);
    }
}
