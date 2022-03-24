package it.polimi.ingsw;

/**
 * Overrides the factory method in order to return the object implementation
 */
public class ConcreteCharacterFactory extends CharacterFactory{

    @Override
    public Character createCharacter(int characterID) {
        switch(characterID) {
            case 1:
            case 7:
            case 10:
            case 11:
            case 12:
                return new CharacterMover(characterID);
            case 2:
                return new CharacterProfessorUpdater(characterID);
            case 3:
                return new CharacterInfluenceCalculator(characterID);
            case 4:
                return new CharacterStepsAdder(characterID);
            case 5:
            case 6:
            case 8:
            case 9:
                return new CharacterInfluenceModifier(characterID);
        }
        return null;
    }
}
