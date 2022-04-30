package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.network.message.clientToserver.Message;
import it.polimi.ingsw.network.message.serverToclient.Answer;
import it.polimi.ingsw.network.server.Server;

import java.beans.PropertyChangeSupport;

//gestisce la singola partita
public class GameManager {

    private Game game;
    private GameController gameController;
    private final PropertyChangeSupport listeners =new PropertyChangeSupport(this);
    private final static String GAME_CONTROLLER = "gameController";


    public GameManager(){


        game = new Game();
        gameController = new GameController(game, this);
        listeners.addPropertyChangeListener(GAME_CONTROLLER, gameController);
    }


    public void setNumberOfPlayer(int numberOfPlayer) {
        game.setNumberOfPlayers(numberOfPlayer);
    }


    public void setGameMode(GameMode gameMode) {
        game.setGameMode(gameMode);
    }


    public void addPlayer(String nickname) {
        //todo: nella lobbyPhase;
    }


    public void singleSend(Answer answer) {
        //trovo la virtual view corrispondente al client a cui devo mandare il messaggio
        //virtualView.send(answer)
    }


    public void sendAll(Answer answer) {
        /*for(tutti i player nel gioco ) {
            trovo la virtual view corrispondente al client a cui devo mandare il messaggio
            virtualView.send(answer)
        }*/
    }


    public void sendAllExcepet(Answer answer) {
        //qualche parametro in input per sapere a chi non inviare
        /*for(tutti i player nel gioco ) {
            if(player non è quello da escludere) {
                trovo la virtual view corrispondente al client a cui devo mandare il messaggio
                virtualView.send(answer)
            }
        }*/

    }


    public void onMessageReceived(Message message) {
        listeners.firePropertyChange("actionByClient", null, message);
    }





}
