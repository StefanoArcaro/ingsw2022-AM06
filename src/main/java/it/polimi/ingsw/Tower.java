package it.polimi.ingsw;

public class Tower {
    private TowerColor color;
    private Board board;
    private Island island;

    public Tower(TowerColor color, Board board) {
        this.color = color;
        this.board = board;
        this.island = null;
    }

    public TowerColor getColor() {
        return color;
    }

    public Board getBoard() {
        return board;
    }

    public Island getIsland() {
        return island;
    }
}
