package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.ExceededStepsException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.IslandGroup;

public class MoveMotherNaturePhase extends ActionPhase {

    private int numberOfSteps;
    private final int maxNumberOfSteps;

    /**
     * Default constructor
     * @param game game played
     * @param currentPlayer player who is playing
     */
    public MoveMotherNaturePhase(Game game, Player currentPlayer) {
        this.game = game;
        this.currentPlayer = currentPlayer;
        if(activatedCharacter != null) {
            this.maxNumberOfSteps = game.getPlayerPriority().get(currentPlayer).getMaxSteps(activatedCharacter);
        } else {
            this.maxNumberOfSteps = game.getPlayerPriority().get(currentPlayer).getMaxSteps();
        }
    }

    /**
     * Sets the number of steps mother nature has to take
     * @param numberOfSteps number of steps chosen
     */
    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps; //TODO: input
    }

    /**
     * Checks if the number of steps chosen is allowed
     * by comparing them to the number of steps indicated by the assistant played
     * @param numberOfSteps number of steps chosen
     * @return whether the steps chosen are allowed
     */
    private boolean checkNumberOfSteps(int numberOfSteps) {
        return numberOfSteps <= maxNumberOfSteps;
    }

    /**
     * Moves mother nature in the steps indicated
     * Calculates the influence on the island where mother nature arrives
     * and replaces the towers if necessary
     * Check whether adjacent islands need to be merged
     * @throws ExceededStepsException if it's tried to perform more steps than allowed
     */
    @Override
    public void play() throws ExceededStepsException {
        IslandGroup currentIslandGroup = game.getMotherNature().getCurrentIslandGroup();
        int numberOfIslandGroups = game.getIslandGroups().size();
        int currentIslandGroupIndex = game.getIndexOfIslandGroup(currentIslandGroup);
        IslandGroup nextIslandGroup;

        if (currentIslandGroupIndex >= 0) {
            int nextIndex;
            if (checkNumberOfSteps(numberOfSteps)) {
                nextIndex = (currentIslandGroupIndex + numberOfSteps) % numberOfIslandGroups;
            } else {
                throw new ExceededStepsException();
            }
            nextIslandGroup = game.getIslandGroupByIndex(nextIndex);
            game.getMotherNature().setCurrentIslandGroup(nextIslandGroup);

            if (game.getGameMode().equals(GameMode.EXPERT)) {
                game.calculateInfluence(nextIndex, activatedCharacter);
            } else {
                game.calculateInfluence(nextIndex);
            }
        }
    }
}
