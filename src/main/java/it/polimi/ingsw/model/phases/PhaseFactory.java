package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;

public class PhaseFactory {

    private Game game;

    public PhaseFactory(Game game) {
        this.game = game;
    }

    public Phase createPhase(GameState gameState) {

        switch(gameState) {
            case LOBBY_PHASE:
                return new LobbyPhase(game);
            case PREPARE_PHASE:
                return new PreparePhase(game);
            case PLANNING_PHASE:
                int firstPlayerIndex = game.getFirstPlayerIndex();
                return new PlanningPhase(game, game.getPlayers(), firstPlayerIndex);
            case MOVE_STUDENT_PHASE:
                return new MoveStudentPhase(game, game.getCurrentPlayer());
            case MOVE_MOTHER_NATURE_PHASE:
                return new MoveMotherNaturePhase(game, game.getCurrentPlayer());
            case PICK_CLOUD_PHASE:
                return new PickCloudPhase(game, game.getCurrentPlayer());
            case ENDED_TOWER:
            case ENDED_ISLAND:
            case ENDED_STUDENTS:
            case ENDED_ASSISTANTS:
                return new EndgamePhase(game);
        }
        return null;
    }
}
