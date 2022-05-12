package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.network.message.serverToclient.CurrentPhaseMessage;
import it.polimi.ingsw.network.message.serverToclient.GenericMessage;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.Map;

public class PhaseListener extends Listener {

    public PhaseListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Map.Entry<GameState, GameMode> preferences = (Map.Entry<GameState, GameMode>) evt.getNewValue();
        GameState gameState = preferences.getKey();
        GameMode gameMode = preferences.getValue();

        if(!gameState.equals(GameState.LOBBY_PHASE)) {
            virtualView.send(new CurrentPhaseMessage(getCurrentPhaseAction(gameState), Constants.getPhaseInstructions(gameState, gameMode)), getCurrentPlayer());
            virtualView.sendAllExcept(new GenericMessage(getCurrentPlayer() + ": " + getCurrentPhaseAction(gameState)), getCurrentPlayer());

            if(gameState.equals(GameState.MOVE_MOTHER_NATURE_PHASE)) {
                Game game = virtualView.getGameManager().getGame();
                int maxSteps = game.getPlayerPriority().get(game.getCurrentPlayer()).getMaxSteps(game.getActivatedCharacter());
                virtualView.send(new GenericMessage("Mother nature can take " + maxSteps + " steps at most."), getCurrentPlayer());
            }
        }
    }
}
