package it.polimi.ingsw.network.message.serverToclient;

import it.polimi.ingsw.model.gameBoard.Island;
import it.polimi.ingsw.model.gameBoard.IslandGroup;
import it.polimi.ingsw.model.gameBoard.MotherNature;
import it.polimi.ingsw.model.gameBoard.Student;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;

/**
 * Message used by the server to display the island groups and their information.
 */
public class IslandGroupsMessage extends Answer {

    private final ArrayList<IslandGroup> islandGroup;
    private final int motherNatureIndex;

    public IslandGroupsMessage(ArrayList<IslandGroup> islandGroup, int motherNatureIndex) {
        super(MessageType.ISLAND_GROUPS_MESSAGE);
        this.islandGroup = islandGroup;
        this.motherNatureIndex = motherNatureIndex;
    }

    public String getMessage() {
        String islands = "\n";
        int islandGroupIndex = 0;

        for(IslandGroup iG : islandGroup) {
            int islandGroupId = islandGroupIndex + 1;
            islands = islands + "Island group " + islandGroupId + " composed by islands: \n";

            for(Island i : iG.getIslands()) {
                islands = "\t" + islands + i.getIslandID() + " -> ";
                for(Student s : i.getStudents()) {
                    islands = islands + s.getColor().getColorName() + " ";
                }
            }
            islands = islands + "\n";


            if(iG.getConquerorColor() != null) {
                islands = islands + "\tConquered by -> " + iG.getConquerorColor() + "\n";
            }

            if(iG.getNumberOfBanCardPresent() > 0) {
                islands = islands + "\tThere are " + iG.getNumberOfBanCardPresent() + " ban card on this island group.\n";
            }

            if(motherNatureIndex == islandGroupIndex){
                islands = islands + "Mother nature is on this island group!\n";
            }

            islands = islands + "\n";

            islandGroupIndex += 1;
        }

        return islands;
    }

}
