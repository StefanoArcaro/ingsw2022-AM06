package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.CreatureColor;

public class MoveStudentPhase extends ActionPhase {

    private CreatureColor creatureColor;

    public MoveStudentPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
    }

    public void setCreatureColor(CreatureColor creatureColor) {
        this.creatureColor = creatureColor;
    }

    @Override
    public void play() {

    }
}
