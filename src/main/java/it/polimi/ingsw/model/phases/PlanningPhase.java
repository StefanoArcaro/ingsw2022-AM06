package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Bag;
import it.polimi.ingsw.model.gameBoard.Cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlanningPhase extends Phase {

    private Player currentPlayer;
    private ArrayList<Player> playingOrder;
    private Map<Player, Assistant> playerPriority;
    private Bag bag;

    public PlanningPhase(Game game, ArrayList<Player> players, int firstPlayerIndex) {
        this.game = game;
        currentPlayer = players.get(firstPlayerIndex);
        this.playingOrder = new ArrayList<>();
        playerPriority = new HashMap<>();
        bag = Bag.getBag();
    }

    public ArrayList<Player> getPlayingOrder() {
        return new ArrayList<>(playingOrder);
    }

    @Override
    public void play() {
        fillClouds();
        playAssistants();
    }

    private void fillClouds() {
        for(Cloud cloud : game.getClouds()) {
            cloud.fill(bag, playingOrder.size());
        }
    }

    public void playAssistants() {
        // TODO
    }

    private void calculatePlayingOrder() {
        // TODO
    }

    /**
     * Checks if the currentPlayer's chosen Assistant can be played or not
     * @param priority of the Assistant that the currentPlayer wants to play
     * @return whether the Assistant can be played or not
     */
    public boolean checkAssistantPlayed(int priority) {
        List<Integer> priorities = playerPriority.values()
                .stream()
                .map(Assistant::getPriority)
                .collect(Collectors.toList());

        List<Integer> assistantsToPlay = currentPlayer.getWizard().getAssistants()
                .stream()
                .map(Assistant::getPriority)
                .collect(Collectors.toList());

        if(priorities.contains(priority)) {
            if(priorities.containsAll(assistantsToPlay)) {
                return false;
            }

            return true;
        }

        return false;
    }
}
