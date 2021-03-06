package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.exceptions.NicknameTakenException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.util.Constants;

import java.util.AbstractMap;

public class LobbyPhase extends Phase {

    private static final int NO_PLAYERS = 0;
    private static final int ONE_PLAYER = 1;
    private static final int TWO_PLAYERS = 2;

    private static final int FIRST_PLAYER_INDEX = 0;

    private PlayerColor playerColor;

    /**
     * Default constructor.
     * @param game reference to the game.
     */
    public LobbyPhase(Game game) {
        this.game = game;
        this.phaseFactory = new PhaseFactory(game);
    }

    /**
     * Lobby phase of the game.
     * Handles the entry of new players to the game up to the chosen number of players.
     * @throws NicknameTakenException when a nickname has already been chosen.
     */
    @Override
    public void play() throws NicknameTakenException, MaxPlayersReachedException {
        addPlayer(playerNickname);

        if(game.getPlayers().size() == game.getNumberOfPlayers().getNum()) {
            game.setCurrentPlayer(game.getPlayers().get(FIRST_PLAYER_INDEX));
            game.getListeners().firePropertyChange(Constants.PLAYER_LISTENER, null, game.getCurrentPlayer().getNickname());

            game.setGameState(GameState.PREPARE_PHASE);
            game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
            game.getListeners().firePropertyChange(Constants.PHASE_LISTENER, null, new AbstractMap.SimpleEntry<>(game.getGameState(), game.getGameMode()));
        }
    }

    /**
     * Adds a player to the game if the chosen number of players has yet to be reached.
     * @param nickname of the player to add.
     * @throws NicknameTakenException if the nickname has already been chosen.
     */
    private void addPlayer(String nickname) throws NicknameTakenException, MaxPlayersReachedException {
        if(!checkNicknameTaken(nickname)) {
            int num = game.getPlayers().size();

            switch (num) {
                case NO_PLAYERS -> playerColor = PlayerColor.BLACK;
                case ONE_PLAYER -> playerColor = PlayerColor.WHITE;
                case TWO_PLAYERS -> playerColor = PlayerColor.GRAY;
            }

            Player player = new Player(game, nickname, playerColor);

            if(game.getPlayers().size() < game.getNumberOfPlayers().getNum()) {
                game.addPlayer(player);
            } else {
                throw new MaxPlayersReachedException();
            }
        } else {
            throw new NicknameTakenException();
        }
    }

    /**
     * Checks whether a nickname has already been chosen.
     * @param nickname to check.
     * @return whether the nickname has been chosen already.
     */
    private boolean checkNicknameTaken(String nickname) {
        return getPlayerByNickname(nickname) != null;
    }

    /**
     * Returns the player having the inputted nickname.
     * @param nickname to check.
     * @return the player whose nickname matches the parameter passed.
     */
    private Player getPlayerByNickname(String nickname) {
        for(Player player : game.getPlayers()) {
            if(player.getNickname().equalsIgnoreCase(nickname)) {
                return player;
            }
        }
        return null;
    }
}
