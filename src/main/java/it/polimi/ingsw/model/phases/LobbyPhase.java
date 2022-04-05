package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerColor;

import java.util.ArrayList;

public class LobbyPhase extends Phase {

    private static final int NO_PLAYERS = 0;
    private static final int ONE_PLAYER = 1;
    private static final int TWO_PLAYERS = 2;

    private PlayerColor playerColor;

    // Used for testing purposes
    ArrayList<String> names;

    /**
     * Default constructor
     * @param game reference to the game
     */
    public LobbyPhase(Game game) {
        this.game = game;

        // Testing
        this.names = new ArrayList<>();
        names.add("Stefano");
        names.add("Stefano");
        names.add("Chiara");
        names.add("Stefano");
        names.add("Nick");
        names.add("Chiara");
        names.add("Nick");
    }

    /**
     * Lobby phase of the game.
     * Handles the entry of new players to the game up to
     * the chosen number of players.
     */
    @Override
    public void play() {
        int i = 0;

        while(game.getPlayers().size() < game.getNumberOfPlayers().getNum()) {
            // TODO input : nickname to be received
            String nickname = names.get(i);
            i++;

            addPlayer(nickname);
        }

        game.setGameState(GameState.PREPARE_PHASE);
    }

    /**
     * Adds a player to the game if the chosen number of
     * players has yet to be reached
     */
    private void addPlayer(String nickname) {
        if(!isNicknameTaken(nickname)) {
            int num = game.getPlayers().size();

            switch(num) {
                case NO_PLAYERS:
                    playerColor = PlayerColor.BLACK;
                    break;
                case ONE_PLAYER:
                    playerColor = PlayerColor.WHITE;
                    break;
                case TWO_PLAYERS:
                    playerColor = PlayerColor.GRAY;
            }

            Player player = new Player(game, nickname, playerColor);
            game.addPlayer(player);
        }
    }

    /**
     * Checks whether a nickname has already been chosen
     * @param nickname to check
     * @return whether the nickname has been chosen already
     */
    private boolean isNicknameTaken(String nickname) {
        return getPlayerByNickname(nickname) != null;
    }

    /**
     * Returns the player having the inputted nickname
     * @param nickname to check
     * @return the player whose nickname matches the parameter passed
     */
    private Player getPlayerByNickname(String nickname) {
        for(Player player : game.getPlayers()) {
            if(player.getNickname().equals(nickname)) {
                return player;
            }
        }
        return null;
    }
}
