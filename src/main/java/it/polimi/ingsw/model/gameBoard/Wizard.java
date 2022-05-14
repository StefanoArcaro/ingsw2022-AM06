package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.WizardName;

import java.util.ArrayList;

public class Wizard {

    private static final int MIN_ASSISTANT_PRIORITY = 1;
    private static final int MAX_ASSISTANT_PRIORITY = 10;

    private final Player player;
    private final WizardName name;
    private final ArrayList<Assistant> assistants;

    /**
     * Default constructor.
     */
    public Wizard(Player player, WizardName name) {
        this.player = player;
        this.name = name;
        assistants = new ArrayList<>();
        for(int i = MIN_ASSISTANT_PRIORITY; i <= MAX_ASSISTANT_PRIORITY; i++) {
            assistants.add(new Assistant(i, (i + 1) / 2));
        }
    }

    /**
     * @return the player who chose this wizard.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the name of this wizard.
     */
    public WizardName getName() {
        return name;
    }

    /**
     * @return a copy of assistants' list.
     */
    public ArrayList<Assistant> getAssistants() {
        return new ArrayList<>(assistants);
    }

    /**
     * Returns the assistant in the deck that has a given priority.
     * @param priority to search the assistant.
     * @return assistant with the given priority.
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
     * Plays the assistant card corresponding to the priority number passed to this method.
     * @param priority used to select the chosen Assistant.
     * @return the Assistant that corresponds to the priority inputted.
     */
    public Assistant playAssistant(int priority) {
        return getAssistantByPriority(priority);
    }

    /**
     * Removes assistant from the deck.
     * @param priority identifies the assistant to remove.
     * @return true if everything went well.
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
     * Checks if no Assistants to be played remain.
     * @return whether there are no assistants.
     */
    public boolean checkIfNoAssistants() {
        return assistants.size() <= 0;
    }

}
