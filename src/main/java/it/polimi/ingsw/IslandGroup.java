package it.polimi.ingsw;

import java.util.ArrayList;

public class IslandGroup {
    private ArrayList<Island> islands;
    private BanCard banCard;

    public int calculateInfluence(Player player){return 0;}
    public void addIsland (Island island){}
    public IslandGroup connectIslandGroup(IslandGroup islandGroup){return null;}
    private void checkNumberOfIslandGroups(){}
    public void addBanCard(BanCard banCard){}

    public IslandGroup(ArrayList<Island> islands, BanCard banCard) {
        this.islands = islands;
        this.banCard = banCard;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public BanCard getBanCard() {
        return banCard;
    }
}




