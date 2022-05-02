package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.listeners.BoardListener;
import it.polimi.ingsw.listeners.CloudListener;
import it.polimi.ingsw.listeners.WinListener;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeSupport;


public class EndgamePhase extends Phase {

    private final static int MAX_TOWERS = 8;

    //Listener
    //protected final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final static String WIN_LISTENER = "winListener";


    /**
     * Default constructor.
     * @param game game played.
     */
    public EndgamePhase(Game game) {
        this.game = game;
        this.phaseFactory = new PhaseFactory(game);
    }

    /*public void createListeners(VirtualView clientView){
        listeners.addPropertyChangeListener(WIN_LISTENER, new WinListener(clientView));
    }*/

    /**
     * Checks how the game ended and calculates the winner.
     */
    @Override
    public void play() {
        if(game.getGameState().equals(GameState.ENDED_TOWER)) {
            winner = game.getCurrentPlayer();
        } else {
            winner = calculateWinner();
        }
        game.getListeners().firePropertyChange(WIN_LISTENER, null, winner.getNickname());
    }

    /**
     * @return the winner.
     */
    private Player calculateWinner() {
        int numberOfTowers;
        int minNumberOfTowers = MAX_TOWERS;
        Player playerWinning = null;

        for(Player player : game.getPlayers()) {
            numberOfTowers = player.getBoard().getTowers();
            if(numberOfTowers < minNumberOfTowers) {
                minNumberOfTowers = numberOfTowers;
                playerWinning = player;
            } else if(numberOfTowers == minNumberOfTowers && playerWinning != null) {
                playerWinning = calculateWinner_Professors(player, playerWinning);
            }
        }

        return playerWinning;
    }

    /**
     * Calculates the player who has more professors.
     * @param playerA first player.
     * @param playerB second player.
     * @return the player with more professors.
     */
    private Player calculateWinner_Professors(Player playerA, Player playerB) {
        int numberOfProfessorsA = playerA.getBoard().getProfessors().size();
        int numberOfProfessorsB = playerB.getBoard().getProfessors().size();

        if(numberOfProfessorsA > numberOfProfessorsB) {
            return playerA;
        }

        return playerB;
    }
}
