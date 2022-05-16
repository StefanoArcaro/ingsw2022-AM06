package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class GameStartedListener extends Listener {

    /**
     * Default constructor.
     *
     * @param virtualView virtual representation of the clients' view.
     */
    public GameStartedListener(VirtualView virtualView) {
        super(virtualView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Game game = (Game) evt.getNewValue();

        List<String> activePlayers = game.getPlayers().stream().map(Player::getNickname).toList();
        List<Board> boards = game.getPlayers().stream().map(Player::getBoard).toList();
        ArrayList<IslandGroup> islandGroups = game.getIslandGroups();
        String currentPlayerNickname = game.getCurrentPlayer().getNickname();
        int motherNatureIndex = game.getIndexOfIslandGroup(game.getMotherNature().getCurrentIslandGroup());

        virtualView.sendAll(new ActivePlayersMessage(activePlayers));
        virtualView.sendAll(new CurrentPlayerMessage(currentPlayerNickname));

        for(Board b : boards) {
            virtualView.sendAll(new BoardMessage(b.getPlayerNickname(), b.getEntrance(), b.getHall(), b.getProfessors(), b.getTowers()));
        }

        virtualView.sendAll(new IslandGroupsMessage(islandGroups, motherNatureIndex));

    }
}