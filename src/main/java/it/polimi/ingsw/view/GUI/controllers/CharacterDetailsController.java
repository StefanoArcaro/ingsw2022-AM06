package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.CharacterView;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CharacterDetailsController implements GUIController {

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
        int id = gui.getCharacterID();
        Scene scene = gui.getSceneByName(Constants.CHARACTER_DETAILS);

        ArrayList<CharacterView> characterViews = gui.getModelView().getCharactersSorted();
        CharacterView characterView = characterViews.get(id - 1);

        String info = Constants.getCharacterInformation(characterView.getCharacterID());
        ArrayList<Student> students = characterView.getStudents();
        int banCards = characterView.getBanCards();

        //todo
    }

    @Override
    public void quit() {

    }
}
