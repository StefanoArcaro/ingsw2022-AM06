package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.enumerations.CreatureColor;

public interface StudentDestination {

    boolean receiveStudent(CreatureColor color) throws TableFullException;

}
