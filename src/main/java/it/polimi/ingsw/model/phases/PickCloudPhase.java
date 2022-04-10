package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.gameBoard.CreatureColor;

import java.util.List;

public class PickCloudPhase extends ActionPhase {

    private int cloudID;

    /**
     * Default constructor
     * @param game game played
     * @param currentPlayer player who is playing
     */
    public PickCloudPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Sets the ID of the cloud that the player wants to take
     * @param cloudID ID of the cloud chosen
     */
    public void setCloudID(int cloudID) {
        this.cloudID = cloudID; //TODO: input
    }

    /**
     * Takes the students present on the chosen cloud and places them in the player's entrance
     * @throws NoAvailableCloudException if the cloud chosen doesn't exist
     */
    @Override
    public void play() throws NoAvailableCloudException {
        Cloud cloudChosen = game.getCloudByID(cloudID);
        if(cloudChosen == null) {
            throw new NoAvailableCloudException();
        }

        List<CreatureColor> colorChosen = cloudChosen.getStudents().stream().map(Creature::getColor).toList();

        for(CreatureColor color : colorChosen) {
            currentPlayer.getBoard().addStudentToEntrance(color);
        }
        game.removeCloud(cloudChosen);
    }
}
