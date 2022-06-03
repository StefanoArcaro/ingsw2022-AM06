package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.Entrance;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class BoardController {

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

}
