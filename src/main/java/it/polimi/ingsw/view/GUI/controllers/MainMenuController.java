package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

public class MainMenuController implements GUIController {

    private GUI gui;

    @FXML
    private TextField address_field;
    @FXML
    private TextField portNumber_field;


    public void setGUI(GUI gui) {
        this.gui = gui;
    }



    /**
     * Closes the application when the "Quit" button is pressed.
     **/
    public void quit() {
        //todo send message
        System.exit(0);
    }

    /**
     * Changes the stage scene to the setup one when the button "Play" is pressed.
     **/
    public void play() {
        gui.changeStage(Constants.SETUP);
    }

    /**
     * Instantiates a socket connection with the server
     * and changes the scene to the lobby.
     */
    public void serverInfo() {
        //todo: add error case
        String address;
        int port;

        if(address_field.getText().matches(Constants.IPV4_PATTERN)) {
            address = address_field.getText();
        } else {
            address = Constants.DEFAULT_IP_ADDRESS;
        }

        try {
            port = Integer.parseInt(portNumber_field.getText());
        } catch (NumberFormatException e) {
            port = Constants.DEFAULT_PORT;
        }

        try {
            gui.setSocketClient(new SocketClient(gui, new Socket(address, port)));
        } catch (IOException e) {
            System.err.println("A problem occurred while trying to instantiate the connection.");
            System.err.println("The application will now close...");
            System.exit(0);
        }

        gui.changeStage(Constants.LOGIN);
    }


}
