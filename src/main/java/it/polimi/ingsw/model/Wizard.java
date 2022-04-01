package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Wizard {

    private Player player;
    private WizardName name;
    private ArrayList<Assistant> assistants;

    /**
     * Default constructor
     */
    public Wizard(Player player, WizardName name) {
        this.player = player;
        this.name = name;
        assistants = new ArrayList<>();
        for(int i = 1; i < 11; i++) {
            assistants.add(new Assistant(i, (i + 1) / 2));
        }
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
        return new ArrayList<>(assistants);
    }

    /**
     * Returns the assistant in the deck that has a given priority
     * @param priority to search the assistant
     * @return assistant with the given priority
     */
    private Assistant getAssistantByPriority(int priority) {
        for(Assistant assistant : assistants) {
            if(assistant.getPriority() == priority) {
                return assistant;
            }
        }
        return null;
    }

    /**
     * Plays the assistant card corresponding to the priority number passed to this method
     * @param priority used to select the chosen Assistant
     * @return the Assistant that corresponds to the priority inputted
     */
    public Assistant playAssistant(int priority) {
        return getAssistantByPriority(priority);
    }

    /**
     * Removes assistant from the deck
     * @param priority identifies the assistant to remove
     * @return true if everything went well
     */
    public boolean removeAssistant(int priority) {
        Assistant assistantToRemove = getAssistantByPriority(priority);
        if(assistants.contains(assistantToRemove)) {
            assistants.remove(assistantToRemove);
            return true;
        }
        return false;
    }

    /**
     * Checks if no Assistants to be played remain
     * @return true if no assistants
     */
    public boolean checkIfNoAssistants() {
        return assistants.size() <= 0;
    }
}
