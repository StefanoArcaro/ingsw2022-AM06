package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    @Test
    void getMotherNature() {
        MotherNature motherNature = MotherNature.getMotherNature();
        assertNotNull(motherNature);
        assertEquals(motherNature, MotherNature.getMotherNature());
    }

    @Test
    void setCurrentIslandGroup() {
        MotherNature motherNature = MotherNature.getMotherNature();
        assertNull(motherNature.getCurrentIslandGroup());
        IslandGroup islandGroup=new IslandGroup();
        motherNature.setCurrentIslandGroup(islandGroup);
        assertNotNull(motherNature.getCurrentIslandGroup());
    }
}