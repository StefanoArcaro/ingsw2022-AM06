package it.polimi.ingsw;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IslandGroupTest {

    private IslandGroup islandGroup;
    private Island island1;
    private Island island2;
    private Island island3;
    private Island island4;

    @BeforeEach
    void setUp() {
        islandGroup = new IslandGroup();
    }

    @AfterEach
    void tearDown() {
        islandGroup = null;
    }

    @Test
    void getIslands() {
        ArrayList<Island> expectedIslands = new ArrayList<Island>();
        island1 = new Island(1);
        island2 = new Island(2);

        //Test no island
        assertEquals(expectedIslands, islandGroup.getIslands());

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        expectedIslands.add(island1);
        expectedIslands.add(island2);

        //Test 2 islands
        assertEquals(expectedIslands, islandGroup.getIslands());
    }

    @Test
    void getNumberOfIsland() {
        assertEquals(0, islandGroup.getNumberOfIsland());

        island1 = new Island(1);
        island2 = new Island(2);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);

        assertEquals(2, islandGroup.getNumberOfIsland());
    }

    @Test
    void addIsland_OK() {
        Island islandToAdd1 = new Island(4);
        Island islandToAdd2 = new Island(1);

        island2 = new Island(2);
        island3 = new Island(3);

        assertEquals(true,islandGroup.addIsland(island2));
        assertEquals(true,islandGroup.addIsland(island3));

        assertEquals(true, islandGroup.addIsland(islandToAdd1));
        assertEquals(true, islandGroup.addIsland(islandToAdd2));
        assertEquals(false, islandGroup.addIsland(island1));
    }

    @Test
    void addIsland_KO() {
        Island islandToAdd = new Island(6);
        Island islandToAdd2 = new Island(1);

        island4 = new Island(4);
        island3 = new Island(3);

        assertEquals(true,islandGroup.addIsland(island3));
        assertEquals(true,islandGroup.addIsland(island4));

        assertEquals(false, islandGroup.addIsland(islandToAdd));
        assertEquals(false, islandGroup.addIsland(islandToAdd2));

    }

    @Test
    void addIsland_Boundary() {
        Island islandToAdd = new Island(12);
        Island islandToAdd2 = new Island(11);

        island1 = new Island(1);
        island2 = new Island(2);

        assertEquals(true,islandGroup.addIsland(island1));
        assertEquals(true,islandGroup.addIsland(island2));

        assertEquals(true, islandGroup.addIsland(islandToAdd));
        assertEquals(true, islandGroup.addIsland(islandToAdd2));

    }

    @Test
    void addIsland_Boundary2() {
        island1 = new Island(1);
        island2 = new Island(2);
        Island island12 = new Island(12);
        Island islandToAdd = new Island(11);

        assertEquals(true,islandGroup.addIsland(island1));
        assertEquals(true,islandGroup.addIsland(island2));
        assertEquals(true,islandGroup.addIsland(island12));

        assertEquals(true,islandGroup.addIsland(islandToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(11, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void addIsland_Boundary3() {
        island1 = new Island(1);
        island2 = new Island(2);
        Island island12 = new Island(12);

        assertEquals(true,islandGroup.addIsland(island1));
        assertEquals(true,islandGroup.addIsland(island2));
        assertEquals(true,islandGroup.addIsland(island12));

        Island islandToAdd = new Island(3);
        assertEquals(true,islandGroup.addIsland(islandToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
    }

    @Test
    void isBanCardPresent() {
        assertEquals(false, islandGroup.isBanCardPresent());

        islandGroup.addBanCard();
        assertEquals(true, islandGroup.isBanCardPresent());
    }

    @Test
    void addBanCard() {
        assertEquals(true, islandGroup.addBanCard());
        assertEquals(false, islandGroup.addBanCard());
    }

    @Test
    void connectIslandGroupOK() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island3 = new Island(3);
        island4 = new Island(4);

        islandGroupToAdd.addIsland(island3);
        islandGroupToAdd.addIsland(island4);

        islandGroup.addIsland(island1);
        islandGroup.addIsland(island2);


        assertEquals(true, islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroupOK2() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island3 = new Island(3);
        island4 = new Island(4);

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island3);
        islandGroup.addIsland(island4);


        assertEquals(true, islandGroup.connectIslandGroup(islandGroupToAdd));

        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(4, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroupKO() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island1 = new Island(1);
        island2 = new Island(2);
        island4 = new Island(4);

        islandGroupToAdd.addIsland(island1);
        islandGroupToAdd.addIsland(island2);

        islandGroup.addIsland(island4);

        assertEquals(false, islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(4, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(1, islandGroup.getNumberOfIsland());
    }

    @Test
    void connectIslandGroup_Boundary() {
        IslandGroup islandGroupToAdd = new IslandGroup();
        island2 = new Island(2);
        island3 = new Island(3);
        islandGroupToAdd.addIsland(island2);
        islandGroupToAdd.addIsland(island3);

        Island island12 = new Island(12);
        island1 = new Island(1);
        islandGroup.addIsland(island12);
        islandGroup.addIsland(island1);

        assertEquals(true, islandGroup.connectIslandGroup(islandGroupToAdd));
        assertEquals(1, islandGroup.getIslands().get(0).getIslandID());
        assertEquals(2, islandGroup.getIslands().get(1).getIslandID());
        assertEquals(3, islandGroup.getIslands().get(2).getIslandID());
        assertEquals(12, islandGroup.getIslands().get(3).getIslandID());
        assertEquals(4, islandGroup.getNumberOfIsland());
    }



    @Test
    void calculateInfluence() {
    }
}