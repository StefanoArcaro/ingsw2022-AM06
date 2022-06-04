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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class PlayController extends BoardController implements GUIController {

    private GUI gui;

    public Text name;
    public Label coins;
    public Text numOfCoins;
    public Text currentNickname;
    public Text currentPhase;

    //islands
    public GridPane gridPane_1;
    public GridPane gridPane_2;
    public GridPane gridPane_3;
    public GridPane gridPane_4;
    public GridPane gridPane_5;
    public GridPane gridPane_6;
    public GridPane gridPane_7;
    public GridPane gridPane_8;
    public GridPane gridPane_9;
    public GridPane gridPane_10;
    public GridPane gridPane_11;
    public GridPane gridPane_12;

    // clouds
    public Button cloud_1;
    public Button cloud_2;
    public Button cloud_3;

    // hall
    public GridPane gridPane;

    // client's professors
    public Button professor_green;
    public Button professor_red;
    public Button professor_yellow;
    public Button professor_pink;
    public Button professor_blue;

    private final Map<String, String> nicknameToSceneName = new HashMap<>(); //todo opponents?

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public GUI getGUI() {
        return gui;
    }

    // INIT

    public void init() {
        ModelView modelView = gui.getModelView();
        String playerNickname = modelView.getNickname();
        Board board = modelView.getBoard(playerNickname);
        Entrance entrance = board.getEntrance();
        ArrayList<Button> professors = new ArrayList<>(Arrays.asList(professor_green, professor_red, professor_yellow, professor_pink, professor_blue));
        ArrayList<IslandGroup> islands = modelView.getIslandGroups();
        String phase = modelView.getCurrentPhase();
        String player = modelView.getCurrentPlayer();

        initInfo(modelView, playerNickname, player, phase);

        initEntrance(gui, gui.getCurrentScene(), entrance);
        initHall(gridPane);
        initProfessors(professors);
        initTowers(gui, gui.getCurrentScene(), modelView, playerNickname);

        initIslands(gui.getCurrentScene(), islands);

        initClouds(gui.getCurrentScene());
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

    private void initIslands(Scene scene, ArrayList<IslandGroup> islandGroups) {
        int islandGroupIndex = 0;

        for(IslandGroup islandGroup : islandGroups) {
            islandGroupIndex += 1;

            for(Island island : islandGroup.getIslands()) {
                int islandID = island.getIslandID();
                GridPane islandGridPane = getIslandGridPaneByIslandID(islandID);

                // set students
                ArrayList<Student> students = island.getStudents();
                if(students.size() > 0) {
                    int indexColor = students.get(0).getColor().getIndex();
                    Node node = getNodeByRowColumnIndex(indexColor, 0, islandGridPane);
                    if(node != null) {
                        ((Text)node).setText("1");
                    }
                }

                // set mother nature
                updateMotherNature(scene, islandGroupIndex);

                // set tower
                String t = "#t_" + islandGroupIndex;
                ImageView tImage = (ImageView) scene.lookup(t);

                tImage.setOpacity(0);
            }
        }
    }

    private void initClouds(Scene scene) {
        for(int i = 1; i <= 3; i++) {
            String student = "#cloud" + i + "_s";

            for(int j = 1; j <= 4; j++) {
                Button buttonStudent = (Button)scene.lookup(student + j);
                buttonStudent.setOpacity(0);
                buttonStudent.setDisable(true);
            }
        }

        cloud_1.setOpacity(0);
        cloud_1.setDisable(true);
        cloud_2.setOpacity(0);
        cloud_2.setDisable(true);
        cloud_3.setOpacity(0);
        cloud_3.setDisable(true);
    }


    // UPDATES

    public void updateCurrentPlayer(String player) {
        currentNickname.setText(player);
    }

    public void updateCurrentPhase(String phase) {
        currentPhase.setText(phase);
    }

    public void updateCoins(int coins) {
        numOfCoins.setText(Integer.toString(coins));
    }

    public void updateIslandGroups(Scene scene, ArrayList<IslandGroup> islandGroups) {
        int islandGroupIndex = 0;

        for(IslandGroup islandGroup : islandGroups) {
            islandGroupIndex += 1;

            for(Island island : islandGroup.getIslands()) {
                updateIsland(scene, island);
            }

            // set mother nature
            updateMotherNature(scene, islandGroupIndex);
        }

        // todo union
    }

    public void updateIsland(Scene scene, Island island) {
        Map<CreatureColor, Integer> islandStudents = new HashMap<>();
        ArrayList<Student> students = island.getStudents();

        if(students != null) {
            List<CreatureColor> colors = students.stream().map(Student::getColor).toList();

            for (CreatureColor color : CreatureColor.values()) {
                islandStudents.put(color, 0);
            }

            for (CreatureColor color : colors) {
                int number = islandStudents.get(color);
                islandStudents.replace(color, number + 1);
            }

            for (int i = 0; i <= 4; i++) {
                int colorStudents = islandStudents.get(CreatureColor.getColorByIndex(i));
                GridPane islandGridPane = getIslandGridPaneByIslandID(island.getIslandID());
                Text node = (Text) getNodeByRowColumnIndex(i, 0, islandGridPane);
                if (node != null) {
                    node.setText(String.valueOf(colorStudents));
                }
            }
        }

        PlayerColor tower = island.getTower();
        String t = "#t_" + island.getIslandID();
        ImageView tImage = (ImageView) scene.lookup(t);

        if(tower != null) {
            URL url = getClass().getResource(getTowerPathByColor(tower));
            if(url != null) {
                tImage.setImage(new Image(url.toString()));
                tImage.setOpacity(1);
            }
        } else {
            tImage.setOpacity(0);
        }

    }

    private void updateMotherNature(Scene scene, int islandGroupIndex) {
        String mn = "#mn_" + islandGroupIndex;
        ImageView mnImage = (ImageView) scene.lookup(mn);

        int motherNatureIndex = gui.getModelView().getMotherNatureIndex();
        if(motherNatureIndex == islandGroupIndex - 1) {
            mnImage.setOpacity(1);
        } else {
            mnImage.setOpacity(0);
        }
    }

    public void updateClouds(Scene scene, ArrayList<Cloud> clouds) {
        int i;

        for(Cloud cloud : clouds) {
            Button cloudButton = getCloudButtonByID(cloud.getCloudID());
            if(cloud.isEmpty()) {
                cloudButton.setOpacity(0.3);
                cloudButton.setDisable(true);

                int numberOfStudents = gui.getModelView().getNumberOfPlayers() + 1;
                String student = "#cloud" + cloud.getCloudID() + "_s";

                for(i = 1; i <= numberOfStudents; i++) {
                    Button buttonStudent = (Button)scene.lookup(student + i);
                    buttonStudent.setOpacity(0);
                    buttonStudent.setDisable(true);
                }

            } else {
                cloudButton.setOpacity(0.3);
                cloudButton.setDisable(false);

                int numberOfStudents = cloud.getStudents().size();
                String student = "#cloud" + cloud.getCloudID() + "_s";

                for(i = 1; i <= numberOfStudents; i++) {
                    Button buttonStudent = (Button)scene.lookup(student + i);
                    CreatureColor color = cloud.getStudents().get(i - 1).getColor();
                    String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color));
                    buttonStudent.setOpacity(1);
                    buttonStudent.setDisable(false);
                    buttonStudent.setStyle(style);
                }

            }
        }
    }

    //todo: modify opponent board (nicknameBoard)
    public void updateBoard(BoardMessage msg) {
        ModelView modelView = gui.getModelView();
        String nicknameBoard = msg.getNickname();
        Entrance entrance = msg.getEntrance();
        Hall hall = msg.getHall();
        ArrayList< Professor > professors = msg.getProfessors();
        int towers = msg.getTowers();

        if(nicknameBoard.equals(modelView.getNickname())) {
            //modify main scene
            Scene scene = gui.getSceneByName(Constants.BOARD_AND_ISLANDS);
            updateEntrance(scene, entrance);
            updateHall(hall);
            updateProfessors(professors);
            updateTowersBoard(towers, modelView.getPlayers().get(modelView.getNickname()));
        } else {
            //todo: modify opponent board (nicknameBoard)
        }

    }

    private void updateEntrance(Scene scene, Entrance entrance) {

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

    private void updateHall(Hall hall) {
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

            //todo check
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

        List<CreatureColor> missingProfessors = getMissingProfessors(professors);
        for(CreatureColor color : missingProfessors) {
            switch (color) {
                case GREEN -> professor_green.setOpacity(0);
                case RED -> professor_red.setOpacity(0);
                case YELLOW -> professor_yellow.setOpacity(0);
                case PINK -> professor_pink.setOpacity(0);
                case BLUE -> professor_blue.setOpacity(0);
            }
        }
    }

    private void updateTowersBoard(int towers, PlayerColor color) {
        Scene scene = gui.getSceneByName(Constants.BOARD_AND_ISLANDS);

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


    // ACTIONS

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

    public void onOpenPlayAssistants() {
        gui.createWindow(Constants.ASSISTANTS);
    }

    public void onMoveStudentToHall() {
        CreatureColor entranceColor = gui.getEntranceColor();

        if(entranceColor != null) {
            String message = "MOVESTUDENT " + entranceColor.getColorName() + " 0";
            gui.getMessageParser().parseInput(message);
            gui.setEntranceColor(null);
            gui.setDestinationIsland(0);
        } else {
            Platform.runLater(() -> AlertBox.display("Wrong move", "Please select a student from your entrance."));
        }
    }

    public void onIslandClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        String island = button.getId();
        int islandID = Integer.parseInt(island.substring(7));

        gui.setDestinationIsland(islandID);
    }

    public void onMoveStudentToIsland() {
        if(gui.getEntranceColor() != null) {
            if(gui.getDestinationIsland() > 0) {
                String message = "MOVESTUDENT " + gui.getEntranceColor().getColorName() + " " + gui.getDestinationIsland();
                gui.getMessageParser().parseInput(message);
                gui.setEntranceColor(null);
                gui.setDestinationIsland(0);
            } else {
                Platform.runLater(() -> AlertBox.display("Wrong move", "Please choose an island."));
            }
        } else {
            Platform.runLater(() -> AlertBox.display("Wrong move", "Please choose a student to move."));
        }
    }

    public void onShowOpponents() {
        List<String> opponents = gui.getModelView().getOpponents();
        //todo map ? nickname - scene

        gui.createWindow(Constants.OPPONENT_BOARD_1);
        ((Opponent1BoardController)gui.getNameToController().get(Constants.OPPONENT_BOARD_1)).initOpponent(opponents.get(0));

        if(opponents.size() == 2) {
            gui.createWindow(Constants.OPPONENT_BOARD_2);
            ((Opponent2BoardController)gui.getNameToController().get(Constants.OPPONENT_BOARD_2)).initOpponent(opponents.get(1));
        }
    }

    public void onOpenMotherNature() {
        gui.createWindow(Constants.MOTHER_NATURE);
    }

    public void onPickCloud(ActionEvent event) {
        Button cloud = (Button) event.getSource();
        String cloudID = cloud.getId().substring(6);

        String message = "PICKCLOUD " + cloudID;
        gui.getMessageParser().parseInput(message);
    }

    public void onOpenCharactersInfo(ActionEvent event) {
    }

    public void onOpenPlayCharacter(ActionEvent event) {
    }


    // UTILITY

    private GridPane getIslandGridPaneByIslandID(int islandID) {
        return switch (islandID) {
            case 1 -> gridPane_1;
            case 2 -> gridPane_2;
            case 3 -> gridPane_3;
            case 4 -> gridPane_4;
            case 5 -> gridPane_5;
            case 6 -> gridPane_6;
            case 7 -> gridPane_7;
            case 8 -> gridPane_8;
            case 9 -> gridPane_9;
            case 10 -> gridPane_10;
            case 11 -> gridPane_11;
            case 12 -> gridPane_12;
            default -> null;
        };
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

    private List<CreatureColor> getMissingProfessors(ArrayList<Professor> professors) {
        List<CreatureColor> missing = new ArrayList<>(Arrays.asList(CreatureColor.values()));
        List<CreatureColor> colors = professors.stream().map(Creature::getColor).toList();

        missing.removeAll(colors);

        return missing;
    }

    private Button getCloudButtonByID(int id) {
        return switch (id) {
            case 1 -> cloud_1;
            case 2 -> cloud_2;
            case 3 -> cloud_3;
            default -> null;
        };
    }

    private CreatureColor getButtonColor(Button button) {
        Color color = (Color) button.getBackground().getFills().get(0).getFill();

        return gui.getCreatureColorByFXColor(color);
    }

    private String getTowerPathByColor(PlayerColor color) {
        return switch (color) {
            case BLACK -> "/images/tower_black.png";
            case WHITE -> "/images/tower_white.png";
            case GRAY -> "/images/tower_gray.png";
        };
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getStage(),"Are you sure you want to quit?"));

        String message = "QUIT";
        gui.getMessageParser().parseInput(message);
    }

}
