package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeListener;

/** Interface used to communicate to View from Model. */

public abstract class Listener implements PropertyChangeListener {

    protected final VirtualView virtualView;

    /**
     * Default constructor.
     * @param virtualView virtual representation of the clients' view.
     */
    public Listener(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    /**
     * @return the nickname of the current player.
     */
    protected String getCurrentPlayer() {
        return virtualView.getGameManager().getGame().getCurrentPlayer().getNickname();
    }

    /**
     * @return the name of the phase.
     */
    protected String getCurrentPhaseAction(GameState gameState) {
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
