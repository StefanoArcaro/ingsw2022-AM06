package it.polimi.ingsw;

public class CharacterInfluenceModifier extends Character{

    private CreatureColor colorNoPoints;
    private boolean towerCounter;
    private int extraPoints;

    public CharacterInfluenceModifier(int characterID){
        this.characterID = characterID;
        this.used = false;
        this.numberOfStudents = 0;
        this.moreSteps = 0;
        this.colorNoPoints = null;
        this.towerCounter = true;
        this.extraPoints = 0;
        switch (characterID) {
            case 5:
                this.cost = 2;
                this.numberOfBanCards = 4;
                break;
            case 6:
                this.cost = 3;
                this.numberOfBanCards = 0;
                this.towerCounter = false;
                break;
            case 8:
                this.cost = 2;
                this.numberOfBanCards = 0;
                this.extraPoints = 2;
                break;
            case 9:
                this.cost = 3;
                this.numberOfBanCards = 0;
                break;
        }
    }

    @Override
    public void effect() {

    }

    public int getExtraPoints() {
        return extraPoints;
    }

    public boolean getTowerCounter (){
        return towerCounter;
    }

    public void setColorNoPoints(CreatureColor colorNoPoints) {
        this.colorNoPoints = colorNoPoints;
    }

    public CreatureColor getColorNoPoints() {
        return colorNoPoints;
    }
}
