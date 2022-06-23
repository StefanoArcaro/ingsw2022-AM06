package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.gui.AlertBox;
import it.polimi.ingsw.view.gui.ConfirmationBox;
import it.polimi.ingsw.view.gui.GUI;
import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

public class MainMenuSetupController implements GUIController {

    private GUI gui;

    public TextField address_field;
    public TextField portNumber_field;

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

    }

    /**
     * Changes the stage scene to the setup one when the button "Play" is pressed.
     **/
    public void play() {
        gui.changeStage(gui.getPrimaryStage(), Constants.SETUP);
    }

    /**
     * Instantiates a socket connection with the server
     * and changes the scene to the lobby.
     */
    public void serverInfo() {
        String address;
        int port;

        String message = "";

        if(address_field.getText().matches(Constants.IPV4_PATTERN)) {
            address = address_field.getText();
        } else {
            address = Constants.DEFAULT_IP_ADDRESS;
            message += "Invalid IP address: set to 'localhost' by default.\n";
        }

        try {
            port = Integer.parseInt(portNumber_field.getText());

            if(port < 1024) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            port = Constants.DEFAULT_PORT;
            message += "Invalid port: set to '1234' by default.\n";
        }

        if(!message.equals("")) {
            String finalMessage = message;
            Platform.runLater(() -> AlertBox.display("Warning", finalMessage));
        }

        try {
            SocketClient socketClient = new SocketClient(gui, new Socket(address, port));
            gui.setSocketClient(socketClient);
            gui.setMessageParser(socketClient);
        } catch (IOException e) {
            System.err.println("A problem occurred while trying to instantiate the connection.");
            System.err.println("The application will now close...");
            System.exit(0);
        }

        gui.changeStage(gui.getPrimaryStage(), Constants.LOGIN);
    }

    @Override
    public void quit() {
        Platform.runLater(() -> ConfirmationBox.display(0, gui.getPrimaryStage(),"Are you sure you want to quit?"));
    }

}
