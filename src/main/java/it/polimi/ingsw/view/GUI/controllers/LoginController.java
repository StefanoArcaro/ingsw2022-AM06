package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.view.GUI.AlertBox;
import it.polimi.ingsw.view.GUI.ConfirmationBox;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicInteger;

public class LoginController implements GUIController {

    private GUI gui;
    MessageParser messageParser;

    ObservableList<String> numberOfPlayersList = FXCollections.observableArrayList("2", "3");
    ObservableList<String> gameModeList = FXCollections.observableArrayList("Easy", "Expert");

    @FXML
    private TextField nickname_field;
    @FXML
    private ChoiceBox<String> numberPlayers_box;
    @FXML
    private ChoiceBox<String> gameMode_box;

    @Override
    public GUI getGUI() {
        return gui;
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
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
        messageParser = new MessageParser(gui.getSocketClient());

        String nickname = nickname_field.getText();
        if(nickname.length() == 0) {
            Platform.runLater(() -> AlertBox.display("Error", "Nickname field can't be empty."));
            return;
        }

        String numberOfPlayers = Integer.toString(numberPlayers_box.getSelectionModel().getSelectedIndex() + 2);
        String gameMode = Integer.toString(gameMode_box.getSelectionModel().getSelectedIndex());

        String message = "LOGIN" + " " + nickname + " " + numberOfPlayers + " " + gameMode;

        messageParser.parseInput(message);
    }

    @Override
    public void quit() {
        AtomicInteger status = new AtomicInteger(0);

        Platform.runLater(() -> {
            status.set(ConfirmationBox.display(1, gui.getStage(), "Are you sure you want to quit?"));
        });

        if(status.get() == 1) {
            messageParser = new MessageParser(gui.getSocketClient());
            String message = "QUIT";
            messageParser.parseInput(message);
        }
    }

}
