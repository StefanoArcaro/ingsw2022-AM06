package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.gameBoard.Entrance;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

public class Opponent1BoardController extends BoardController implements GUIController{

    GUI gui;

    public Button opponent_professor_green_1;
    public Button opponent_professor_red_1;
    public Button opponent_professor_yellow_1;
    public Button opponent_professor_pink_1;
    public Button opponent_professor_blue_1;

    @FXML
    private GridPane opponent_gridPane_1;

    public Label opponentNickname_1;

    @Override
    public GUI getGUI() {
        return gui;
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


    public void initOpponent(String opponent_1) {
        opponentNickname_1.setText(opponent_1);

        Scene scene = gui.getSceneByName(Constants.OPPONENT_BOARD_1);

        Entrance entrance = gui.getModelView().getBoard(opponent_1).getEntrance();
        initEntrance(gui, scene, entrance);
        initHall(opponent_gridPane_1);

        ArrayList<Button> professors = new ArrayList<>(Arrays.asList(opponent_professor_green_1, opponent_professor_red_1,
                opponent_professor_yellow_1, opponent_professor_pink_1, opponent_professor_blue_1));

        initProfessors(professors);
        initTowers(gui, scene, gui.getModelView(), opponent_1);
    }


    @Override
    public void init() {

    }

    @Override
    public void quit() {

    }
}
