package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.gameBoard.CreatureColor;

public class CharacterInfluenceModifier extends Character {

    public CharacterInfluenceModifier(int characterID) {
        this.characterID = characterID;
        this.game = Game.getGame();
        this.used = false;
        this.numberOfStudents = 0;
        this.colorNoPoints = null;
        this.towerCounter = true;
        this.extraPoints = 0;
        this.toDoNow = 0;

        CharacterID character = CharacterID.values()[characterID];

        switch(character) {
            case CHARACTER_FIVE:
                this.cost = 2;
                this.numberOfBanCards = 4;
                break;
            case CHARACTER_SIX:
                this.cost = 3;
                this.numberOfBanCards = 0;
                this.towerCounter = false;
                break;
            case CHARACTER_EIGHT:
                this.cost = 2;
                this.numberOfBanCards = 0;
                this.extraPoints = 2;
                break;
            case CHARACTER_NINE:
                this.cost = 3;
                this.numberOfBanCards = 0;
                break;
        }
    }

    @Override
    public void effect() {
        // do nothing;
    }

}
