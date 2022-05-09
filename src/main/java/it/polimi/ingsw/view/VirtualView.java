package it.polimi.ingsw.view;

import it.polimi.ingsw.GameManager;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.SocketClientHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * It is a representation of the virtual instance of the client.
 * Forwards the events received from the model over the network (to the client view).
 * It receives events from the client view over the network and forwards them to the controller.
 */

public class VirtualView {

    private final GameManager gameManager;

    public VirtualView(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * @return the game manager attribute of this virtual view.
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Prepares the answer for sending it through the network.
     * @param serverAnswer the answer to be sent to the user.
     */
    public void send(Answer serverAnswer, String nickname) {
        gameManager.singleSend(serverAnswer, nickname);
    }

    /**
     * Sends the message to all the players in the game.
     * @param serverAnswer the answer to be sent to the players.
     */
    public void sendAll(Answer serverAnswer){
        gameManager.sendAll(serverAnswer);
    }

    /**
     * Sends the specified message to all the players in the game but the one
     * identified by the specified nickname.
     * @param serverAnswer the message to send.
     * @param nickname nickname of the player to exclude.
     */
    public void sendAllExcept(Answer serverAnswer, String nickname){
        gameManager.sendAllExcept(serverAnswer, nickname);
    }
}
