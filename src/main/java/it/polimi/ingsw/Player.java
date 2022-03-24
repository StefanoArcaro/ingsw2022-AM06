package it.polimi.ingsw;

import java.util.ArrayList;

public class Player {

    private String nickname;
    private PlayerColor color;
    private Board board;
    private Wizard wizard;
    private int coins;

    /**
     * Default constructor
     */
    public Player() {

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
