package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * GameManager class handles a single match.
 */
public class GameManager {

    private final Map<Integer, ClientHandler> clients;
    private final Map<String, Integer> nicknameToId;

    private final Game game;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final static String GAME_CONTROLLER = "gameController";


    public GameManager() {
        this.clients = new HashMap<>();
        this.nicknameToId = new HashMap<>();

        this.game = new Game();
        this.game.createListeners(new VirtualView(this));
        GameController gameController = new GameController(this, game);
        this.listeners.addPropertyChangeListener(GAME_CONTROLLER, gameController);
    }

    public Game getGame() {
        return game;
    }

    public Map<Integer, ClientHandler> getClients() {
        return clients;
    }

    public Map<String, Integer> getNicknameToId() {
        return nicknameToId;
    }

    public void setGamePreferences(NumberOfPlayers numberOfPlayers, GameMode gameMode) {
        setNumberOfPlayers(numberOfPlayers);
        setGameMode(gameMode);
    }

    private void setNumberOfPlayers(NumberOfPlayers numberOfPlayers) {
        game.setNumberOfPlayers(numberOfPlayers.getNum());
    }

    private void setGameMode(GameMode gameMode) {
        game.setGameMode(gameMode);
    }

    public void addClient(int clientID, ClientHandler clientHandler, String nickname) {
        clients.put(clientID, clientHandler);
        nicknameToId.put(nickname, clientID);

    }

    public void removeClient(int clientID) {
        for(String name : nicknameToId.keySet()) {
            if(nicknameToId.get(name) == clientID) {
                nicknameToId.remove(name);
                return;
            }
        }
    }

    public void singleSend(Answer answer, String nickname) {
        ClientHandler clientHandler = clients.get(nicknameToId.get(nickname));
        clientHandler.sendMessage(answer);
    }

    public void sendAll(Answer answer) {
        for(ClientHandler clientHandler : clients.values()) {
            clientHandler.sendMessage(answer);
        }
    }

    public void sendAllExcept(Answer answer, String nickname) {
        for(String name : nicknameToId.keySet()) {
            if(!name.equals(nickname)) {
                clients.get(nicknameToId.get(name)).sendMessage(answer);
            }
        }
    }

    public void onMessageReceived(Map.Entry<String, Message> pair) {
        listeners.firePropertyChange("gameController", null, pair);
    }
}