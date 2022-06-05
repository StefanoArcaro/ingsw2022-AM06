package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.message.serverToclient.BoardMessage;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BoardController {

    // INIT

    protected void initEntrance(GUI gui, Scene scene, Entrance entrance) {

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

    protected void initHall(GridPane gridPane) {
        for(Node node : gridPane.getChildren()) {
            node.setOpacity(0);
            node.setDisable(true);
        }
    }

    protected void initProfessors(ArrayList<Button> professors) {
        for(Button professor : professors) {
            professor.setOpacity(0);
            professor.setDisable(true);
        }
    }

    protected void initTowers(GUI gui, Scene scene, ModelView modelView, String playerNickname) {
        Board board = modelView.getBoard(playerNickname);

        for(int i = 0; i < board.getTowers(); i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) scene.lookup(towerID);
            PlayerColor color = modelView.getPlayers().get(playerNickname);
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByPlayerColor(color));
            tower.setStyle(style);
        }

        for(int i = board.getTowers(); i < 8; i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) scene.lookup(towerID);
            tower.setOpacity(0);
        }
    }

    protected void initOpponent(String opponentNickname) {}

    // UPDATE

    protected void updateBoard(GUI gui, Scene scene, BoardMessage message, GridPane gridPane, ArrayList<Button> FXProfessors) {
        ModelView modelView = gui.getModelView();

        updateEntrance(gui, scene, message.getEntrance());
        updateHall(message.getHall(), gridPane);
        updateProfessors(FXProfessors, message.getProfessors());
        updateTowersBoard(scene, gui, message.getTowers(), modelView.getPlayers().get(message.getNickname()));

    }

    protected void updateOpponent(BoardMessage message) {    }

    protected void updateEntrance(GUI gui, Scene scene, Entrance entrance) {

        for(int i = 0; i < entrance.getStudents().size(); i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            CreatureColor color = entrance.getStudents().get(i).getColor();
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color));
            entranceButton.setStyle(style);
            entranceButton.setOpacity(1);
            entranceButton.setDisable(false);
        }

        for(int i = entrance.getStudents().size(); i < 9; i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            entranceButton.setOpacity(0);
            entranceButton.setDisable(true);
        }
    }

    protected void updateHall(Hall hall, GridPane gridPane) {
        ArrayList<Table> tables = hall.getStudents();

        int row = 0;
        for(Table table : tables) {
            int column;
            for(column = 0; column < table.getLength(); column++) {
                Node node = getNodeByRowColumnIndex(row, column, gridPane);
                if(node != null) {
                    node.setOpacity(1);
                    node.setDisable(false);
                }
            }

            //todo check - (character effect)
            while(column < 10) {
                Node node = getNodeByRowColumnIndex(row, column, gridPane);
                if(node != null) {
                    node.setOpacity(0);
                    node.setDisable(true);
                }
                column++;
            }
            row++;
        }
    }

    protected void updateProfessors(ArrayList<Button> FXProfessors, ArrayList<Professor> professors) {
        for(Professor professor : professors) {
            switch (professor.getColor()) {
                case GREEN -> FXProfessors.get(0).setOpacity(1);
                case RED -> FXProfessors.get(1).setOpacity(1);
                case YELLOW -> FXProfessors.get(2).setOpacity(1);
                case PINK -> FXProfessors.get(3).setOpacity(1);
                case BLUE -> FXProfessors.get(4).setOpacity(1);
            }
        }

        List<CreatureColor> missingProfessors = getMissingProfessors(professors);
        for(CreatureColor color : missingProfessors) {
            switch (color) {
                case GREEN -> FXProfessors.get(0).setOpacity(0);
                case RED -> FXProfessors.get(1).setOpacity(0);
                case YELLOW -> FXProfessors.get(2).setOpacity(0);
                case PINK -> FXProfessors.get(3).setOpacity(0);
                case BLUE -> FXProfessors.get(4).setOpacity(0);
            }
        }
    }

    protected void updateTowersBoard(Scene scene, GUI gui, int towers, PlayerColor color) {

        for(int i = 0; i < towers; i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) scene.lookup(towerID);
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByPlayerColor(color));
            tower.setStyle(style);
            tower.setOpacity(1);
        }

        for(int i = towers; i < 8; i++) {
            String towerID = "#tower_" + (i + 1);
            Pane tower = (Pane) scene.lookup(towerID);
            tower.setOpacity(0);
        }
    }

    // UTILITY

    protected Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
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

    private List<CreatureColor> getMissingProfessors(ArrayList<Professor> professors) {
        List<CreatureColor> missing = new ArrayList<>(Arrays.asList(CreatureColor.values()));
        List<CreatureColor> colors = professors.stream().map(Creature::getColor).toList();

        missing.removeAll(colors);

        return missing;
    }

}
