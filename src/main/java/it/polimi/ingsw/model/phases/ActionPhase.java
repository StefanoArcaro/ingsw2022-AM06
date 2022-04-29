package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.listeners.CharacterListener;
import it.polimi.ingsw.listeners.CoinListener;
import it.polimi.ingsw.listeners.IslandGroupListener;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.view.VirtualView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ActionPhase extends Phase {

    protected Player currentPlayer;

    //listeners
    protected final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    protected final String COIN_LISTENER = "coinListener";

    public void createListeners(VirtualView clientView){
        listeners.addPropertyChangeListener(COIN_LISTENER, new CoinListener(clientView));
    }


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
            Character activatedCharacter = game.getCharacterByID(characterID);

            if(activatedCharacter.getNumberOfIterations() < activatedCharacter.getToDoNow()) {

                //just one character in each player's turn
                Character activateCharacter = game.getActivatedCharacter();
                Character characterNone = game.getCharacterByID(CharacterID.CHARACTER_NONE.getID());
                if(!activateCharacter.equals(characterNone) && !activateCharacter.equals(activatedCharacter)){
                    throw new MoreCharactersInTurnExceptions();
                }

                int price = activatedCharacter.getCost();
                if(currentPlayer.getCoins() >= price) {

                    activatedCharacter.effect();
                    game.setActivatedCharacter(activatedCharacter);

                    activatedCharacter.setUsed();
                    activatedCharacter.increaseNumberOfIteration();

                    currentPlayer.spendCoins(price);
                    int oldCoins = currentPlayer.getCoins();
                    listeners.firePropertyChange(COIN_LISTENER, oldCoins, currentPlayer.getCoins());

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

    private boolean checkValidCharacterID(int characterID) {
        return characterID != 0 && game.getCharacterByID(characterID) != null;
    }

    /**
     * Method for paying the character.
     * @param activatedCharacter character chosen to pay.
     * @throws NotEnoughMoneyException if the player doesn't have so many coins.
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
