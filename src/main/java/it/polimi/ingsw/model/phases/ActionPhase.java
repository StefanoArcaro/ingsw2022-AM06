package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.util.Constants;

public abstract class ActionPhase extends Phase {

    protected Player currentPlayer;

    /**
     * Pay and use the effect of a character.
     * @param characterID ID of the chosen character.
     * @throws MoreCharactersInTurnExceptions if a character was already played in this turn.
     * @throws NoAvailableBanCardsException if there aren't available ban cards.
     * @throws OutOfBoundException if the index of an island chosen doesn't exist.
     * @throws NoAvailableColorException if the color chosen is not available.
     * @throws NotEnoughMoneyException if the player doesn't have money to buy the character.
     * @throws TooManyIterationsException if the player tries to use the effect of the character more times than allowed.
     */
    public void playCharacter(int characterID) throws NoAvailableBanCardsException, OutOfBoundException,
            NoAvailableColorException, NotEnoughMoneyException, TooManyIterationsException, InvalidCharacterIDException, MoreCharactersInTurnExceptions {

        if(checkValidCharacterID(characterID)) {
            Character characterToActivate = game.getCharacterByID(characterID);

            if(characterToActivate.getNumberOfIterations() < characterToActivate.getToDoNow()) {

                //just one character in each player's turn
                Character activateCharacter = game.getActivatedCharacter();
                Character characterNone = game.getCharacterByID(CharacterID.CHARACTER_NONE.getID());
                if(!activateCharacter.equals(characterNone) && !activateCharacter.equals(characterToActivate)) {
                    throw new MoreCharactersInTurnExceptions();
                }

                int price = characterToActivate.getCost();
                if(currentPlayer.getCoins() >= price || characterToActivate.isPayed()) {
                    characterToActivate.effect();
                    characterToActivate.setUsed();
                    game.setActivatedCharacter(characterToActivate);
                    characterToActivate.increaseNumberOfIteration();

                    if(!characterToActivate.isPayed()) {
                        currentPlayer.spendCoins(price);
                        characterToActivate.setPayed(true);
                    }

                    game.getListeners().firePropertyChange(Constants.CHARACTER_PLAYED_LISTENER, null, characterToActivate);

                } else {
                    throw new NotEnoughMoneyException();
                }
            } else {
                throw new TooManyIterationsException();
            }
        } else {
            throw new InvalidCharacterIDException();
        }
    }

    /**
     * Checks if the specified character ID is valid, meaning if it's within the
     * boundaries of possible character IDs.
     * @param characterID to check.
     * @return whether the ID is valid.
     */
    private boolean checkValidCharacterID(int characterID) {
        return characterID != 0 && game.getCharacterByID(characterID) != null;
    }
}
