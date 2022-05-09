package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gameBoard.Student;

import java.util.ArrayList;

public class CharacterView {

    private final int characterID;
    private int cost;
    private boolean used;
    private ArrayList<Student> students;
    private int banCards;

    public CharacterView(int characterID, int cost, boolean used, ArrayList<Student> students, int banCards) {
        this.characterID = characterID;
        this.cost = cost;
        this.used = used;
        this.students = students;
        this.banCards = banCards;
    }

    public int getCharacterID() {
        return characterID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getBanCards() {
        return banCards;
    }

    public void setBanCards(int banCards) {
        this.banCards = banCards;
    }
}
