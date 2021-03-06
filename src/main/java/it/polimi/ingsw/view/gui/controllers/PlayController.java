package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.message.serverToclient.BoardMessage;
import it.polimi.ingsw.network.message.serverToclient.CoinMessage;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.gui.AlertBox;
import it.polimi.ingsw.view.gui.ConfirmationBox;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.ModelView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class PlayController extends BoardController implements GUIController {

    private GUI gui;

    public Text name;
    public Label coins;
    public Text numOfCoins;
    public Text currentNickname;
    public Text currentPhase;

    // actions
    public Button assistantAction;
    public Button hallAction;
    public Button islandAction;
    public Button motherNatureAction;
    public Button charactersAction;

    // islands
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

    public ImageView cloud_image_3;

    // hall
    public GridPane gridPane;

    // client's professors
    public Button professor_green;
    public Button professor_red;
    public Button professor_yellow;
    public Button professor_pink;
    public Button professor_blue;


    @Override
    public GUI getGUI() {
        return gui;
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    // INIT

    /**
     * Initializes the scene.
     */
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

        // opponents
        initOpponents();
    }

    /**
     * Initializes the scenes' textual information.
     * @param modelView a reference to the ModelView.
     * @param playerNickname the nickname of the client.
     * @param player the current player' nickname.
     * @param phase the current phase's name.
     */
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

    /**
     * Initializes the islands.
     * @param scene the scene containing the islands.
     * @param islandGroups the list of island groups.
     */
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

                // set tower
                String t = "#t_" + islandGroupIndex;
                ImageView tImage = (ImageView) scene.lookup(t);

                tImage.setOpacity(0);
            }

            // set mother nature
            List<Integer> islandsID = islandGroup.getIslands().stream().map(Island::getIslandID).toList();
            updateMotherNature(scene, islandGroupIndex, islandsID);
        }
    }

    /**
     * Initializes the clouds.
     * @param scene the scene containing the clouds.
     */
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

        if(gui.getModelView().getNumberOfPlayers() == 2) {
            cloud_image_3.setOpacity(0);
        }
    }

    /**
     * Initializes the opponents' boards in their own scenes.
     */
    private void initOpponents() {
        for(String name : gui.getNicknameToSceneName().keySet()) {
            String sceneName = gui.getNicknameToSceneName().get(name);
            BoardController controller = (BoardController)gui.getNameToController().get(sceneName);
            controller.initOpponent(name);
        }
    }


    // UPDATES

    /**
     * Updates the current player.
     * @param player to set the Text's content to.
     */
    public void updateCurrentPlayer(String player) {
        currentNickname.setText(player);
    }

    /**
     * Updates the current phase.
     * @param phase to set the Text's content to.
     */
    public void updateCurrentPhase(String phase) {
        currentPhase.setText(phase);

        switch (phase) {
            case "Planning phase" -> updateActionButtons(new ArrayList<>(Arrays.asList(false, true, true, true, false)));
            case "Move student phase" -> updateActionButtons(new ArrayList<>(Arrays.asList(true, false, false, true, false)));
            case "Move mother nature phase" -> updateActionButtons(new ArrayList<>(Arrays.asList(true, true, true, false, false)));
            case "Pick cloud phase" -> updateActionButtons(new ArrayList<>(Arrays.asList(true, true, true, true, false)));
        }
    }

    /**
     * Updates the coins owned by the client.
     * @param msg the CoinMessage sent by the server.
     */
    public void updateCoins(CoinMessage msg) {
        if(msg.getNickname().equals(gui.getModelView().getNickname())) {
            numOfCoins.setText(Integer.toString(msg.getCoins()));
        }
    }

    /**
     * Updates the island groups by connecting the islands in the
     * same island group.
     * @param scene the scene containing the islands.
     * @param islandGroups the list of island groups.
     */
    public void updateIslandGroups(Scene scene, ArrayList<IslandGroup> islandGroups) {
        int islandGroupIndex = 0;

        for(IslandGroup islandGroup : islandGroups) {
            islandGroupIndex += 1;

            ArrayList<Island> islands = islandGroup.getIslands();
            List<Integer> islandsID = islands.stream().map(Island::getIslandID).toList();

            for(Island island : islands) {
                updateIsland(scene, island);

                // Union
                boolean unionCondition = islandsID.contains((island.getIslandID() % 12) + 1);
                if(unionCondition) {
                    ImageView arrow = (ImageView) scene.lookup("#link" + island.getIslandID());
                    arrow.setOpacity(1);
                }

                // Set ban card
                ImageView banCard = (ImageView) scene.lookup("#bancard_" + island.getIslandID());
                if(islandGroup.getNumberOfBanCardPresent() > 0) {
                    banCard.setOpacity(1);
                } else {
                    banCard.setOpacity(0);
                }
            }

            // Set mother nature
            updateMotherNature(scene, islandGroupIndex, islandsID);
        }
    }

    /**
     * Update the specified island.
     * @param scene the scene containing the island.
     * @param island the island to update.
     */
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
            URL url = getClass().getResource(gui.getTowerPathByPlayerColor(tower));
            if(url != null) {
                tImage.setImage(new Image(url.toString()));
                tImage.setOpacity(1);
            }
        } else {
            tImage.setOpacity(0);
        }

    }

    /**
     * Updates Mother Nature's position.
     * @param scene the scene containing Mother Nature.
     * @param islandGroupIndex the index of the island group Mother Nature ended up at.
     * @param islandsID the list of IDs of the islands that belong to the specified island group.
     */
    private void updateMotherNature(Scene scene, int islandGroupIndex, List<Integer> islandsID) {
        List<Integer> IDs = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        String mn = "#mn_";

        int motherNatureIndex = gui.getModelView().getMotherNatureIndex();
        if(motherNatureIndex == islandGroupIndex - 1) {
            for(int id : IDs) {
                ImageView mnImage = (ImageView) scene.lookup(mn + id);

                if(islandsID.contains(id)) {
                    mnImage.setOpacity(1);
                } else {
                    mnImage.setOpacity(0);
                }
            }
        }
    }

    /**
     * Update the clouds.
     * @param scene the scene containing the clouds.
     * @param clouds the list of clouds to update.
     */
    public void updateClouds(Scene scene, ArrayList<Cloud> clouds) {
        int i;

        for(Cloud cloud : clouds) {
            Button cloudButton = getCloudButtonByID(cloud.getCloudID());

            if(cloud.isEmpty()) {
                cloudButton.setDisable(true);

                int numberOfStudents = gui.getModelView().getNumberOfPlayers() + 1;
                String student = "#cloud" + cloud.getCloudID() + "_s";

                for(i = 1; i <= numberOfStudents; i++) {
                    Button buttonStudent = (Button)scene.lookup(student + i);

                    buttonStudent.setOpacity(0);
                    buttonStudent.setDisable(true);
                }
            } else {
                cloudButton.setDisable(false);

                int numberOfStudents = cloud.getStudents().size();
                String student = "#cloud" + cloud.getCloudID() + "_s";

                for(i = 1; i <= numberOfStudents; i++) {
                    Button buttonStudent = (Button)scene.lookup(student + i);

                    CreatureColor color = cloud.getStudents().get(i - 1).getColor();
                    String style = "-fx-background-color: " + gui.getHexByFXColor(gui.getFXColorByCreatureColor(color)) +
                                   ";-fx-background-radius: 50";
                    buttonStudent.setOpacity(1);
                    buttonStudent.setDisable(false);
                    buttonStudent.setStyle(style);
                }
            }
        }
    }

    /**
     * Update the board of the client or an opponent's.
     * @param msg the BoardMessage, that also specifies which board to update.
     */
    public void updateBoards(BoardMessage msg) {
        ModelView modelView = gui.getModelView();
        String nicknameBoard = msg.getNickname();
        ArrayList<Button> FXProfessors = new ArrayList<>(Arrays.asList(professor_green, professor_red,
                                                    professor_yellow, professor_pink, professor_blue));

        if(nicknameBoard.equals(modelView.getNickname())) {
            Scene scene = gui.getSceneByName(Constants.BOARD_AND_ISLANDS);
            updateBoard(gui, scene, msg, gridPane, FXProfessors);
        } else {
            String sceneName = gui.getNicknameToSceneName().get(nicknameBoard);
            GUIController controller = gui.getNameToController().get(sceneName);
            ((BoardController) controller).updateOpponent(msg);
        }
    }

    /**
     * Update the action buttons by enabling or disabling them based on the specified list.
     * @param actions a list of boolean values that specifies which action buttons to enable/disable.
     */
    private void updateActionButtons(ArrayList<Boolean> actions) {
        assistantAction.setDisable(actions.get(0));
        hallAction.setDisable(actions.get(1));
        islandAction.setDisable(actions.get(2));
        motherNatureAction.setDisable(actions.get(3));
        charactersAction.setDisable(actions.get(4));

        if(gui.getModelView().getGameMode().equals(GameMode.EASY)) {
            charactersAction.setDisable(true);
        }
    }


    // ACTIONS

    /**
     * Sets the selected entrance color in the GUI.
     * @param event the event fired by an entrance student button.
     */
    public void onEntranceClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        CreatureColor color = gui.getButtonColor(button);

        gui.setEntranceColor(color);
    }

    /**
     * Sets the selected hall color in the GUI.
     * @param event the event fired by a hall button.
     */
    public void onHallClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        CreatureColor color = gui.getButtonColor(button);

        gui.setHallColor(color);
    }

    /**
     * Sets the selected destination island in the GUI.
     * @param event the event fired by an island button.
     */
    public void onIslandClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        String island = button.getId();
        int islandID = Integer.parseInt(island.substring(7));

        gui.setDestinationIsland(islandID);
    }

    /**
     * Creates a PickCloudMessage to send to the server.
     * @param event the event fired by a cloud button.
     */
    public void onPickCloud(ActionEvent event) {
        Button cloud = (Button) event.getSource();
        String cloudID = cloud.getId().substring(6);

        String message = "PICKCLOUD " + cloudID;
        gui.getMessageParser().parseInput(message);
    }


    /**
     * Displays the opponents' boards in a new window (the GUI's secondary stage).
     */
    public void onShowOpponents() {
        for(String name : gui.getNicknameToSceneName().keySet()) {
            gui.createWindow(gui.getNicknameToSceneName().get(name));
        }
    }

    /**
     * Displays the assistants' scene in a new window (the GUI's secondary stage).
     */
    public void onOpenPlayAssistants() {
        gui.createWindow(Constants.ASSISTANTS);
    }

    /**
     * Creates the MoveStudentMessage to send to the server (student goes to hall).
     */
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

    /**
     * Creates the MoveStudentMessage to send to the server (student goes to the selected island).
     */
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

    /**
     * Displays the Mother Nature scene in a new window (the GUI's secondary stage).
     */
    public void onOpenMotherNature() {
        gui.createWindow(Constants.MOTHER_NATURE);
    }

    /**
     * Displays the characters' scene in a new window (the GUI's secondary stage).
     */
    public void onOpenCharacters() {
        gui.createWindow(Constants.CHARACTERS);
    }

    // UTILITY

    /**
     * @param islandID the ID of the island to return the students' grid of.
     * @return the island's students' grid pane.
     */
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

    /**
     * @param id the ID of the cloud to return the button of.
     * @return the cloud's button.
     */
    private Button getCloudButtonByID(int id) {
        return switch (id) {
            case 1 -> cloud_1;
            case 2 -> cloud_2;
            case 3 -> cloud_3;
            default -> null;
        };
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(1, gui.getPrimaryStage(),"Are you sure you want to quit?"));

        String message = "QUIT";
        gui.getMessageParser().parseInput(message);
    }

}
