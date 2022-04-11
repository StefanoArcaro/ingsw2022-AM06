package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;

public abstract class ActionPhase extends Phase {

    protected Player currentPlayer;

    /**
     * Pay and use the effect of a character
     * @param characterID ID of the chosen character
     * @throws NoAvailableBanCardsException if there aren't available ban cards
     * @throws OutOfBoundException if the index of an island chosen doesn't exist
     * @throws NoAvailableColorException if the color chosen is not available
     * @throws NotEnoughMoneyException if the player doesn't have money to buy the character
     * @throws TooManyIterationsException if the player tries to use the effect of the character more times than allowed
     */
    public void playCharacter(int characterID) throws NoAvailableBanCardsException, OutOfBoundException,
            NoAvailableColorException, NotEnoughMoneyException, TooManyIterationsException, InvalidCharacterIDException {

        if(checkValidCharacterID(characterID)) {
            Character activatedCharacter = game.getCharacterByID(characterID);

            if(activatedCharacter.getNumberOfIterations() < activatedCharacter.getToDoNow()) {
                payCharacter(activatedCharacter);
                game.setActivatedCharacter(activatedCharacter);
                activatedCharacter.effect();
                activatedCharacter.setUsed();
                activatedCharacter.increaseNumberOfIteration();
            } else {
                throw new TooManyIterationsException();
            }
        } else {
            throw new InvalidCharacterIDException();
        }
    }

    private boolean checkValidCharacterID(int characterID) {
        return characterID != 0 && game.getCharacterByID(characterID) != null;
    }

    /**
     * Method for paying the character
     * @param activatedCharacter character chosen to pay
     * @throws NotEnoughMoneyException if the player doesn't have so many coins
     */
    private void payCharacter(Character activatedCharacter) throws NotEnoughMoneyException {
        int price = activatedCharacter.getCost();
        if(currentPlayer.getCoins() >= price) {
            currentPlayer.spendCoins(price);
        } else {
            throw new NotEnoughMoneyException();
        }
    }
}
