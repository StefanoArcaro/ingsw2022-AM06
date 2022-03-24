package it.polimi.ingsw;

public class CharacterInfluenceModifier extends Character{

    public CharacterInfluenceModifier(int characterID){
        this.characterID = characterID;
        this.used = false;
        this.numberOfStudents = 0;
        switch (characterID) {
            case 5:
                this.cost = 2;
                this.numberOfBanCards = 4;
                break;
            case 6:
            case 9:
                this.cost = 3;
                this.numberOfBanCards = 0;
                break;
            case 8:
                this.cost = 2;
                this.numberOfBanCards = 0;
                break;
        }
    }

    @Override
    public void effect() {

    }
}
