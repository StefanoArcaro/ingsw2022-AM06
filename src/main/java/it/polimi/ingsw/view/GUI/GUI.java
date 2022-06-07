package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.controllers.GUIController;
import it.polimi.ingsw.view.GUI.controllers.PlayAssistantController;
import it.polimi.ingsw.view.GUI.controllers.PlayController;
import it.polimi.ingsw.view.GUI.controllers.WizardController;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    private Stage stage;
    private Scene currentScene;
    private final HashMap<String, Scene> nameToScene = new HashMap<>();
    private final HashMap<String, GUIController> nameToController = new HashMap<>();
    private final Map<String, String> nicknameToSceneName = new HashMap<>();

    private final Map<Color, CreatureColor> colorToCreature = new HashMap<>();
    private final Map<Color, PlayerColor> colorToPlayer = new HashMap<>();
    private final Map<Color, String> colorToHex = new HashMap<>();

    private CreatureColor entranceColor;
    private CreatureColor hallColor;
    private int destinationIsland;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        setup();
        this.stage = stage;
        stage.setScene(currentScene);

        this.stage.setOnCloseRequest(e -> {
            e.consume();
            int exitStatus = ConfirmationBox.display(1, this.stage, "Are you sure you want to quit?");
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


    public Stage getStage() {
        return stage;
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

    public void changeStage(String scene) {
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
            Stage stage = new Stage();

            GUIController controller = getNameToController().get(sceneName);
            controller.init();
            Scene scene = getSceneByController(controller);

            stage.setScene(scene);
            stage.show();
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

        // Player color map
        colorToPlayer.put(Color.BLACK, PlayerColor.BLACK);
        colorToPlayer.put(Color.WHITE, PlayerColor.WHITE);
        colorToPlayer.put(Color.GRAY, PlayerColor.GRAY);

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

    public PlayerColor getPlayerColorByFXColor(Color color) {
        return colorToPlayer.get(color);
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

    public Color getFXColorByHex(String hex) {
        for(Color color : colorToHex.keySet()) {
            if(colorToHex.get(color).equals(hex)) {
                return color;
            }
        }
        return null;
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

        changeStage(Constants.LOBBY);
    }

    /**
     * Handles the GameStartedMessage sent by the server.
     */
    @Override
    public void gameStartingHandler() {
        changeStage(Constants.WIZARD);
    }

    /**
     * Handles the GameReadyMessage sent by the server.
     */
    @Override
    public void gameReadyHandler() {
        changeStage(Constants.BOARD_AND_ISLANDS);
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
        ((PlayController)(nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCoins(msg.getCoins());
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
        //TODO
    }

    /**
     * Handles the CharacterInfoMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void characterInfoHandler(CharacterInfoMessage msg) {
        //TODO
    }

    /**
     * Handles the CharacterPlayedMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void characterPlayedHandler(CharacterPlayedMessage msg) {
        modelView.setPlayedCharacter(msg);
        //TODO
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
        //todo update
        Platform.runLater(() -> AlertBox.display("Message", "You won!"));
    }

    /**
     * Handles the LoserMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void loserHandler(LoserMessage msg) {
        modelView.setWinner(msg.getWinnerNickname());
        //todo update
        Platform.runLater(() -> AlertBox.display("Message", msg.getWinnerNickname() + " won!"));
    }

    /**
     * Handles the end of the game
     */
    @Override
    public void gameEndedHandler() {
        //TODO

        Platform.runLater(() -> AlertBox.display("Message", "The game has ended, the application will close soon..."));
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
            case "PHASE" -> ((PlayController) (nameToController.get(Constants.BOARD_AND_ISLANDS))).updateCurrentPhase(option);
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
