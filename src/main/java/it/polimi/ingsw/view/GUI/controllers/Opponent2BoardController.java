package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.gameBoard.Entrance;
import it.polimi.ingsw.network.message.serverToclient.BoardMessage;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

public class Opponent2BoardController extends BoardController implements GUIController {

    GUI gui;

    public Button opponent_professor_green_2;
    public Button opponent_professor_red_2;
    public Button opponent_professor_yellow_2;
    public Button opponent_professor_pink_2;
    public Button opponent_professor_blue_2;

    public GridPane opponent_gridPane_2;

    public Label opponentNickname_2;

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

    @Override
    public void initOpponent(String opponent_2) {
        opponentNickname_2.setText(opponent_2);

        // init opponent 1
        Scene scene = gui.getSceneByName(Constants.OPPONENT_BOARD_2);

        Entrance entrance = gui.getModelView().getBoard(opponent_2).getEntrance();
        initEntrance(gui, scene, entrance);
        initHall(opponent_gridPane_2);

        ArrayList<Button> professors = new ArrayList<>(Arrays.asList(opponent_professor_green_2, opponent_professor_red_2,
                opponent_professor_yellow_2, opponent_professor_pink_2, opponent_professor_blue_2));

        initProfessors(professors);
        initTowers(gui, scene, gui.getModelView(), opponent_2);
    }

    @Override
    public void updateOpponent(BoardMessage message) {
        Scene scene = gui.getSceneByName(Constants.OPPONENT_BOARD_2);
        ArrayList<Button> FXProfessors = new ArrayList<>(Arrays.asList(opponent_professor_green_2, opponent_professor_red_2,
                opponent_professor_yellow_2, opponent_professor_pink_2, opponent_professor_blue_2));

        updateBoard(gui, scene, message, opponent_gridPane_2, FXProfessors);
    }

    @Override
    public void quit() {

    }

}
