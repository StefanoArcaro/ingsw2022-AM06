package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.NoAvailableColorException;
import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.gameBoard.*;

import java.util.ArrayList;

public class CharacterMover extends Character {

    private static final int ISLAND_ID_MIN = 1;
    private static final int ISLAND_ID_MAX = 12;

    int islandID;
    CreatureColor fromColor;
    CreatureColor toColor;
    CreatureColor colorToRemove;

    /**
     * Default constructor
     * @param game game played
     * @param characterID id of the character to create
     */
    public CharacterMover(Game game, int characterID) {
        this.game = game;
        this.characterID = characterID;
        this.used = false;
        this.numberOfBanCards = 0;
        this.students = new ArrayList<>();
        this.colorNoPoints = null;
        this.extraPoints = 0;
        this.towerCounter = true;
        this.numberOfIterations = 0;
        this.moreSteps = 0;

        CharacterID character = CharacterID.values()[characterID];

        switch (character) {
            case CHARACTER_ONE -> {
                this.cost = 1;
                this.numberOfStudents = 4;
                this.toDoNow = 1;
            }
            case CHARACTER_SEVEN -> {
                this.cost = 1;
                this.numberOfStudents = 6;
                this.toDoNow = 3;
            }
            case CHARACTER_TEN -> {
                this.cost = 1;
                this.numberOfStudents = 0;
                this.toDoNow = 2;
            }
            case CHARACTER_ELEVEN -> {
                this.cost = 2;
                this.numberOfStudents = 4;
                this.toDoNow = 1;
            }
            case CHARACTER_TWELVE -> {
                this.cost = 3;
                this.numberOfStudents = 0;
                this.toDoNow = 1;
            }
        }
    }

    @Override
    public void initialPreparation() {
        Bag bag = game.getBag();

        for(int i = 0; i < numberOfStudents; i++) {
            students.add(bag.drawStudent());
        }
    }


    /**
     * Set the color of the student to move from the source
     * @param fromColor color of the student to move
     */
    public void setFromColor(CreatureColor fromColor) {
        this.fromColor = fromColor; //TODO: input
    }

    /**
     * Set the color of the student to move from the destination
     * @param toColor color of the student to move
     */
    public void setToColor(CreatureColor toColor) {
        this.toColor = toColor; //TODO: input
    }

    /**
     * Set the island ID of the island where to move the student
     * @param islandID of the island where to move the student
     */
    public void setIslandID(int islandID) {
        this.islandID = islandID; //TODO: input

    }

    /**
     * Set the color of the students to remove from the hall of all players.
     * Up to three students per color can be removed.
     * @param colorToRemove color of the students to remove
     */
    public void setColorToRemove(CreatureColor colorToRemove) {
        this.colorToRemove = colorToRemove; //TODO: input

    }


    @Override
    public void effect() throws NoAvailableColorException, OutOfBoundException {
        CharacterID character = CharacterID.values()[this.characterID];

        //TODO: input
        switch (character) {
            case CHARACTER_ONE -> effect_one(fromColor, islandID);
            case CHARACTER_SEVEN -> effect_seven(fromColor, toColor);
            case CHARACTER_TEN -> effect_ten(fromColor, toColor);
            case CHARACTER_ELEVEN -> effect_eleven(fromColor);
            case CHARACTER_TWELVE -> effect_twelve(colorToRemove);
        }
    }

    /**
     * Pick the student from this card and move it to an island
     * @param studentColor color of the student on the card chosen
     * @param islandID island where to move the student
     * @throws NoAvailableColorException when the color chosen is not on the card
     * @throws OutOfBoundException when the island id chosen doesn't exist
     */
    private void effect_one(CreatureColor studentColor, int islandID) throws NoAvailableColorException, OutOfBoundException {
        if(this.getStudents().stream().map(Creature::getColor).toList().contains(studentColor)) {
            if(islandID >= ISLAND_ID_MIN && islandID <= ISLAND_ID_MAX) {
                Island island = game.getIslandByID(islandID);
                island.receiveStudent(studentColor);
                students.remove(getStudentByColor(students, studentColor));
                students.add(game.getBag().drawStudent());
            } else {
                throw new OutOfBoundException();
            }
        } else {
            throw new NoAvailableColorException();
        }
    }

    /**
     * Swaps a student present on the card with a student present in the entrance to the player board
     * @param cardColor color of the student on the card
     * @param entranceColor color of the student in the player's entrance
     * @throws NoAvailableColorException when the color chosen is not on the card or in player's entrance
     */
    private void effect_seven(CreatureColor cardColor, CreatureColor entranceColor) throws NoAvailableColorException {
        Board playerBoard = game.getCurrentPlayer().getBoard();
        Student studentEntrance = getStudentByColor(playerBoard.getEntrance().getStudents(), entranceColor);
        Student studentCard = getStudentByColor(students, cardColor);

        if(studentEntrance != null && studentCard != null) {
            playerBoard.removeStudentFromEntrance(entranceColor);
            students.remove(studentCard);

            playerBoard.addStudentToEntrance(cardColor);
            students.add(studentEntrance);
        } else {
            throw new NoAvailableColorException();
        }
    }


    /**
     * Swaps a student present in the hall of the player board
     * with a student present in the entrance to the player board
     * @param hallColor color of the student in player's hall
     * @param entranceColor color of the student in player's entrance
     * @throws NoAvailableColorException when the color chosen is not in player's hall or in player's entrance
     */
    private void effect_ten(CreatureColor hallColor, CreatureColor entranceColor) throws NoAvailableColorException {
        Board playerBoard = game.getCurrentPlayer().getBoard();
        Student studentEntrance = getStudentByColor(playerBoard.getEntrance().getStudents(), entranceColor);

        if(studentEntrance != null && playerBoard.getHall().studentInTable(hallColor)) {
            playerBoard.removeStudentFromEntrance(entranceColor);
            playerBoard.removeStudentFromHall(hallColor);

            playerBoard.addStudentToEntrance(hallColor);
            playerBoard.addStudentToHall(entranceColor);
        } else {
            throw new NoAvailableColorException();
        }
    }

    /**
     * Take a student from this card e move it to player's hall
     * @param cardColor color of the student on the card chosen
     * @throws NoAvailableColorException when the color chosen is not on the card
     */
    private void effect_eleven(CreatureColor cardColor) throws NoAvailableColorException {
        Board boardPlayer = game.getCurrentPlayer().getBoard();
        Student cardStudent = getStudentByColor(students, cardColor);

        if(cardStudent != null) {
            students.remove(cardStudent);
            boardPlayer.addStudentToHall(cardColor);
            students.add(game.getBag().drawStudent());
        } else {
            throw new NoAvailableColorException();
        }
    }

    /**
     * Remove three student of a color from every player's hall
     * @param colorToBeRemoved color chosen to be removed
     */
    private void effect_twelve(CreatureColor colorToBeRemoved) {
        ArrayList<Player> players = game.getPlayers();

        for(Player player : players) {
            int removed = 0;
            boolean haveStudent = true;

            while(removed < 3 && haveStudent) {
                haveStudent = player.getBoard().removeStudentFromHall(colorToBeRemoved);
                removed++;
            }
        }
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
