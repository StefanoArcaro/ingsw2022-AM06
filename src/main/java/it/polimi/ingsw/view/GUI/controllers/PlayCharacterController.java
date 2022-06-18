package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.CharacterView;
import it.polimi.ingsw.view.GUI.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;

public class PlayCharacterController implements GUIController{

    GUI gui;

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
        Scene scene = gui.getSceneByName(Constants.CHARACTERS);

        ArrayList<CharacterView> characters = modelView.getCharactersSorted();

        String character = "#ch_";
        for(int i = 1; i <= 3; i++) {
            ImageView chImage = (ImageView) scene.lookup(character + i);

            URL url = getClass().getResource(gui.getCharacterPathByCharacterID(characters.get(i - 1).getCharacterID()));
            if(url != null) {
                chImage.setImage(new Image(url.toString()));
            }
        }

        String cost = "#cost_";
        for(int i = 1; i <= 3; i++) {
            ImageView imageCost = (ImageView) scene.lookup(cost + i);
            if(characters.get(i - 1).isUsed()) {
                imageCost.setOpacity(1);
            }
        }
    }

    /**
     * Opens the character details scene.
     * @param event the event fired by clicking on one of the available characters.
     */
    public void onOpenCharacterDetails(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        int id = Integer.parseInt(buttonId.substring(11));

        CharacterView characterView = gui.getModelView().getCharactersSorted().get(id - 1);

        gui.setCharacterID(characterView.getCharacterID());

        gui.changeStage(gui.getSecondaryStage(), Constants.CHARACTER_DETAILS);
    }

    @Override
    public void quit() {

    }

}
