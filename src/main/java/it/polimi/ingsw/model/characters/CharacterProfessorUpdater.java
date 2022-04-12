package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

public class CharacterProfessorUpdater extends Character {

    /**
     * Default constructor.
     * @param game game played.
     * @param characterID id of the character to create.
     */
    public CharacterProfessorUpdater(Game game, int characterID) {
        this.characterID = characterID;
        this.game = game;
        this.cost = 2;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 1;
        this.numberOfIterations = 0;
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
        this.moreSteps = 0;
    }

    @Override
    public void effect() {
        game.updateProfessors();
    }
}
