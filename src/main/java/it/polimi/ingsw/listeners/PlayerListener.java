package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.network.message.serverToclient.CurrentPhaseMessage;
import it.polimi.ingsw.network.message.serverToclient.CurrentPlayerMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;

public class PlayerListener extends Listener {

    public PlayerListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        virtualView.send(new GenericMessage("It's your turn!"), getCurrentPlayer());
        virtualView.sendAllExcept(new CurrentPlayerMessage((String) evt.getNewValue()), getCurrentPlayer());

        Game game = virtualView.getGameManager().getGame();
        if(game.getGameState().equals(GameState.PREPARE_PHASE)) {
            if(!game.getPlayers().get(0).equals(game.getCurrentPlayer())) {
                virtualView.send(new CurrentPhaseMessage(getCurrentPhaseAction(game.getGameState()), Constants.getPhaseInstructions(game.getGameState(), game.getGameMode())), getCurrentPlayer());
            }
        } else if(game.getGameState().equals(GameState.PLANNING_PHASE)) {
            if(game.getFirstPlayerIndex() != game.getPlayers().indexOf(game.getCurrentPlayer())) {
                virtualView.send(new CurrentPhaseMessage(getCurrentPhaseAction(game.getGameState()), Constants.getPhaseInstructions(game.getGameState(), game.getGameMode())), getCurrentPlayer());
            }
        }
    }
}
