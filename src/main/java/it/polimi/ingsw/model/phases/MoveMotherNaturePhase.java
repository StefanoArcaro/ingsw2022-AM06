package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.ExceededStepsException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.util.Constants;

import java.util.AbstractMap;

public class MoveMotherNaturePhase extends ActionPhase {

    /**
     * Default constructor.
     * @param game game played.
     * @param currentPlayer player who is playing.
     */
    public MoveMotherNaturePhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
        this.phaseFactory = new PhaseFactory(game);
    }

    /**
     * Checks if the number of steps chosen is allowed.
     * by comparing them to the number of steps indicated by the assistant played.
     * @param numberOfSteps number of steps chosen.
     * @return whether the steps chosen are allowed.
     */
    private boolean checkNumberOfSteps(int numberOfSteps) {
        int maxNumberOfSteps = game.getPlayerPriority().get(currentPlayer).getMaxSteps(game.getActivatedCharacter());
        return numberOfSteps <= maxNumberOfSteps;
    }

    /**
     * Moves mother nature in the steps indicated.
     * Calculates the influence on the island where mother nature arrives
     * and replaces the towers if necessary.
     * Check whether adjacent islands need to be merged.
     * @throws ExceededStepsException if it's tried to perform more steps than allowed.
     */
    @Override
    public void play() throws ExceededStepsException {
        IslandGroup currentIslandGroup = game.getMotherNature().getCurrentIslandGroup();
        int numberOfIslandGroups = game.getIslandGroups().size();
        int currentIslandGroupIndex = game.getIndexOfIslandGroup(currentIslandGroup);
        IslandGroup nextIslandGroup;

        if(currentIslandGroupIndex >= 0) {
            int nextIndex;
            if(checkNumberOfSteps(numberOfSteps)) {
                nextIndex = (currentIslandGroupIndex + numberOfSteps) % numberOfIslandGroups;
            } else {
                throw new ExceededStepsException();
            }

            nextIslandGroup = game.getIslandGroupByIndex(nextIndex);
            game.getMotherNature().setCurrentIslandGroup(nextIslandGroup);
            game.calculateInfluence(nextIndex);
        }

        calculateNextPhase();
        game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
        game.getListeners().firePropertyChange(Constants.PHASE_LISTENER, null, new AbstractMap.SimpleEntry<>(game.getGameState(), game.getGameMode()));
    }

    /**
     * Sets the next phase of the play checking if the game ended due to a player using all
     * of their towers or the number of island groups reaching 3.
     */
    private void calculateNextPhase() {
        boolean endedTower = currentPlayer.getBoard().checkNoTowers();

        for(Player player : game.getPlayers()) {
            if(player.getBoard().checkNoTowers()) {
                endedTower = true;
                break;
            }
        }

        boolean endedIsland = game.checkEndDueToIslandGroup();
        boolean skipPickCloudPhase = game.getSkipPickCloudPhase();

        int numberOfPlayers = game.getNumberOfPlayers().getNum();
        Player lastPlayer = game.getPlayingOrder().get(numberOfPlayers - 1);
        boolean isCurrentPlayerTheLastOne = game.getCurrentPlayer().equals(lastPlayer);

        if(endedTower) {
            game.setGameState(GameState.ENDED_TOWER);
            return;
        }

        if(endedIsland) {
            game.setGameState(GameState.ENDED_ISLAND);
            return;
        }

        if(skipPickCloudPhase) {
            game.getActivatedCharacter().reset();
            game.setActivatedCharacter(game.getCharacterByID(CharacterID.CHARACTER_NONE.getID()));

            game.getListeners().firePropertyChange(Constants.CHARACTER_PLAYED_LISTENER, null, game.getActivatedCharacter());

            if(isCurrentPlayerTheLastOne) {
                game.setGameState(GameState.ENDED_STUDENTS);
            } else {
                game.setGameState(GameState.MOVE_STUDENT_PHASE);
                game.setCurrentPlayer(game.getNextPlayer());
                game.getListeners().firePropertyChange(Constants.PLAYER_LISTENER, null, game.getCurrentPlayer().getNickname());
            }
            return;
        }

        game.setGameState(GameState.PICK_CLOUD_PHASE);
        game.getListeners().firePropertyChange(Constants.CLOUD_LISTENER, null, game.getClouds());
    }
}
