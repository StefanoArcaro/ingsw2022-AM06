package it.polimi.ingsw;

import java.util.ArrayList;

public class Wizard {

    private Player player;
    private WizardName name;
    private ArrayList<Assistant> assistants;

    /**
     * Default constructor
     */
    public Wizard() {

    }

    /**
     * @return the player who chose this wizard
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the name of this wizard
     */
    public WizardName getName() {
        return name;
    }

    /**
     * @return a copy of assistants' list
     */
    public ArrayList<Assistant> getAssistants() {
        // TODO
        return null;
    }

    /**
     * Plays the assistant card corresponding to the priority number passed to this method
     * @param priority used to select the chosen Assistant
     * @return the Assistant that corresponds to the priority inputted
     */
    public Assistant playAssistant(int priority) {
        // TODO
        return null;
    }

    // TODO do we need this?
    public void removeAssistant(int priority) {

    }

    /**
     * Checks if no Assistants to be played remain
     */
    private void checkIfNoAssistants() {

    }
}
