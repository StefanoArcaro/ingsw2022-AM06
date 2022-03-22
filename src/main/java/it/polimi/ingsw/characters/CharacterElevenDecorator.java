package it.polimi.ingsw.characters;

public class CharacterElevenDecorator extends CharacterDecorator {

    protected final int characterID = 11;
    protected final String name = "Character11";
    protected final int cost = 2;

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
     * Return students that are on the card
     * @return students on the card
     */
    public ArrayList<Student> getStudents() {
        // TODO
        return null;
    }

    /**
     * move student from card to hall
     * @param student that i have to move
     */
    private void moveStudentToHall(Student student) {

    }

    @Override
    public void effect() {

    }
}
