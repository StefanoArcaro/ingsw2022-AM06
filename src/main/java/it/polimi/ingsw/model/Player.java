package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Island;

public class Player {

    private String nickname;
    private PlayerColor color;
    private Board board;
    private Wizard wizard;
    private int coins;

    /**
     * Default constructor
     */
    public Player(String nickname, PlayerColor color) {
        this.nickname = nickname;
        this.color = color;
        this.board = new Board(this);
        /*if(gameMode == GameMode.EXPERT){
            coins = 20;
        }*/
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    /**
     * Lets player select the number of players for the game
     */
    public void chooseNumberOfPlayers() {

    }

    /**
     * Lets player select the game mode
     */
    public void chooseGameMode() {

    }

    /**
     * Lets player choose one of the available wizards
     */
    public void chooseWizard() {

    }

    /**
     * @return player's color
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * @return player's board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Lets player choose the color of a creature
     * (student or professor)
     * @return the color chosen by the player
     */
    public CreatureColor chooseColor() {
        // TODO
        return null;
    }

    /**
     * Lets player choose the destination of a student
     * (the hall or an island)
     * @return the destination chosen by the player
     */
    public String chooseStudentDestination() {
        // TODO
        return null;
    }

    /**
     * Lets player select one of the islands
     * @return the island selected by the player
     */
    public Island chooseIsland() {
        // TODO
        return null;
    }


}
