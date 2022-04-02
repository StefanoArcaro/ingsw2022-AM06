package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class PickCloudPhase extends ActionPhase {

    private int cloudID;

    public PickCloudPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
    }

    public void setCloudID(int cloudID) {
        this.cloudID = cloudID;
    }

    @Override
    public void play() {

    }
}
