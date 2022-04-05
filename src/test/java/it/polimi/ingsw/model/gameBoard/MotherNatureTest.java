package it.polimi.ingsw.model.gameBoard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    MotherNature motherNature;

    @BeforeEach
    void setUp() {
        motherNature = new MotherNature();
    }

    @AfterEach
    void tearDown() {
        motherNature = null;
    }

    @Test
    void setCurrentIslandGroup() {
        assertNull(motherNature.getCurrentIslandGroup());
        IslandGroup islandGroup = new IslandGroup();
        motherNature.setCurrentIslandGroup(islandGroup);
        assertNotNull(motherNature.getCurrentIslandGroup());
    }
}