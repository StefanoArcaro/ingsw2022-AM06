package it.polimi.ingsw;

import java.util.ArrayList;

public class CharacterSevenDecorator extends CharacterDecorator {

    protected final int characterID = 7;
    protected final String name = "Character7";
    protected final int cost = 1;

    private ArrayList<Student> students;

    /**
     * draws students to place on the card
     */
    private void addStudent() {

    }

    @Override
    public void initialPreparation() {

    }

    /**
     * Return the students on the card.
     * @return ArrayList<Student> students on the card
     */
    public ArrayList<Student> getStudents() {
        // TODO
        return null;
    }

    /**
     * Swap up to three students.
     */
    public void swapStudents(){

    }

    /**
     * Swaps one student at a time between card and entrance.
     * @param characterStudent student from card
     * @param entranceStudent student from entrance
     */
    private void swapStudent(Student characterStudent, Student entranceStudent) {

    }

    @Override
    public void effect() {

    }
}
