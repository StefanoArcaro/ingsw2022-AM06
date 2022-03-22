package it.polimi.ingsw;

import java.util.ArrayList;

public class CharacterFiveDecorator extends CharacterDecorator {

    protected final int characterID = 5;
    protected final String name = "Character5";
    protected final int cost = 2;

    private ArrayList<BanCard> banCards;

    @Override
    public void initialPreparation() {

    }

    /**
     * add banCard on this card (at the beginning of the game or after use)
     * @param banCard that i have to move from island to the card
     */
    private void addBanCard(BanCard banCard) {

    }

    /**
     * move banCard to a chosen island
     * @param islandGroup chosen on which to move the bancard
     */
    private void useBanCard(IslandGroup islandGroup) {

    }

    @Override
    public void effect() {

    }
}
