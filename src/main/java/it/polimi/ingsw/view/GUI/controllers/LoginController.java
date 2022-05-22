package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.network.message.clientToserver.LoginRequestMessage;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class LoginController implements GUIController {

    private GUI gui;
    MessageParser messageParser;

    ObservableList<String> numberOfPlayersList = FXCollections.observableArrayList("2", "3");
    ObservableList<String> gameModeList = FXCollections.observableArrayList("Easy", "Expert");


    @FXML
    private TextField nickname_field;
    @FXML
    private ChoiceBox numberPlayers_box;
    @FXML
    private ChoiceBox gameMode_box;

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


    public void login() {
        messageParser = new MessageParser(gui.getSocketClient()); //todo check

        //TODO add error case
        String nickname = nickname_field.getText();
        String numberOfPlayers = Integer.toString(numberPlayers_box.getSelectionModel().getSelectedIndex() + 2);
        String gameMode = Integer.toString(gameMode_box.getSelectionModel().getSelectedIndex());



        String message = "LOGIN" + " " + nickname + " " + numberOfPlayers + " " + gameMode;

        messageParser.parseInput(message);

        //todo continue

    }

    /**
     * Closes the application when the "Quit" button is pressed.
     **/
    public void quit() {
        messageParser = new MessageParser(gui.getSocketClient()); //todo check

        String message = "QUIT";
        messageParser.parseInput(message);
    }

}
