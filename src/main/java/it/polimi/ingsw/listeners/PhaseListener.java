package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.phases.Phase;
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
            virtualView.send(new CurrentPhaseMessage(Constants.getPhaseInstructions(gameState, gameMode)), getCurrentPlayer());

            // TODO check gameState to determine whether to send to all players or just the current player
            virtualView.sendAllExcept(new GenericMessage(getCurrentPlayer() + ": " + getCurrentPhaseAction(gameState)), getCurrentPlayer());
        }
    }

    public String getCurrentPhaseAction(GameState gameState) {
        return switch(gameState) {
            case LOBBY_PHASE -> "Lobby phase";
            case PREPARE_PHASE -> "Prepare phase";
            case PLANNING_PHASE -> "Planning phase";
            case MOVE_STUDENT_PHASE -> "Move student phase";
            case MOVE_MOTHER_NATURE_PHASE -> "Move mother nature phase";
            case PICK_CLOUD_PHASE -> "Pick cloud phase";
            case ENDED_ISLAND -> "Game ended (reached three groups of islands)";
            case ENDED_TOWER -> "Game ended (a player finished his towers)";
            case ENDED_STUDENTS -> "Game ended (finished student in bag)";
            case ENDED_ASSISTANTS -> "Game ended (a player finished his assistants)";
        };
    }
}
