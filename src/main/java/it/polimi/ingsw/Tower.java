package it.polimi.ingsw;

public class Tower {
    private TowerColor color;
    private Board board;

    public Tower(TowerColor color, Board board) {
        this.color = color;
        this.board = board;
    }

    public TowerColor getColor() {
        return color;
    }

    public Board getBoard() {
        return board;
    }
}
