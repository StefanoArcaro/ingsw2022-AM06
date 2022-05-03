package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.AssistantTakenException;
import it.polimi.ingsw.exceptions.InvalidPriorityException;
import it.polimi.ingsw.model.gameBoard.Assistant;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class PlanningPhase extends Phase {

    private static final int MIN_PRIORITY = 1;
    private static final int MAX_PRIORITY = 10;

    private static final int FIRST_PLAYER_INDEX = 0;

    int firstPlayerIndex;
    private Player currentPlayer;
    private ArrayList<Player> playingOrder;
    private final Map<Player, Assistant> playerPriority;
    int turns;

    /**
     * Default constructor.
     * @param game reference to the game.
     * @param players list of the players.
     * @param firstPlayerIndex index of the player going first.
     */
    public PlanningPhase(Game game, ArrayList<Player> players, int firstPlayerIndex) {
        this.game = game;
        this.phaseFactory = new PhaseFactory(game);
        this.firstPlayerIndex = firstPlayerIndex;
        this.playingOrder = new ArrayList<>(players);
        this.playerPriority = new HashMap<>();
        turns = 0;
    }

    /**
     * Planning phase of the game.
     * The cloud cards are filled with the correct number of students and
     * each player plays an assistant card.
     * The assistant cards then serve as a way to determine the action phase's
     * playing order.
     */
    @Override
    public void play() throws AssistantTakenException, InvalidPriorityException {
        playAssistant();

        if(turns == game.getNumberOfPlayers().getNum()) {
            fillClouds();
            calculatePlayingOrder();

            game.setPlayingOrder(playingOrder);
            game.setFirstPlayerIndex(game.getPlayers().indexOf(playingOrder.get(FIRST_PLAYER_INDEX)));
            game.setPlayerPriority(playerPriority);
            game.setGameState(GameState.MOVE_STUDENT_PHASE);
            game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
            game.setCurrentPlayer(playingOrder.get(FIRST_PLAYER_INDEX));
        }
    }

    /**
     * The current player chooses an assistant card to play.
     */
    private void playAssistant() throws AssistantTakenException, InvalidPriorityException {
        Assistant assistant;
        currentPlayer = game.getCurrentPlayer();

        if(checkValidPriority(priority)) {
            if(!checkAssistantPlayed(priority)) {
                turns += 1;
                assistant = currentPlayer.getWizard().playAssistant(priority);
                currentPlayer.getWizard().removeAssistant(priority);
                game.getListeners().firePropertyChange(Constants.ASSISTANT_LISTENER, null, new ArrayList<>(List.of(assistant)));

                playerPriority.put(currentPlayer, assistant);

                if(turns < game.getNumberOfPlayers().getNum()) {
                    game.setCurrentPlayer(game.getNextPlayer());
                }
            } else {
                throw new AssistantTakenException();
            }
        } else {
            throw new InvalidPriorityException();
        }
    }

    /**
     * Checks if the selected priority is inside the external bounds and if
     * the assistant corresponding to such priority has not been played yet.
     * @param priority to check.
     * @return whether the priority passed is valid and whether the corresponding
     * assistant has not yet been played.
     */
    private boolean checkValidPriority(int priority) {
        if(priority >= MIN_PRIORITY && priority <= MAX_PRIORITY) {
            return currentPlayer.getWizard().getAssistants().stream()
                    .map(Assistant::getPriority).toList().contains(priority);
        }
        return false;
    }

    /**
     * Checks if the currentPlayer's chosen Assistant can be played or not.
     * @param priority of the Assistant that the currentPlayer wants to play.
     * @return whether the Assistant can be played or not.
     */
    private boolean checkAssistantPlayed(int priority) {
        List<Integer> priorities = playerPriority.values()
                .stream()
                .map(Assistant::getPriority).toList();

        List<Integer> assistantsToPlay = currentPlayer.getWizard().getAssistants()
                .stream()
                .map(Assistant::getPriority).toList();

        if(priorities.contains(priority)) {
            return !priorities.containsAll(assistantsToPlay);
        }
        return false;
    }

    /**
     * Fills the cloud cards.
     */
    private void fillClouds() {
        for(Cloud cloud : game.getClouds()) {
            if(!cloud.fill(game.getBag(), playingOrder.size())) {
                game.setSkipPickCloudPhase(true);
            }
        }
    }

    /**
     * Calculates the new playing order based on the assistant cards played.
     */
    private void calculatePlayingOrder() {
        playingOrder = playerPriority.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
