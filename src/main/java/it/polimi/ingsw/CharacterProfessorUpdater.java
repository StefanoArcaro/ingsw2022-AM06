package it.polimi.ingsw;

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
    }

    @Override
    public void effect() {
        // TO DO
    }
}
