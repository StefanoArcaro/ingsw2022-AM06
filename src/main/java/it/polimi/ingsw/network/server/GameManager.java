package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * GameManager class handles a single match.
 */
public class GameManager {

    private Server server;

    private final Map<Integer, ClientHandler> clients;
    private final Map<String, Integer> nicknameToId;

    private final Game game;
    private final GameController gameController;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final static String GAME_CONTROLLER = "gameController";

    /**
     * Default constructor.
     */
    public GameManager(Server server) {
        this.server = server;

        this.clients = new HashMap<>();
        this.nicknameToId = new HashMap<>();

        this.game = new Game();
        this.game.createListeners(new VirtualView(this));
        this.gameController = new GameController(this, game);
        this.listeners.addPropertyChangeListener(GAME_CONTROLLER, gameController);
    }

    /**
     * Constructor used in controllers' tests.
     */
    public GameManager() {
        this.clients = new HashMap<>();
        this.nicknameToId = new HashMap<>();

        this.game = new Game();
        this.game.createListeners(new VirtualView(this));
        this.gameController = new GameController(this, game);
        this.listeners.addPropertyChangeListener(GAME_CONTROLLER, gameController);
    }

    /**
     * @return the server that instantiated this game manager.
     */
    public Server getServer() {
        return server;
    }

    /**
     * @return the Game instance managed by this.
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return the Game controller.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * @return the map containing ID and handler of each player of the game.
     */
    public Map<Integer, ClientHandler> getClients() {
        return clients;
    }

    /**
     * @return the map containing nickname and ID of each player of the game.
     */
    public Map<String, Integer> getNicknameToId() {
        return nicknameToId;
    }

    /**
     * Sets the number of players for the game to the specified one.
     * @param numberOfPlayers number of players to set.
     */
    private void setNumberOfPlayers(NumberOfPlayers numberOfPlayers) {
        game.setNumberOfPlayers(numberOfPlayers.getNum());
    }

    /**
     * Sets the game mode for the game to the specified one.
     * @param gameMode the mode to set.
     */
    private void setGameMode(GameMode gameMode) {
        game.setGameMode(gameMode);
    }

    /**
     * Sets the preferences of the game to the specified ones.
     * @param numberOfPlayers the number of players to set for the game.
     * @param gameMode the mode to set for the game.
     */
    public void setGamePreferences(NumberOfPlayers numberOfPlayers, GameMode gameMode) {
        setNumberOfPlayers(numberOfPlayers);
        setGameMode(gameMode);
    }

    /**
     * Adds a client as a player to the game.
     * To do so, it also puts the client's information in the
     * clients and nicknameToId maps.
     * @param clientID ID of the client to add.
     * @param clientHandler handler of the client to add.
     * @param nickname nickname of the client to add.
     */
    public void addClient(int clientID, ClientHandler clientHandler, String nickname) {
        clients.put(clientID, clientHandler);
        nicknameToId.put(nickname, clientID);
    }

    /**
     * Empties the game manager's maps.
     */
    public void emptyClients() {
        clients.clear();
        nicknameToId.clear();
    }

    /**
     * Removes a player from the game.
     * @param clientID ID of the client to remove.
     */
    public void removeClient(int clientID) {
        clients.remove(clientID);

        for(String name : nicknameToId.keySet()) {
            if(nicknameToId.get(name) == clientID) {
                nicknameToId.remove(name);
                return;
            }
        }
    }

    /**
     * Sends the specified message to the client identified by the specified nickname only.
     * @param answer the message to send.
     * @param nickname the nickname of the client to send the message to.
     */
    public void singleSend(Answer answer, String nickname) {
        ClientHandler clientHandler = clients.get(nicknameToId.get(nickname));
        clientHandler.sendMessage(answer);
    }

    /**
     * Sends the specified message to all the players in the game.
     * @param answer the message to send.
     */
    public void sendAll(Answer answer) {
        for(ClientHandler clientHandler : clients.values()) {
            clientHandler.sendMessage(answer);
        }
    }

    /**
     * Sends a message to all the players in the game but the one identified
     * by the specified nickname.
     * @param answer the message to send.
     * @param nickname the nickname of the client to not send the message to.
     */
    // TODO check synchronized
    public synchronized void sendAllExcept(Answer answer, String nickname) {
        for(String name : nicknameToId.keySet()) {
            if(!name.equals(nickname)) {
                clients.get(nicknameToId.get(name)).sendMessage(answer);
            }
        }
    }

    /**
     * Calls for the game controller to handle the reception of a message from a client.
     * @param pair a pair of values made of the JSON representation of the message
     *             and the message itself.
     */
    public synchronized void onMessageReceived(Map.Entry<String, Message> pair) {
        listeners.firePropertyChange("gameController", null, pair);
    }
}