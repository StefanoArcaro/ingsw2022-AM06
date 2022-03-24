package it.polimi.ingsw;

import java.util.ArrayList;

public class CharacterMover extends Character{

    public CharacterMover(int characterID) {
        this.characterID = characterID;
        this.used = false;
        this.numberOfBanCards = 0;
        this.students = new ArrayList<Student>();
        switch (characterID){
            case 1:
                this.cost = 1;
                this.numberOfStudents = 4;
                break;
            case 7:
                this.cost = 1;
                this.numberOfStudents = 6;
                break;
            case 10:
                this.cost = 1;
                this.numberOfStudents = 0;
                break;
            case 11:
                this.cost = 2;
                this.numberOfStudents = 4;
                break;
            case 12:
                this.cost = 3;
                this.numberOfStudents = 0;
                break;
        }
    }

    @Override
    public void initialPreparation() {
        // TO DO
    }

    @Override
    public void effect() {

    }
}
