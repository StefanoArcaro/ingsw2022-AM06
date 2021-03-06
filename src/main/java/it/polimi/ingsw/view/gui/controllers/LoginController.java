package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.AlertBox;
import it.polimi.ingsw.view.gui.ConfirmationBox;
import it.polimi.ingsw.view.gui.GUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicInteger;

public class LoginController implements GUIController {

    private GUI gui;

    final ObservableList<String> numberOfPlayersList = FXCollections.observableArrayList("2", "3");
    final ObservableList<String> gameModeList = FXCollections.observableArrayList("Easy", "Expert");

    public TextField nickname_field;
    public ChoiceBox<String> numberPlayers_box;
    public ChoiceBox<String> gameMode_box;

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

    @FXML
    private void initialize() {
        numberPlayers_box.setItems(numberOfPlayersList);
        numberPlayers_box.getSelectionModel().selectFirst();
        gameMode_box.setItems(gameModeList);
        gameMode_box.getSelectionModel().selectFirst();
    }

    /**
     * Takes the information of the login form, performs the necessary checks.
     * If all is well, formats the login message to send.
     */
    public void login() {
        String nickname = nickname_field.getText();
        if(nickname.length() == 0) {
            Platform.runLater(() -> AlertBox.display("Error", "Nickname field can't be empty."));
            return;
        }

        String numberOfPlayers = Integer.toString(numberPlayers_box.getSelectionModel().getSelectedIndex() + 2);
        String gameMode = Integer.toString(gameMode_box.getSelectionModel().getSelectedIndex());

        String message = "LOGIN" + " " + nickname + " " + numberOfPlayers + " " + gameMode;

        gui.getMessageParser().parseInput(message);
    }

    @Override
    public void quit() {
        AtomicInteger status = new AtomicInteger(0);

        Platform.runLater(() -> status.set(ConfirmationBox.display(1, gui.getPrimaryStage(), "Are you sure you want to quit?")));

        if(status.get() == 1) {
            String message = "QUIT";
            gui.getMessageParser().parseInput(message);
        }
    }

}
