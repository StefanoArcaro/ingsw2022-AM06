package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    MotherNature motherNature;

    @BeforeEach
    void setUp() {
        MotherNature.getMotherNature().resetMotherNature();
        motherNature = MotherNature.getMotherNature();
    }

    @Test
    void getMotherNature() {
        MotherNature motherNature = MotherNature.getMotherNature();
        assertNotNull(motherNature);
        assertEquals(motherNature, MotherNature.getMotherNature());
    }

    @Test
    void setCurrentIslandGroup() {
        assertNull(motherNature.getCurrentIslandGroup());
        IslandGroup islandGroup=new IslandGroup();
        motherNature.setCurrentIslandGroup(islandGroup);
        assertNotNull(motherNature.getCurrentIslandGroup());
    }
}