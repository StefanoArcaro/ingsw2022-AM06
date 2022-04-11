package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.NoAvailableBanCardsException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.gameBoard.IslandGroup;

public class CharacterInfluenceModifier extends Character {

    protected int islandGroupIndex;

    /**
     * Default constructor
     * @param game game played
     * @param characterID id of the character to create
     */
    public CharacterInfluenceModifier(Game game, int characterID) {
        this.game = game;
        this.characterID = characterID;
        this.used = false;
        this.numberOfStudents = 0;
        this.colorNoPoints = null;
        this.towerCounter = true;
        this.extraPoints = 0;
        this.toDoNow = 0;
        this.numberOfIterations = 0;
        this.moreSteps = 0;

        CharacterID character = CharacterID.values()[characterID];

        switch (character) {
            case CHARACTER_FIVE -> {
                this.cost = 2;
                this.numberOfBanCards = 4;
                this.toDoNow = 1;
            }
            case CHARACTER_SIX -> {
                this.cost = 3;
                this.numberOfBanCards = 0;
                this.towerCounter = false;
            }
            case CHARACTER_EIGHT -> {
                this.cost = 2;
                this.numberOfBanCards = 0;
                this.extraPoints = 2;
            }
            case CHARACTER_NINE -> {
                this.cost = 3;
                this.numberOfBanCards = 0;
            }
        }
    }

    /**
     * Set the island group on which to add the ban card
     * @param islandGroupIndex is the index of the island group chosen
     */
    public void setIslandGroupIndex(int islandGroupIndex) {
        this.islandGroupIndex = islandGroupIndex; // TODO: input
    }

    @Override
    public void effect() throws NoAvailableBanCardsException, OutOfBoundException {
        CharacterID character = CharacterID.values()[characterID];
        if(character.equals(CharacterID.CHARACTER_FIVE)) {
            effect_five(this.islandGroupIndex);
        }
    }

    /**
     * Add the ban card on the island group chosen, if there are enough ban cards
     * @param islandGroupIndex is the index of the island group chosen
     * @throws NoAvailableBanCardsException if there are not enough ban cards on the card
     * @throws OutOfBoundException when the index of the island group chosen doesn't exist
     */
    private void effect_five(int islandGroupIndex) throws NoAvailableBanCardsException, OutOfBoundException {
        int numberOfIslandGroups = game.getIslandGroups().size();

        if(islandGroupIndex >= 0 && islandGroupIndex < numberOfIslandGroups) {
            IslandGroup islandGroupChosen = game.getIslandGroupByIndex(islandGroupIndex);
            if(numberOfBanCards > 0) {
                islandGroupChosen.addBanCard();
                removeBanCard();
            } else {
                throw new NoAvailableBanCardsException();
            }
        } else {
            throw new OutOfBoundException();
        }
    }

}
