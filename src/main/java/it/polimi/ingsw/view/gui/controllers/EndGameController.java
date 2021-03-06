package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class EndGameController implements GUIController {

    GUI gui;

    public Text message;
    public Text winner;
    public Text loser_1;
    public Text loser_2;
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
            message.setText("YOU WIN!");
        } else {
            message.setText("YOU LOST!");
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
