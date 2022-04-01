package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfPlayers;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WizardName;

public class PreparePhase extends Phase {

    private static final int TWO_PLAYERS_TOWERS = 8;
    private static final int THREE_PLAYERS_TOWERS = 6;

    public PreparePhase(Game game) {

    }

    @Override
    public void play() {

    }

    private void islandGroupSetup() {

    }

    private int placeMotherNature() {
        return 0;
    }

    private void placeInitialStudents(int motherNatureIsland) {

    }

    private void initialBagFill() {

    }

    private void instantiateClouds() {
        // TODO based on numberOfPlayers
    }

    private void instantiateProfessors() {

    }

    private void instantiateTowers() {
        int numberOfTowers = game.getNumberOfPlayers().equals(NumberOfPlayers.TWO_PLAYERS)
                ? TWO_PLAYERS_TOWERS : THREE_PLAYERS_TOWERS;

        for(Player player: game.getPlayers()) {
            player.getBoard().setTowers(numberOfTowers);
        }
    }

    private void assignWizards() {

    }

    private boolean checkWizardChosen(WizardName wizardName) {
        return false;
    }

    private void initializeEntranceStudents() {
        // 2P -> 7
        // 3P -> 9
    }

    private Player drawFirstPlayer() {
        return null;
    }

    private void drawCharacters() {
        // for each character -> initialPreparation()
    }

    private void distributeCoins() {
        // 1 each player, the rest in game.treasury - tot 20 coins
    }
}
