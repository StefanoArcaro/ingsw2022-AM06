package it.polimi.ingsw.model.characters;

/**
 * Overrides the factory method in order to return the object implementation
 */
public class ConcreteCharacterFactory extends CharacterFactory {

    @Override
    public Character createCharacter(int characterID) {

        CharacterID character = CharacterID.values()[characterID];

        switch(character) {
            case CHARACTER_NONE:
                return null;
            case CHARACTER_ONE:
            case CHARACTER_SEVEN:
            case CHARACTER_TEN:
            case CHARACTER_ELEVEN:
            case CHARACTER_TWELVE:
                return new CharacterMover(characterID);
            case CHARACTER_TWO:
                return new CharacterProfessorUpdater(characterID);
            case CHARACTER_THREE:
                return new CharacterInfluenceCalculator(characterID);
            case CHARACTER_FOUR:
                return new CharacterStepsAdder(characterID);
            case CHARACTER_FIVE:
            case CHARACTER_SIX:
            case CHARACTER_EIGHT:
            case CHARACTER_NINE:
                return new CharacterInfluenceModifier(characterID);
        }
        return null;
    }
}
