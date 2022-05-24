package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class WizardController implements GUIController {

    private GUI gui;
    MessageParser messageParser;


    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


    public void chooseWizard(ActionEvent event) {
        //TODO add error case

        messageParser = new MessageParser(gui.getSocketClient());

        Button button = (Button) event.getSource();
        String wizardName = button.getId();

        String message = "WIZARD " + wizardName;

        messageParser.parseInput(message);

        //todo continue

    }
}
