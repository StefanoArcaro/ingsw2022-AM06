package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;

/**
 * Overrides the factory method in order to return the object implementation
 */
public class ConcreteCharacterFactory extends CharacterFactory {

    public ConcreteCharacterFactory(Game game) {
        this.game = game;
    }

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
                return new CharacterMover(game, characterID);
            case CHARACTER_TWO:
                return new CharacterProfessorUpdater(game, characterID);
            case CHARACTER_THREE:
                return new CharacterInfluenceCalculator(game, characterID);
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
