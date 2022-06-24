package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.message.serverToclient.BoardMessage;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BoardController {

    // INIT

    /**
     * Initializes the entrance.
     * @param gui the GUI the entrance is relative to.
     * @param scene the scene containing the board.
     * @param entrance the entrance to initialize.
     */
    protected void initEntrance(GUI gui, Scene scene, Entrance entrance) {
        for(int i = 0; i < entrance.getStudents().size(); i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            CreatureColor color = entrance.getStudents().get(i).getColor();
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color)) +
                           ";-fx-background-radius: 50";
            entranceButton.setStyle(style);
        }

        for(int i = entrance.getStudents().size(); i < 9; i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            entranceButton.setOpacity(0);
            entranceButton.setDisable(true);
        }
    }

    /**
     * Initializes the hall.
     * @param gridPane the grid containing all the students in the hall.
     */
    protected void initHall(GridPane gridPane) {
        for(Node node : gridPane.getChildren()) {
            node.setOpacity(0);
            node.setDisable(true);
        }
    }

    /**
     * Initializes the professors.
     * @param professors the list of professors.
     */
    protected void initProfessors(ArrayList<Button> professors) {
        for(Button professor : professors) {
            professor.setOpacity(0);
            professor.setDisable(true);
        }
    }

    /**
     * Initializes the towers.
     * @param gui the GUI the towers are relative to.
     * @param scene the scene containing the board.
     * @param modelView a reference to the ModelView.
     * @param playerNickname the nickname of the owner of the towers to initialize.
     */
    protected void initTowers(GUI gui, Scene scene, ModelView modelView, String playerNickname) {
        Board board = modelView.getBoard(playerNickname);

        for(int i = 0; i < board.getTowers(); i++) {
            String towerID = "#tower_" + (i + 1);
            ImageView tower = (ImageView) scene.lookup(towerID);
            PlayerColor color = modelView.getPlayers().get(playerNickname);

            URL url = getClass().getResource(gui.getTowerPathByPlayerColor(color));
            if(url != null) {
                tower.setImage(new Image(url.toString()));
                tower.setOpacity(1);
            }
        }

        for(int i = board.getTowers(); i < 8; i++) {
            String towerID = "#tower_" + (i + 1);
            ImageView tower = (ImageView) scene.lookup(towerID);
            tower.setOpacity(0);
        }
    }

    /**
     * Initializes the opponents board.
     * @param opponentNickname nickname of the opponent to initialize.
     */
    protected void initOpponent(String opponentNickname) {}

    // UPDATE

    /**
     * Updates a board.
     * @param gui the GUI the board is relative to.
     * @param scene the scene containing the board.
     * @param message the BoardMessage containing the updated board information.
     * @param gridPane the hall students' grid.
     * @param FXProfessors the list of all the professors.
     */
    protected void updateBoard(GUI gui, Scene scene, BoardMessage message, GridPane gridPane, ArrayList<Button> FXProfessors) {
        updateEntrance(gui, scene, message.getEntrance());
        updateHall(message.getHall(), gridPane);
        updateProfessors(FXProfessors, message.getProfessors());
        updateTowersBoard(scene, message.getTowers());

    }

    /**
     * Updates an opponent's board.
     * @param message the message containing the updated board information.
     */
    protected void updateOpponent(BoardMessage message) {

    }

    /**
     * Updates the specified entrance.
     * @param gui the GUI the entrance is relative to.
     * @param scene the scene containing the entrance.
     * @param entrance the entrance to update.
     */
    protected void updateEntrance(GUI gui, Scene scene, Entrance entrance) {
        for(int i = 0; i < entrance.getStudents().size(); i++) {
            String entranceID = "#entrance_" + (i + 1);
            Button entranceButton = (Button) scene.lookup(entranceID);
            CreatureColor color = entrance.getStudents().get(i).getColor();
            String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color)) +
                           ";-fx-background-radius: 50";
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

    /**
     * Updates the specified hall.
     * @param hall the hall to update.
     * @param gridPane the specified hall's students' grid.
     */
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

    /**
     * Updates the professors.
     * @param FXProfessors the list of all the professors.
     * @param professors the list of owned professors.
     */
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

    /**
     * Updates the towers of a board.
     * @param scene the scene containing the board.
     * @param towers the number of towers still on the board.
     */
    protected void updateTowersBoard(Scene scene, int towers) {
        for(int i = 0; i < towers; i++) {
            String towerID = "#tower_" + (i + 1);
            ImageView tower = (ImageView) scene.lookup(towerID);
            tower.setOpacity(1);
        }

        for(int i = towers; i < 8; i++) {
            String towerID = "#tower_" + (i + 1);
            ImageView tower = (ImageView) scene.lookup(towerID);
            tower.setOpacity(0);
        }
    }

    // UTILITY

    /**
     * @param row the row of the node to return.
     * @param column the column of the node to return.
     * @param gridPane the grid pane to get the node from.
     * @return the node at the specified row and column indices.
     */
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

    /**
     * @param professors the list of owned professors.
     * @return the list of professors that are not owned.
     */
    private List<CreatureColor> getMissingProfessors(ArrayList<Professor> professors) {
        List<CreatureColor> missing = new ArrayList<>(Arrays.asList(CreatureColor.values()));
        List<CreatureColor> colors = professors.stream().map(Creature::getColor).toList();

        missing.removeAll(colors);

        return missing;
    }

}
