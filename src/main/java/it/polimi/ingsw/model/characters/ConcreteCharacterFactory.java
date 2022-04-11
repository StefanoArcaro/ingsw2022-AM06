package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterID;

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

        return switch (character) {
            case CHARACTER_NONE -> new CharacterNone(characterID);
            case CHARACTER_ONE, CHARACTER_SEVEN, CHARACTER_TEN,
                    CHARACTER_ELEVEN, CHARACTER_TWELVE -> new CharacterMover(game, characterID);
            case CHARACTER_TWO -> new CharacterProfessorUpdater(game, characterID);
            case CHARACTER_THREE -> new CharacterInfluenceCalculator(game, characterID);
            case CHARACTER_FOUR -> new CharacterStepsAdder(characterID);
            case CHARACTER_FIVE, CHARACTER_SIX,
                    CHARACTER_EIGHT, CHARACTER_NINE -> new CharacterInfluenceModifier(game, characterID);
        };
    }
}
