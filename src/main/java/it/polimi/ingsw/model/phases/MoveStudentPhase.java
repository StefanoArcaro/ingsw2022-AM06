package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.CreatureColor;

public class MoveStudentPhase extends ActionPhase {

    private CreatureColor creatureColor;

    /**
     * Default constructor
     * @param game
     * @param currentPlayer
     */
    public MoveStudentPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Setter for creatureColor attribute
     * @param creatureColor
     */
    public void setCreatureColor(CreatureColor creatureColor) {
        this.creatureColor = creatureColor;
    }

    /**
     * First part of the action phase.
     *
     */
    @Override
    public void play() {

    }
}
