package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.controllers.GUIController;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class GUI extends Application implements View {

    private SocketClient socketClient;
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);
    private final ModelView modelView;

    private final HashMap<String, Scene> nameToScene = new HashMap<>();
    private final HashMap<String, GUIController> nameToController = new HashMap<>();
    private Scene currentScene;
    private Stage stage;



    /**
     * Default constructor.
     */
    public GUI() {
        //TODO: thread?
        this.modelView = new ModelView();
    }



    @Override
    public void start(Stage stage) {
        setup();
        this.stage = stage;
        stage.setScene(currentScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    private void setup() {
        List<String> fxmlList = new ArrayList<>(Arrays.asList(Constants.MENU, Constants.SETUP, Constants.LOGIN));
        try {
            for (String path : fxmlList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                Scene scene = new Scene(loader.load());
                nameToScene.put(path, scene);
                if(path.equals(Constants.MENU)) {
                    this.currentScene = scene;
                }
                GUIController controller = loader.getController();
                controller.setGUI(this);
                nameToController.put(path, controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void changeStage(String scene) {
        currentScene = nameToScene.get(scene);
        stage.setScene(currentScene);
        stage.show();
    }




    /**
     * Adds a listener to the GUI.
     * @param propertyName name of the observed property of the GUI.
     * @param listener listener added to the CLI.
     */
    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        this.listener.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * @return the socket attribute of this client.
     */
    @Override
    public SocketClient getSocketClient() {
        return this.socketClient;
    }

    /**
     * Sets the socket attribute of this client to the specified one.
     * @param socketClient socket to set.
     */
    @Override
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        socketClient.enablePinger(true); //todo check
    }




    /**
     * Handles the LoginReplyMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void loginReplyHandler(LoginReplyMessage msg) {

    }

    /**
     * Handles the WizardsAvailableMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void wizardsHandler(WizardsAvailableMessage msg) {

    }

    /**
     * Handles the AssistantsMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void assistantsHandler(AssistantsMessage msg) {

    }

    /**
     * Handles the ActivePlayersMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void activePlayersHandler(ActivePlayersMessage msg) {

    }

    /**
     * Handles the BoardMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void boardHandler(BoardMessage msg) {

    }

    /**
     * Handles the IslandGroupsMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void islandGroupsHandler(IslandGroupsMessage msg) {

    }

    /**
     * Handles the IslandMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void islandHandler(IslandMessage msg) {

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
     * Handles the CoinMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void coinsHandler(CoinMessage msg) {

    }

    /**
     * Handles the CurrentPlayerMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void currentPlayerHandler(CurrentPlayerMessage msg) {

    }

    /**
     * Handles the CurrentPhaseMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void currentPhaseHandler(CurrentPhaseMessage msg) {

    }

    /**
     * Handles the CharacterDrawnMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void charactersDrawnHandler(CharacterDrawnMessage msg) {

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

    }

    /**
     * Handles the LoserMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void loserHandler(LoserMessage msg) {

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

    }

    /**
     * Handles the ErrorMessage sent by the server.
     *
     * @param msg the message to handle.
     */
    @Override
    public void errorMessageHandler(ErrorMessage msg) {

    }
}
