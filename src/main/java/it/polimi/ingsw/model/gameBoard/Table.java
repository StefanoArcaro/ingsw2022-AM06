package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.enumerations.CreatureColor;

public class Table {

    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 10;

    private final CreatureColor color;
    private int index;

    /**
     * Default constructor.
     * @param color of the table.
     */
    public Table(CreatureColor color) {
        this.color = color;
        this.index = MIN_INDEX;
    }

    /**
     * Adds a student to the table.
     * @return whether the student is added successfully.
     * @throws TableFullException when the table is full.
     */
    public boolean addStudent() throws TableFullException {
        if(index < MAX_INDEX) {
            index = index + 1;
            return true;
        } else {
            throw new TableFullException();
        }
    }

    /**
     * Removes a student from the table.
     * @return whether the removal is successful.
     */
    public boolean removeStudent() {
        if(index > MIN_INDEX) {
            index = index - 1;
            return true;
        }
        return false;
    }

    /**
     * @return the number of students at the table.
     */
    public int getLength(){
        return index;
    }
}
