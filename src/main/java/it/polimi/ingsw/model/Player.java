package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.PlayerColor;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.model.gameBoard.Board;
import it.polimi.ingsw.model.gameBoard.Wizard;

public class Player {

    private final Game game;
    private final String nickname;
    private final PlayerColor color;
    private final Board board;
    private Wizard wizard;
    private int coins;

    /**
     * Default constructor.
     */
    public Player(Game game, String nickname, PlayerColor color) {
        this.game = game;
        this.nickname = nickname;
        this.color = color;
        this.board = new Board(this);
        this.coins = 0;
    }

    /**
     * @return game the player is in.
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return the player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the player's color.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * @return the player's board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the Wizard chosen by the player.
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     * Assigns to the player the Wizard they chose.
     * @param wizardName name of the Wizard chosen by the player.
     */
    public void setWizard(WizardName wizardName) {
        this.wizard = new Wizard(this, wizardName);
    }

    /**
     * @return the number of coins in the player's possession.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Adds 1 coin to the player's coins.
     */
    public void receiveCoin() {
        this.coins += 1;
    }

    /**
     * Subtracts the specified number of coins from the player's coins
     * if the player has enough of them.
     * @param coinsToSpend number of coins to be subtracted.
     */
    public void spendCoins(int coinsToSpend) {
        if(this.coins >= coinsToSpend) {
            this.coins -= coinsToSpend;
        }
    }
}
