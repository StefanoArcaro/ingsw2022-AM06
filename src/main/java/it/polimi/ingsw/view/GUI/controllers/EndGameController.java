package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class EndGameController implements GUIController {

    GUI gui;

    public Label message;
    public Label winner;
    public Label loser_1;
    public Label loser_2;
    public ImageView loser_image_2;
    public Text reason;


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
        ModelView modelView = gui.getModelView();

        String winnerNickname = modelView.getWinner();

        if(winnerNickname.equals(modelView.getNickname())) {
            message.setText("You win!");
        } else {
            message.setText("You lost!");
        }

        winner.setText(winnerNickname);

        ArrayList<String> losers = modelView.getPlayersSorted();
        losers.remove(winnerNickname);

        loser_1.setText(losers.get(0));
        if(losers.size() == 2) {
            loser_image_2.setOpacity(1);
            loser_2.setText(losers.get(1));
        } else {
            loser_image_2.setOpacity(0);
            loser_2.setOpacity(0);
        }

        reason.setText(modelView.getCurrentPhase());
    }

    @Override
    public void quit() {

    }
}
