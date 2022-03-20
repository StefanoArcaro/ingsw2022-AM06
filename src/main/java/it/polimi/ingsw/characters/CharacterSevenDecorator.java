package it.polimi.ingsw.characters;

public class CharacterSevenDecorator extends CharacterDecorator{

    protected final int characterID = 7;
    protected final String name = "Personaggio7";
    protected final int cost = 1;

    private ArrayList<Student> students;

    /**
     * draws students to place on the card
     */
    private void addStudent(){

    }

    @Override
    public void initialPreparation() {

    }

    /**
     * Return the students on the card.
     * @return ArrayList<Student> students on the card
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Swap up to three students.
     */
    public void swapStudents(){

    }

    /**
     * Swaps one student at a time between card and entrance.
     * @param characterStudent student from card
     * @param entraceStudent student from entrance
     */
    private void swapStudent(Student characterStudent, Student entraceStudent){

    }

    @Override
    public void effect() {

    }
}
