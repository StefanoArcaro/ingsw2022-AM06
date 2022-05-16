package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;

public class PhaseFactory {

    private final Game game;

    /**
     * Default constructor.
     * @param game reference to the game.
     */
    public PhaseFactory(Game game) {
        this.game = game;
    }

    /**
     * Creates a phase based on the current state of the game.
     * @param gameState state of the game.
     * @return the created phase.
     */
    public Phase createPhase(GameState gameState) {
        int firstPlayerIndex = game.getFirstPlayerIndex();

        return switch (gameState) {
            case LOBBY_PHASE -> new LobbyPhase(game);
            case PREPARE_PHASE -> new PreparePhase(game);
            case PLANNING_PHASE -> new PlanningPhase(game, game.getPlayers(), firstPlayerIndex);
            case MOVE_STUDENT_PHASE -> new MoveStudentPhase(game, game.getCurrentPlayer());
            case MOVE_MOTHER_NATURE_PHASE -> new MoveMotherNaturePhase(game, game.getCurrentPlayer());
            case PICK_CLOUD_PHASE -> new PickCloudPhase(game, game.getCurrentPlayer());
            case ENDED_TOWER, ENDED_ISLAND, ENDED_STUDENTS, ENDED_ASSISTANTS -> new EndgamePhase(game);
        };
    }
}
