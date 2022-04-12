package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration for the possible number of players in a game.
 */
public enum NumberOfPlayers {

    TWO_PLAYERS (2),
    THREE_PLAYERS (3);

    private final int num;

    NumberOfPlayers(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }
}
