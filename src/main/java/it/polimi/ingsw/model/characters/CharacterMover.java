package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gameBoard.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CharacterMover extends Character {

    CreatureColor fromColor;
    CreatureColor toColor;
    int islandID;
    CreatureColor colorToRemove;

    public CharacterMover(int characterID) {
        this.characterID = characterID;
        this.used = false;
        this.numberOfBanCards = 0;
        this.students = new ArrayList<>();

        CharacterID character = CharacterID.values()[characterID];

        switch(character) {
            case CHARACTER_ONE:
                this.cost = 1;
                this.numberOfStudents = 4;
                this.toDoNow = 1;
                break;
            case CHARACTER_SEVEN:
                this.cost = 1;
                this.numberOfStudents = 6;
                this.toDoNow = 3;
                break;
            case CHARACTER_TEN:
                this.cost = 1;
                this.numberOfStudents = 0;
                this.toDoNow = 2;
                break;
            case CHARACTER_ELEVEN:
                this.cost = 2;
                this.numberOfStudents = 4;
                this.toDoNow = 1;
                break;
            case CHARACTER_TWELVE:
                this.cost = 3;
                this.numberOfStudents = 0;
                this.toDoNow = 1;
                break;
        }
    }

    @Override
    public void initialPreparation() {
        Bag bag = Bag.getBag();

        for(int i = 0; i < numberOfStudents; i++) {
            students.add(bag.drawStudent());
        }
    }


    public void setFromColor(CreatureColor fromColor) {
        this.fromColor = fromColor;
    }

    public void setToColor(CreatureColor toColor) {
        this.toColor = toColor;
    }

    public void setIslandID(int islandID) {
        this.islandID = islandID;
    }

    public void setColorToRemove(CreatureColor colorToRemove) {
        this.colorToRemove = colorToRemove;
    }


    @Override
    public void effect() {
        CharacterID character = CharacterID.values()[this.characterID];

        switch(character) {
            case CHARACTER_ONE:
                effect_one(fromColor, islandID);
                break;
            case CHARACTER_SEVEN:
                effect_seven(fromColor, toColor);    //how to handle iteration? (till 3 students)
                break;
            case CHARACTER_TEN:
                effect_ten(fromColor, toColor);      //how to handle iteration? (till 2 students)
                break;
            case CHARACTER_ELEVEN:
                effect_eleven(fromColor);
                break;
            case CHARACTER_TWELVE:
                effect_twelve(colorToRemove);
                break;
        }
    }

    /**
     * Pick the student from this card and move it to an island
     * @param studentColor color of the student on the card chosen
     * @param islandID island where to move the student
     * @return whether everything went well
     */
    private boolean effect_one(CreatureColor studentColor, int islandID) {
        if(this.getStudents().stream().map(Creature::getColor).collect(Collectors.toList()).contains(studentColor)) {
            Island island = Game.getGame().getIslandByID(islandID);
            island.addStudent(new Student(studentColor));
            students.remove(getStudentByColor(students, studentColor));
            students.add(Bag.getBag().drawStudent());
            return true;
        }
        return false;
    }

    /**
     * Swaps a student present on the card with a student present in the entrance to the player board
     * @param cardColor color of the student on the card
     * @param entranceColor color of the student in the player's entrance
     * @return whether everything went well
     */
    private boolean effect_seven(CreatureColor cardColor, CreatureColor entranceColor) {
        Board playerBoard = Game.getCurrentRound().getCurrentPlayer().getBoard();
        Student studentEntrance = getStudentByColor(playerBoard.getEntrance(), entranceColor);
        Student studentCard = getStudentByColor(students, cardColor);

        if(studentEntrance != null && studentCard != null) {
            playerBoard.removeStudentFromEntrance(entranceColor);
            students.remove(studentCard);

            playerBoard.addStudentToEntrance(cardColor);
            students.add(studentEntrance);

            return true;
        }
        return false;
    }


    /**
     * Swaps a student present in the hall of the player board
     * with a student present in the entrance to the player board
     * @param hallColor color of the student in player's hall
     * @param entranceColor colore of the student in player's entrance
     * @return whether everything went well
     */
    private boolean effect_ten(CreatureColor hallColor, CreatureColor entranceColor) {
        Board playerBoard = Game.getCurrentRound().getCurrentPlayer().getBoard();
        Student studentEntrance = getStudentByColor(playerBoard.getEntrance(), entranceColor);

        if(studentEntrance != null && playerBoard.studentInTable(hallColor)) {
            playerBoard.removeStudentFromEntrance(entranceColor);
            playerBoard.removeStudentFromHall(hallColor);

            playerBoard.addStudentToEntrance(hallColor);
            playerBoard.addStudentToHall(entranceColor);

            return true;
        }
        return false;
    }

    /**
     * Take a student from this card e move it to player's hall
     * @param cardColor color of the student on the card chosen
     * @return whether everything went well
     */
    private boolean effect_eleven(CreatureColor cardColor) {
        Board boardPlayer = Game.getCurrentRound().getCurrentPlayer().getBoard();
        Student cardStudent = getStudentByColor(students, cardColor);

        if(cardStudent != null) {
            students.remove(cardStudent);
            boardPlayer.addStudentToHall(cardColor);
            students.add(Bag.getBag().drawStudent());
            return true;
        }

        return false;
    }

    /**
     * Remove three student of a color from every player's hall
     * @param colorToBeRemoved color chosen to be removed
     * @return whether everything went well
     */
    private boolean effect_twelve(CreatureColor colorToBeRemoved) {
        ArrayList<Player> players = Game.getGame().getPlayers();

        for(Player player : players) {
            int removed = 0;
            boolean haveStudent = true;

            while(removed < 3 && haveStudent) {
                haveStudent = player.getBoard().removeStudentFromHall(colorToBeRemoved);
                removed++;
            }
        }
        return false;
    }

    /**
     * Returns the student with the given color among a set of students
     * @param students set of students
     * @param studentColor color of the student chosen
     * @return the student with the color given
     */
    private Student getStudentByColor(ArrayList<Student> students, CreatureColor studentColor) {
        for(Student student : students) {
            if(student.getColor().equals(studentColor)) {
                return student;
            }
        }
        return null;
    }
}
