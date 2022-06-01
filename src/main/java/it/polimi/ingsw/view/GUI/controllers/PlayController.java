package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.view.GUI.ConfirmationBox;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayController implements GUIController {

    public GUI gui;
    public MessageParser messageParser;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button professor_green;
    @FXML
    private Button professor_red;
    @FXML
    private Button professor_yellow;
    @FXML
    private Button professor_pink;
    @FXML
    private Button professor_blue;

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    // TODO method to set the game scene up
    public void init() {
        ModelView modelView = gui.getModelView();
        String nickname = modelView.getNickname();
        Board board = modelView.getBoard(nickname);
        Entrance entrance = board.getEntrance();
        Hall hall = board.getHall();
        ArrayList<Cloud> clouds = modelView.getClouds();
        ArrayList<IslandGroup> islands = modelView.getIslandGroups();
        String currentPhase = modelView.getCurrentPhase();
        String currentPlayer = modelView.getCurrentPlayer();

        // Set entrance buttons' colors to the entrance students' ones
        for(int i = 0; i < entrance.getStudents().size(); i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) gui.getCurrentScene().lookup(entranceID);
            CreatureColor color = entrance.getStudents().get(i).getColor();
            entranceButton.setStyle("-fx-background-color: " + getHex(color));
        }

        // Set hall
        for(Node node : gridPane.getChildren()) {
            node.setOpacity(0);
            node.setDisable(true);
        }

        // Set professors
        professor_green.setOpacity(0);
        professor_green.setDisable(true);

        professor_red.setOpacity(0);
        professor_red.setDisable(true);

        professor_yellow.setOpacity(0);
        professor_yellow.setDisable(true);

        professor_pink.setOpacity(0);
        professor_pink.setDisable(true);

        professor_blue.setOpacity(0);
        professor_blue.setDisable(true);



        // Set towers

    }

    private String getHex(CreatureColor color) {
        return switch(color) {
            case GREEN -> "#06d132";
            case RED -> "#ed0924";
            case YELLOW -> "#e6ed1f";
            case PINK -> "#eb81ce";
            case BLUE -> "#1b24de";
        };
    }


    public void setColor(ActionEvent event) {
        Button button = (Button) event.getSource();
        CreatureColor color = getColorByButton(button);

    }

    private CreatureColor getColorByButton(Button button) {
        return null; //todo
    }

    public void playAssistant(ActionEvent event) {
    }

    public void getCharactersInfo(ActionEvent event) {
    }

    public void moveStudentToHall(ActionEvent event) {
    }

    public void moveStudentToIsland(ActionEvent event) {
    }

    public void playCharacter(ActionEvent event) {
    }

    public void showOpponents(ActionEvent event) {
    }

    public void pickCloud(ActionEvent event) {
    }

    public void moveMotherNature(ActionEvent event) {
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getStage(),"Are you sure you want to quit?"));

        messageParser = new MessageParser(gui.getSocketClient());
        String message = "QUIT";
        messageParser.parseInput(message);
    }

    @Override
    public GUI getGUI() {
        return gui;
    }

}
