package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.gui.ConfirmationBox;
import it.polimi.ingsw.view.gui.GUI;
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
     * Creates the WizardRequestMessage to send to the server.
     * @param event event fired by the wizard button.
     */
    public void chooseWizard(ActionEvent event) {
        Button button = (Button) event.getSource();
        String wizardName = button.getId();

        String message = "WIZARD " + wizardName;

        gui.getMessageParser().parseInput(message);
    }

    /**
     * Updates the available wizards.
     * @param wizardName the name of the wizard to remove from the available wizards.
     */
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

    /**
     * Updates the wizards' scene after the client's choice.
     * @param wizardName the name of the chosen wizard.
     */
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

    /**
     * @param wizardName the name of the wizard to return the button of.
     * @return the button relative to the specified wizard.
     */
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
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getPrimaryStage(),"Are you sure you want to quit?"));

        String message = "QUIT";
        gui.getMessageParser().parseInput(message);
    }

}
