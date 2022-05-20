package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.NoAvailableCloudException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.Creature;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.util.Constants;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class PickCloudPhase extends ActionPhase {

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

    /**
     * Takes the students present on the chosen cloud and places them in the player's entrance.
     * @throws NoAvailableCloudException if the chosen cloud doesn't exist.
     */
    @Override
    public void play() throws NoAvailableCloudException {
        Cloud cloudChosen = game.getCloudByID(cloudID);
        if(cloudChosen == null || cloudChosen.isEmpty()) {
            throw new NoAvailableCloudException();
        }

        ArrayList<Cloud> cloud = new ArrayList<>();
        cloud.add(cloudChosen);
        game.getListeners().firePropertyChange(Constants.CLOUD_LISTENER, null, cloud);

        List<CreatureColor> colorChosen = cloudChosen.getStudents().stream().map(Creature::getColor).toList();

        for(CreatureColor color : colorChosen) {
            currentPlayer.getBoard().addStudentToEntrance(color);
        }
        game.getListeners().firePropertyChange(Constants.BOARD_LISTENER, null, currentPlayer.getBoard());

        cloudChosen.empty();

        // Restore
        game.getActivatedCharacter().setNumberOfIterations(0);
        game.setActivatedCharacter(game.getCharacterByID(CharacterID.CHARACTER_NONE.getID()));
        //TODO: check - reset character in model view
        game.getListeners().firePropertyChange(Constants.CHARACTER_PLAYED_LISTENER, null, game.getActivatedCharacter());

        int numberOfPlayers = game.getNumberOfPlayers().getNum();
        Player lastPlayer = game.getPlayingOrder().get(numberOfPlayers - 1);
        boolean isCurrentPlayerTheLastOne = game.getCurrentPlayer().equals(lastPlayer);

        if(isCurrentPlayerTheLastOne) {
            calculateNextPhase();
            game.setCurrentPlayer(game.getPlayers().get(game.getFirstPlayerIndex()));
            game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));

            game.getListeners().firePropertyChange(Constants.PLAYER_LISTENER, null, game.getCurrentPlayer().getNickname());
            game.getListeners().firePropertyChange(Constants.PHASE_LISTENER, null, new AbstractMap.SimpleEntry<>(game.getGameState(), game.getGameMode()));

            game.getListeners().firePropertyChange(Constants.ASSISTANTS_AVAILABLE_LISTENER, null, game.getCurrentPlayer().getWizard().getAssistants());

        } else {
            game.setCurrentPlayer(game.getNextPlayer());
            game.setGameState(GameState.MOVE_STUDENT_PHASE);

            game.getListeners().firePropertyChange(Constants.PLAYER_LISTENER, null, game.getCurrentPlayer().getNickname());
            game.getListeners().firePropertyChange(Constants.PHASE_LISTENER, null, new AbstractMap.SimpleEntry<>(game.getGameState(), game.getGameMode()));

            game.getListeners().firePropertyChange(Constants.BOARD_LISTENER, game.getCurrentPlayer().getNickname(), game.getCurrentPlayer().getBoard());
            game.getListeners().firePropertyChange(Constants.ISLAND_GROUPS_LISTENER, game.getCurrentPlayer().getNickname(), new AbstractMap.SimpleEntry<>(game.getIslandGroups(), game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup())));

            game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
        }
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
