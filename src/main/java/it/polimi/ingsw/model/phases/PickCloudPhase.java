package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.listeners.BoardListener;
import it.polimi.ingsw.listeners.CloudListener;
import it.polimi.ingsw.listeners.IslandGroupListener;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class PickCloudPhase extends ActionPhase {

    //Listeners
    //protected final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final static String CLOUD_LISTENER = "cloudListener";
    private final static String BOARD_LISTENER = "boardListener";

    /**
     * Default constructor.
     * @param game game played.
     * @param currentPlayer player who is playing.
     */
    public PickCloudPhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
        this.phaseFactory = new PhaseFactory(game);
    }

    /*public void createListeners(VirtualView clientView){
        listeners.addPropertyChangeListener(CLOUD_LISTENER, new CloudListener(clientView));
        listeners.addPropertyChangeListener(BOARD_LISTENER, new BoardListener(clientView));
    }*/

    /**
     * Takes the students present on the chosen cloud and places them in the player's entrance.
     * @throws NoAvailableCloudException if the chosen cloud doesn't exist.
     */
    @Override
    public void play() throws NoAvailableCloudException {
        Cloud cloudChosen = game.getCloudByID(cloudID);
        if(cloudChosen == null) {
            throw new NoAvailableCloudException();
        }

        game.getListeners().firePropertyChange(CLOUD_LISTENER, null, new ArrayList<>(List.of(cloudID)));

        List<CreatureColor> colorChosen = cloudChosen.getStudents().stream().map(Creature::getColor).toList();

        for(CreatureColor color : colorChosen) {
            currentPlayer.getBoard().addStudentToEntrance(color);
        }
        game.getListeners().firePropertyChange(BOARD_LISTENER, null, currentPlayer.getBoard());

        game.removeCloud(cloudChosen);

        //restore
        game.getActivatedCharacter().setNumberOfIterations(0);
        game.setActivatedCharacter(game.getCharacterByID(CharacterID.CHARACTER_NONE.getID()));

        int numberOfPlayers = game.getNumberOfPlayers().getNum();
        Player lastPlayer = game.getPlayingOrder().get(numberOfPlayers - 1);
        boolean isCurrentPlayerTheLastOne = game.getCurrentPlayer().equals(lastPlayer);

        if(isCurrentPlayerTheLastOne) {
            calculateNextPhase();
            game.setCurrentPlayer(game.getPlayers().get(game.getFirstPlayerIndex()));
        } else {
            game.setGameState(GameState.MOVE_STUDENT_PHASE);
            game.setCurrentPlayer(game.getNextPlayer());
        }

        game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
    }

    /**
     * Sets the next phase of the play.
     * checking the end of the game.
     * due to the end of the assistants or the end of the students.
     */
    private void calculateNextPhase() {
        boolean endedStudent = game.getBag().isEmpty();
        boolean endedAssistant = false;

        for(Player player : game.getPlayers()) {
            if(player.getWizard().checkIfNoAssistants()) {
                endedAssistant = true;
                break;
            }
        }

        if(endedStudent) {
            game.setGameState(GameState.ENDED_STUDENTS);
            return;
        }

        if(endedAssistant) {
            game.setGameState(GameState.ENDED_ASSISTANTS);
            return;
        }

        game.setGameState(GameState.PLANNING_PHASE);
    }
}
