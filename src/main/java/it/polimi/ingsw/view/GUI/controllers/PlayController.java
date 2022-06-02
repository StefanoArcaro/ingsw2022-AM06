package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.message.serverToclient.BoardMessage;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.AlertBox;
import it.polimi.ingsw.view.GUI.ConfirmationBox;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PlayController implements GUIController {

    private GUI gui;

    public Text name;
    public Label coins;
    public Text numOfCoins;
    public Text currentNickname;
    public Text currentPhase;


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
        String playerNickname = modelView.getNickname();
        Board board = modelView.getBoard(playerNickname);
        Entrance entrance = board.getEntrance();
        ArrayList<Cloud> clouds = modelView.getClouds();
        ArrayList<IslandGroup> islands = modelView.getIslandGroups();
        String phase = modelView.getCurrentPhase();
        String player = modelView.getCurrentPlayer();

        initInfo(modelView, playerNickname, player, phase);

        initEntrance(entrance);

        initHall();

        initProfessors();

        initTowers(modelView, board, playerNickname);



        //TODO: set islands and clouds
    }

    private void initInfo(ModelView modelView, String playerNickname, String player, String phase) {
        name.setText(playerNickname);

        if(modelView.getGameMode().equals(GameMode.EXPERT)) {
            numOfCoins.setText(Integer.toString(modelView.getCoinsByNickname(playerNickname)));
        } else {
            coins.setOpacity(0);
            numOfCoins.setText("");
        }

        currentNickname.setText(player);
        currentPhase.setText(phase);
    }

    private void initEntrance(Entrance entrance) {
        //TODO: change to updateEntrance
        for(int i = 0; i < entrance.getStudents().size(); i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) gui.getCurrentScene().lookup(entranceID);
            CreatureColor color = entrance.getStudents().get(i).getColor();
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color));
            entranceButton.setStyle(style);
        }

        for(int i = entrance.getStudents().size(); i < 9; i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) gui.getCurrentScene().lookup(entranceID);
            entranceButton.setOpacity(0);
            entranceButton.setDisable(true);
        }
    }

    private void initHall() {
        for(Node node : gridPane.getChildren()) {
            node.setOpacity(0);
            node.setDisable(true);
        }
    }

    private void initProfessors() {
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
    }

    private void initTowers(ModelView modelView, Board board, String playerNickname) {
        for(int i = 0; i < board.getTowers(); i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) gui.getCurrentScene().lookup(towerID);
            PlayerColor color = modelView.getPlayers().get(playerNickname);
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByPlayerColor(color));
            tower.setStyle(style);
        }

        for(int i = board.getTowers(); i < 8; i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) gui.getCurrentScene().lookup(towerID);
            tower.setOpacity(0);
        }
    }

    public void updateCurrentPlayer(String player) {
        currentNickname.setText(player);
    }

    public void updateCurrentPhase(String phase) {
        currentPhase.setText(phase);
    }

    public void updateCoins(int coins) {
        numOfCoins.setText(Integer.toString(coins));
    }

    public void updateBoard(BoardMessage msg) {
        ModelView modelView = gui.getModelView();
        String nicknameBoard = msg.getNickname();
        Entrance entrance = msg.getEntrance();
        Hall hall = msg.getHall();
        ArrayList< Professor > professors = msg.getProfessors();
        int towers = msg.getTowers();

        if(nicknameBoard.equals(modelView.getNickname())) {
            //modify main scene
            updateEntrance(entrance);
            updateHall(hall);
            updateProfessors(professors);
            updateTowers(towers, modelView.getPlayers().get(modelView.getNickname()));
        } else {
            //todo: modify opponent board (nicknameBoard)
        }

    }

    private void updateEntrance(Entrance entrance) {
        Scene scene = gui.getSceneByName(Constants.BOARD_AND_ISLANDS);

        for(int i = 0; i < entrance.getStudents().size(); i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            CreatureColor color = entrance.getStudents().get(i).getColor();
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color));
            entranceButton.setStyle(style);
        }

        for(int i = entrance.getStudents().size(); i < 9; i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            entranceButton.setOpacity(0);
            entranceButton.setDisable(true);
        }
    }

    //TODO check
    private void updateHall(Hall hall) {
        ArrayList<Table> tables = hall.getStudents();

        int row = 0;
        for(Table table : tables) {
            for(int column = 0; column < table.getLength(); column++) {
                Node node = getNodeByRowColumnIndex(row, column, gridPane);
                if(node != null) {
                    node.setOpacity(1);
                    node.setDisable(false);
                }
            }
            row++;
        }
    }

    private Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        ObservableList<Node> children = gridPane.getChildren();

        for(Node node : children) {
            int rowIndex = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
            int columnIndex = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);

            if(rowIndex == row && columnIndex == column) {
                return node;
            }
        }

        return null;
    }

    //TODO check
    private void updateProfessors(ArrayList<Professor> professors) {
        for(Professor professor : professors) {
            switch (professor.getColor()) {
                case GREEN -> professor_green.setOpacity(1);
                case RED -> professor_red.setOpacity(1);
                case YELLOW -> professor_yellow.setOpacity(1);
                case PINK -> professor_pink.setOpacity(1);
                case BLUE -> professor_blue.setOpacity(1);
            }
        }
    }

    //TODO check
    private void updateTowers(int towers, PlayerColor color) {
        Scene scene = gui.getSceneByName(Constants.BOARD_AND_ISLANDS);

        for(int i = 0; i < towers; i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) scene.lookup(towerID);
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByPlayerColor(color));
            tower.setStyle(style);
        }

        for(int i = towers; i < 8; i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) scene.lookup(towerID);
            tower.setOpacity(0);
        }
    }







    public void onEntranceClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        CreatureColor color = getButtonColor(button);

        gui.setEntranceColor(color);
    }

    public void onHallClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        CreatureColor color = getButtonColor(button);

        gui.setHallColor(color);
    }

    private CreatureColor getButtonColor(Button button) {
        Color color = (Color) button.getBackground().getFills().get(0).getFill();

        return gui.getCreatureColorByFXColor(color);
    }



    public void onOpenPlayAssistants(ActionEvent event) {
        try {
            Stage stage = new Stage();

            GUIController controller = gui.getNameToController().get(Constants.ASSISTANTS);
            Scene scene = gui.getSceneByController(controller);

            stage.setScene(scene);
            stage.show();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void onMoveStudentToHall() {
        CreatureColor entranceColor = gui.getEntranceColor();

        if(entranceColor != null) {
            String message = "MOVESTUDENT " + entranceColor.getColorName() + " 0";
            gui.getMessageParser().parseInput(message);
            gui.setEntranceColor(null);
        } else {
            Platform.runLater(() -> AlertBox.display("Wrong move", "Please select a student from your entrance."));
        }
    }

    public void onMoveStudentToIsland(ActionEvent event) {
    }

    public void showOpponents(ActionEvent event) {
    }

    public void pickCloud(ActionEvent event) {
    }

    public void onOpenMotherNature() {
        try {
            Stage stage = new Stage();

            GUIController controller = gui.getNameToController().get(Constants.MOTHER_NATURE);
            controller.init();
            Scene scene = gui.getSceneByController(controller);

            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
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

    public void onOpenCharactersInfo(ActionEvent event) {
    }

    public void onOpenPlayCharacter(ActionEvent event) {
    }
}
