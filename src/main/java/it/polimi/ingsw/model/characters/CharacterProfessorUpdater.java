package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

public class CharacterProfessorUpdater extends Character{

    /**
     * Default constructor
     * @param characterID id of the character to create
     */
    public CharacterProfessorUpdater (int characterID) {
        this.characterID = characterID;
        this.cost = 2;
        this.used = false;
        this.numberOfStudents = 0;
        this.numberOfBanCards = 0;
        this.toDoNow = 1;
    }

    @Override
    public void effect() {
        Game.getCurrentRound().updateProfessors();
    }
}
