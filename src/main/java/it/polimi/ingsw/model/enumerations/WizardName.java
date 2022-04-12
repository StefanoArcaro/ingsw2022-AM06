package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration of the available wizards' names.
 */
public enum WizardName {

    DRUID (0),
    KING (1),
    WITCH (2),
    SENSEI (3);

    private final int id;

    WizardName(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
