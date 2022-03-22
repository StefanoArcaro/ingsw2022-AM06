package it.polimi.ingsw;

import java.util.ArrayList;

public class CharacterOneDecorator extends CharacterDecorator{

    protected final int characterID = 1;
    protected final String name = "Character1";
    protected final int cost = 1;

    private ArrayList<Student> students;

    /**
     * Draws students to place on the card.
     */
    private boolean addStudent(){

    }

    /**
     * Remove a student from the card
     * @param student that has been moved to an island
     */
    private boolean removeStudent(Student student){

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
     * Move a student from this card to an island
     * @param student to move
     * @param island to move the student to
     */
    private void moveStudentToIsland(Student student, Island island){

    }

    @Override
    public void effect() {

    }


}
