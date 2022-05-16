package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

public class CharacterPlayedMessage extends Answer {

    private final int characterID;
    private final int cost;
    private final boolean used;
    private final ArrayList<Student> students;
    private final int banCards;

    public CharacterPlayedMessage(int characterID, int cost, boolean used, ArrayList<Student> students, int banCards) {
        super(MessageType.CHARACTER_PLAYED_MESSAGE);
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

    public boolean isUsed() {
        return used;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int getBanCards() {
        return banCards;
    }
}
