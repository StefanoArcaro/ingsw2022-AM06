package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.controllers.*;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends Application implements View {

    private SocketClient socketClient;
    private ModelView modelView;
    private MessageParser messageParser;

    private Stage primaryStage;
    private Stage secondaryStage;
    private Scene currentScene;
    private final HashMap<String, Scene> nameToScene = new HashMap<>();
    private final HashMap<String, GUIController> nameToController = new HashMap<>();
    private final Map<String, String> nicknameToSceneName = new HashMap<>();

    private final Map<Color, CreatureColor> colorToCreature = new HashMap<>();
    private final Map<Color, String> colorToHex = new HashMap<>();

    private CreatureColor entranceColor;
    private CreatureColor hallColor;
    private int destinationIsland;
    private int characterID;
    private CreatureColor characterStudent;
    private CreatureColor characterColor;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        setup();
        this.primaryStage = stage;
        stage.setScene(currentScene);

        this.primaryStage.setOnCloseRequest(e -> {
            e.consume();
            int exitStatus = ConfirmationBox.display(1, this.primaryStage, "Are you sure you want to quit?");
            if(exitStatus == 1) {
                System.exit(0);
            }
        });
        stage.show();
    }

    private void setup() {
        this.modelView = new ModelView();
        initMaps();
        List<String> fxmlList = Constants.SCENES;
        try {
            for (String path : fxmlList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                Scene scene = new Scene(loader.load());
                nameToScene.put(path, scene);
                if(path.equals(Constants.MENU)) {
                    this.currentScene = scene;
                }
                GUIController controller = loader.getController();
                if(controller != null) {
                    controller.setGUI(this);
                    nameToController.put(path, controller);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public SocketClient getSocketClient() {
        return this.socketClient;
    }

    @Override
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        socketClient.readMessage();
        socketClient.enablePinger(true);
    }

    public ModelView getModelView() {
        return modelView;
    }

    public MessageParser getMessageParser() {
        return messageParser;
    }

    public void setMessageParser(SocketClient socketClient) {
        this.messageParser = new MessageParser(socketClient);
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getSecondaryStage() {
        return secondaryStage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Scene getSceneByName(String name) {
        return nameToScene.get(name);
    }

    public HashMap<String, GUIController> getNameToController() {
        return nameToController;
    }

    public Scene getSceneByController(GUIController controller) {
        for(String scene : nameToController.keySet()) {
            if(nameToController.get(scene).equals(controller)) {
                return nameToScene.get(scene);
            }
        }
        return null;
    }

    public Map<String, String> getNicknameToSceneName() {
        return nicknameToSceneName;
    }

    public void changeStage(Stage stage, String scene) {
        Platform.runLater(() -> {
            currentScene = nameToScene.get(scene);
            stage.setScene(currentScene);
            stage.show();
            GUIController controller = nameToController.get(scene);
            if(controller != null) {
                nameToController.get(scene).init();
            }
        });
    }

    public void createWindow(String sceneName) {
        try {
            secondaryStage = new Stage();

            GUIController controller = getNameToController().get(sceneName);
            controller.init();
            Scene scene = getSceneByController(controller);

            secondaryStage.setScene(scene);
            secondaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initMaps() {
        // Creature color map
        colorToCreature.put(Color.GREEN, CreatureColor.GREEN);
        colorToCreature.put(Color.RED, CreatureColor.RED);
        colorToCreature.put(Color.GOLD, CreatureColor.YELLOW);
        colorToCreature.put(Color.DEEPPINK, CreatureColor.PINK);
        colorToCreature.put(Color.BLUE, CreatureColor.BLUE);

        // Color to Hex map
        colorToHex.put(Color.GREEN, "#008000");
        colorToHex.put(Color.RED, "#ff0000");
        colorToHex.put(Color.GOLD, "#ffd700");
        colorToHex.put(Color.DEEPPINK, "#ff1493");
        colorToHex.put(Color.BLUE, "#0000ff");

        colorToHex.put(Color.BLACK, "#000000");
        colorToHex.put(Color.WHITE, "#ffffff");
        colorToHex.put(Color.GRAY, "#808080");
    }

    public CreatureColor getCreatureColorByFXColor(Color color) {
        return colorToCreature.get(color);
    }

    public Color getFXColorByCreatureColor(CreatureColor creatureColor) {
        for(Color color : colorToCreature.keySet()) {
            if(colorToCreature.get(color).equals(creatureColor)) {
                return color;
            }
        }
        return null;
    }

    public String getTowerPathByPlayerColor(PlayerColor color) {
        return switch (color) {
            case BLACK -> "/images/tower_black.png";
            case WHITE -> "/images/tower_white.png";
            case GRAY -> "/images/tower_gray.png";
        };
    }

    public String getHexByFXColor(Color color) {
        return colorToHex.get(color);
    }

    public CreatureColor getButtonColor(Button button) {
        Color color = (Color) button.getBackground().getFills().get(0).getFill();

        return getCreatureColorByFXColor(color);
    }

    public String getCharacterPathByCharacterID(int characterID) {
        return "/images/character_" + characterID + ".png";
    }



    public CreatureColor getEntranceColor() {
        return entranceColor;
    }

    public void setEntranceColor(CreatureColor entranceColor) {
        this.entranceColor = entranceColor;
    }

    public CreatureColor getHallColor() {
        return hallColor;
    }

    public void setHallColor(CreatureColor hallColor) {
        this.hallColor = hallColor;
    }

    public int getDestinationIsland() {
        return destinationIsland;
    }

    public void setDestinationIsland(int destinationIsland) {
        this.destinationIsland = destinationIsland;
    }

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public CreatureColor getCharacterStudent() {
        return characterStudent;
    }

    public void setCharacterStudent(CreatureColor characterStudent) {
        this.characterStudent = characterStudent;
    }

    public CreatureColor getCharacterColor() {
        return characterColor;
    }

    public void setCharacterColor(CreatureColor characterColor) {
        this.characterColor = characterColor;
    }

    // HANDLERS

    /**
     * Handles the LoginReplyMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void loginReplyHandler(LoginReplyMessage msg) {
        modelView.setNickname(msg.getNickname());
        modelView.setNumberOfPlayers(msg.getNumberOfPlayers().getNum());
        modelView.setGameMode(msg.getGameMode());

        changeStage(primaryStage, Constants.LOBBY);
    }

    /**
     * Handles the GameStartedMessage sent by the server.
     */
    @Override
    public void gameStartingHandler() {
        changeStage(primaryStage, Constants.WIZARD);
    }

    /**
     * Handles the GameReadyMessage sent by the server.
     */
    @Override
    public void gameReadyHandler() {
        changeStage(primaryStage, Constants.BOARD_AND_ISLANDS);
    }

    /**
     * Handles the WizardsAvailableMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void wizardsHandler(WizardsAvailableMessage msg) {
        WizardController controller = (WizardController) nameToController.get(Constants.WIZARD);
        controller.updateAvailableWizards(msg.getWizards().get(0));
    }

    /**
     * Handles the FXWizardChoiceMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void fxWizardsHandler(FXWizardChoiceMessage msg) {
        WizardController controller = (WizardController) nameToController.get(Constants.WIZARD);
        controller.updateWizardChoice(msg.getWizardName());
    }

    /**
     * Handles the AssistantsMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void assistantsHandler(AssistantsMessage msg) {
        PlayAssistantController controller = (PlayAssistantController) nameToController.get(Constants.ASSISTANTS);
        if(msg.getAssistants().size() > 1) {
            controller.updateAvailableAssistants(msg.getAssistants());
        }
    }

    /**
     * Handles the ActivePlayersMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void activePlayersHandler(ActivePlayersMessage msg) {
        modelView.setPlayers(msg.getActivePlayers());

        List<String> opponents = modelView.getOpponents();
        nicknameToSceneName.put(opponents.get(0), Constants.OPPONENT_BOARD_1);
        if(opponents.size() == 2) {
            nicknameToSceneName.put(opponents.get(1), Constants.OPPONENT_BOARD_2);
        }
    }

    /**
     * Handles the BoardMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void boardHandler(BoardMessage msg) {
        modelView.setBoard(msg);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateBoards(msg);
    }

    /**
     * Handles the IslandGroupsMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void islandGroupsHandler(IslandGroupsMessage msg) {
        modelView.setIslandGroups(msg.getIslandGroups(), msg.getMotherNatureIndex());
        Scene scene = getSceneByName(Constants.BOARD_AND_ISLANDS);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateIslandGroups(scene, msg.getIslandGroups());
    }

    /**
     * Handles the IslandMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void islandHandler(IslandMessage msg) {
        modelView.setIsland(msg.getIsland());
        Scene scene = getSceneByName(Constants.BOARD_AND_ISLANDS);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateIsland(scene, msg.getIsland());
    }

    /**
     * Handles the CloudsAvailableMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void cloudsAvailableHandler(CloudsAvailableMessage msg) {

    }

    /**
     * Handles the CloudChosenMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void cloudChosenHandler(CloudChosenMessage msg) {

    }

    /**
     * Handles the FXCloudMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void fxCloudsHandler(FXCloudMessage msg) {
        modelView.setClouds(msg.getClouds());
        Scene scene = getSceneByName(Constants.BOARD_AND_ISLANDS);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateClouds(scene, msg.getClouds());
    }

    /**
     * Handles the CoinMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void coinsHandler(CoinMessage msg) {
        modelView.setCoins(msg);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCoins(msg);
    }

    /**
     * Handles the CurrentPlayerMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void currentPlayerHandler(CurrentPlayerMessage msg) {
        String currentPlayer = msg.getCurrentPlayer();
        modelView.setCurrentPlayer(currentPlayer);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCurrentPlayer(currentPlayer);
    }

    /**
     * Handles the CurrentPhaseMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void currentPhaseHandler(CurrentPhaseMessage msg) {
        String currentPhase = msg.getCurrentPhase();
        modelView.setCurrentPhase(currentPhase);
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCurrentPhase(currentPhase);
    }

    /**
     * Handles the CharacterDrawnMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void charactersDrawnHandler(CharacterDrawnMessage msg) {
        modelView.setDrawnCharacter(msg);
    }

    /**
     * Handles the CharacterInfoMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void characterInfoHandler(CharacterInfoMessage msg) {
    }

    /**
     * Handles the CharacterPlayedMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void characterPlayedHandler(CharacterPlayedMessage msg) {
        modelView.setPlayedCharacter(msg.getCharacterID(), msg.getCost(), msg.isUsed(), msg.getStudents(), msg.getBanCards());
    }

    /**
     * Handles the FXBanCardMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void banCardHandler(FXBanCardMessage msg) {
        modelView.setPlayedCharacter(msg.getCharacterID(), msg.getCost(), msg.isUsed(), msg.getStudents(), msg.getBanCards());
    }

    /**
     * Displays the match's current state.
     */
    @Override
    public void matchInfoHandler() {
    }

    /**
     * Handles the WinnerMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void winnerHandler(WinnerMessage msg) {
        modelView.setWinner(msg.getWinnerNickname());
        changeStage(primaryStage, Constants.ENDGAME);
    }

    /**
     * Handles the LoserMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void loserHandler(LoserMessage msg) {
        modelView.setWinner(msg.getWinnerNickname());
        changeStage(primaryStage, Constants.ENDGAME);
    }

    /**
     * Handles the end of the game
     */
    @Override
    public void gameEndedHandler() {
    }

    /**
     * Handles the GenericMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void genericMessageHandler(GenericMessage msg) {
        String[] message = msg.getMessage().split("_");

        if(message.length == 3) {
            sendGeneric(message[0], message[2]);
        } else {
            sendGeneric(message[0], null);
        }
    }

    private void sendGeneric(String message, String option) {
        switch (message) {
            case "PLAYER" -> ((PlayController) (nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCurrentPlayer(getModelView().getNickname());
            case "PHASE" -> {
                modelView.setCurrentPhase(option);
                ((PlayController) (nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCurrentPhase(option);
            }
            case "ASSISTANTPLAYED" -> {
                String[] split = option.split(" ");
                String priority = split[0];
                String steps = split[1];
                String msg = modelView.getCurrentPlayer() + " played the assistant with PRIORITY = " + priority + " and NUMBER OF STEPS = " + steps;
                Platform.runLater(() -> AlertBox.display("Message", msg));
            }
            case "MAXSTEPS" -> modelView.setMaxSteps(Integer.parseInt(option));
            case "DISCONNECT" -> Platform.runLater(() -> AlertBox.display("Message", option + " has disconnected, the game will end soon."));
            case "JOIN" -> Platform.runLater(() -> AlertBox.display("Message", option + " has joined the game."));
        }
    }

    /**
     * Handles the ErrorMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void errorMessageHandler(ErrorMessage msg) {
        Platform.runLater(() -> AlertBox.display("Error", msg.getError()));
    }


    /**
     * Adds a listener to the View.
     *
     * @param propertyName name of the observed property of the View.
     * @param listener listener added to the View.
     */
    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {

    }
}
