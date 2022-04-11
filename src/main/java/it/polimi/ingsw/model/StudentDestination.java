package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.TableFullException;
import it.polimi.ingsw.model.gameBoard.CreatureColor;

public interface StudentDestination {

    boolean receiveStudent(CreatureColor color) throws TableFullException;

}
