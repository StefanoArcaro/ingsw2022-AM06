package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.ConfirmationBox;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class WizardController implements GUIController {

    private GUI gui;

    public Button DRUID;
    public Button KING;
    public Button WITCH;
    public Button SENSEI;

    public Text wizardChosen;

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public GUI getGUI() {
        return gui;
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

    public void updateAvailableWizards(WizardName wizardName) {
        Scene scene = gui.getSceneByName(Constants.WIZARD);

        // Image
        ImageView wizardImage = (ImageView) scene.lookup("#" + wizardName.getName());
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(-0.2);
        colorAdjust.setSaturation(-1.0);
        wizardImage.setEffect(colorAdjust);

        // Button
        Button wizardButton = getWizardButtonByName(wizardName);
        wizardButton.setDisable(true);
    }

    public void updateWizardChoice(WizardName wizardName) {
        Scene scene = gui.getSceneByName(Constants.WIZARD);
        wizardChosen.setOpacity(1);

        for(WizardName name : WizardName.values()) {
            if(!name.equals(wizardName)) {
                String wizardId = "#" + name.getName();
                ImageView wizardImage = (ImageView) scene.lookup(wizardId);
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setContrast(-0.2);
                colorAdjust.setSaturation(-1.0);
                wizardImage.setEffect(colorAdjust);
            }
            getWizardButtonByName(name).setDisable(true);
        }
    }

    private Button getWizardButtonByName(WizardName wizardName) {
        return switch (wizardName) {
            case DRUID -> DRUID;
            case KING -> KING;
            case WITCH -> WITCH;
            case SENSEI -> SENSEI;
        };
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getStage(),"Are you sure you want to quit?"));

        String message = "QUIT";
        gui.getMessageParser().parseInput(message);
    }

}
