package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.gameBoard.CreatureColor;

public class CharacterInfluenceModifier extends Character{

    private CreatureColor colorNoPoints;
    private boolean towerCounter;
    private int extraPoints;

    public CharacterInfluenceModifier(int characterID){
        this.characterID = characterID;
        this.used = false;
        this.numberOfStudents = 0;
        this.colorNoPoints = null;
        this.towerCounter = true;
        this.extraPoints = 0;
        this.toDoNow = 0;
        this.moreSteps = 0;

        CharacterID character = CharacterID.values()[characterID];

        switch (character) {
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

    /**
     * @return extra points given by the character effect
     */
    public int getExtraPoints() {
        return extraPoints;
    }

    /**
     * @return whether the towers affect the influence calculation or not
     */
    public boolean getTowerCounter (){
        return towerCounter;
    }

    /**
     * Set a color that will not be considered for the influence calculation
     * @param colorNoPoints the color to ignore
     */
    public void setColorNoPoints(CreatureColor colorNoPoints) {
        this.colorNoPoints = colorNoPoints;
    }

    public CreatureColor getColorNoPoints() {
        return colorNoPoints;
    }
}
