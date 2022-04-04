package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.gameBoard.Bag;
import it.polimi.ingsw.model.gameBoard.Cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlanningPhase extends Phase {

    private static final int NULL_CHARACTER_ID = 0;
    private static final int FIRST_PLAYER_INDEX = 0;

    int firstPlayerIndex;
    private Player currentPlayer;
    private ArrayList<Player> playingOrder;
    private final Map<Player, Assistant> playerPriority;

    public PlanningPhase(Game game, ArrayList<Player> players, int firstPlayerIndex) {
        this.game = game;
        this.activatedCharacter = new ConcreteCharacterFactory().createCharacter(NULL_CHARACTER_ID);
        this.firstPlayerIndex = firstPlayerIndex;
        this.playingOrder = new ArrayList<>(players);
        this.playerPriority = new HashMap<>();
    }

    @Override
    public void play() {
        fillClouds();
        playAssistants();
        calculatePlayingOrder();

        game.setPlayingOrder(playingOrder);
        game.setFirstPlayerIndex(game.getPlayers().indexOf(playingOrder.get(FIRST_PLAYER_INDEX)));
        game.setGameState(GameState.MOVE_STUDENT_PHASE);
    }

    private void fillClouds() {
        for(Cloud cloud : game.getClouds()) {
            cloud.fill(Bag.getBag(), playingOrder.size());
        }
    }

    private void playAssistants() {
        int priority;
        Assistant assistant;

        // Used for testing purposes
        int[] tempPriorities = {2, 1, 3};

        for(int i = 0; i < playingOrder.size(); i++) {
            currentPlayer = playingOrder.get((i + firstPlayerIndex) % game.getNumberOfPlayers().getNum());

            do {
                priority = tempPriorities[i]; // TODO receive input
            } while (checkAssistantPlayed(priority));

            assistant = currentPlayer.getWizard().playAssistant(priority);
            currentPlayer.getWizard().removeAssistant(priority);
            playerPriority.put(currentPlayer, assistant);
        }
    }

    /**
     * Checks if the currentPlayer's chosen Assistant can be played or not
     * @param priority of the Assistant that the currentPlayer wants to play
     * @return whether the Assistant can be played or not
     */
    private boolean checkAssistantPlayed(int priority) {
        List<Integer> priorities = playerPriority.values()
                .stream()
                .map(Assistant::getPriority)
                .collect(Collectors.toList());

        List<Integer> assistantsToPlay = currentPlayer.getWizard().getAssistants()
                .stream()
                .map(Assistant::getPriority)
                .collect(Collectors.toList());

        if(priorities.contains(priority)) {
            return !priorities.containsAll(assistantsToPlay);
        }
        return false;
    }

    private void calculatePlayingOrder() {
        playingOrder = playerPriority.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
