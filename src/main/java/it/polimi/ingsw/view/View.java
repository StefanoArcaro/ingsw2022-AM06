package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.serverToclient.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface View {


    /**
     * @return the socket attribute of this client.
     */
    SocketClient getSocketClient();

    /**
     * Sets the socket attribute of this client to the specified one.
     * @param socketClient socket to set.
     */
    void setSocketClient(SocketClient socketClient);




    /**
     * Adds a listener to the View.
     * @param propertyName name of the observed property of the View.
     * @param listener listener added to the View.
     */
    void addListener(String propertyName, PropertyChangeListener listener);


    /**
     * Handles the LoginReplyMessage sent by the server.
     * @param msg the message to handle.
     */
    void loginReplyHandler(LoginReplyMessage msg);

    /**
     * Handles the WizardsAvailableMessage sent by the server.
     * @param msg the message to handle.
     */
    void wizardsHandler(WizardsAvailableMessage msg);

    /**
     * Handles the AssistantsMessage sent by the server.
     * @param msg the message to handle.
     */
    void assistantsHandler(AssistantsMessage msg);

    /**
     * Handles the ActivePlayersMessage sent by the server.
     * @param msg the message to handle.
     */
    void activePlayersHandler(ActivePlayersMessage msg);

    /**
     * Handles the BoardMessage sent by the server.
     * @param msg the message to handle.
     */
    void boardHandler(BoardMessage msg);

    /**
     * Handles the IslandGroupsMessage sent by the server.
     * @param msg the message to handle.
     */
    void islandGroupsHandler(IslandGroupsMessage msg);

    /**
     * Handles the IslandMessage sent by the server.
     * @param msg the message to handle.
     */
    void islandHandler(IslandMessage msg);

    /**
     * Handles the CloudsAvailableMessage sent by the server.
     * @param msg the message to handle.
     */
    void cloudsAvailableHandler(CloudsAvailableMessage msg);

    /**
     * Handles the CloudChosenMessage sent by the server.
     * @param msg the message to handle.
     */
    void cloudChosenHandler(CloudChosenMessage msg);

    /**
     * Handles the CoinMessage sent by the server.
     * @param msg the message to handle.
     */
    void coinsHandler(CoinMessage msg);

    /**
     * Handles the CurrentPlayerMessage sent by the server.
     * @param msg the message to handle.
     */
    void currentPlayerHandler(CurrentPlayerMessage msg);

    /**
     * Handles the CurrentPhaseMessage sent by the server.
     * @param msg the message to handle.
     */
    void currentPhaseHandler(CurrentPhaseMessage msg);

    /**
     * Handles the CharacterDrawnMessage sent by the server.
     * @param msg the message to handle.
     */
    void charactersDrawnHandler(CharacterDrawnMessage msg);

    /**
     * Handles the CharacterInfoMessage sent by the server.
     * @param msg the message to handle.
     */
    void characterInfoHandler(CharacterInfoMessage msg);

    /**
     * Handles the CharacterPlayedMessage sent by the server.
     * @param msg the message to handle.
     */
    void characterPlayedHandler(CharacterPlayedMessage msg);

    /**
     * Displays the match's current state.
     */
    void matchInfoHandler();

    /**
     * Handles the WinnerMessage sent by the server.
     * @param msg the message to handle.
     */
    void winnerHandler(WinnerMessage msg);

    /**
     * Handles the LoserMessage sent by the server.
     * @param msg the message to handle.
     */
    void loserHandler(LoserMessage msg);

    /**
     * Handles the end of the game
     */
    void gameEndedHandler();

    /**
     * Handles the GenericMessage sent by the server.
     * @param msg the message to handle.
     */
    void genericMessageHandler(GenericMessage msg);

    /**
     * Handles the ErrorMessage sent by the server.
     * @param msg the message to handle.
     */
    void errorMessageHandler(ErrorMessage msg);
}
