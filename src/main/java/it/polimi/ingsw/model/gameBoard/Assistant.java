package it.polimi.ingsw.model.gameBoard;

import it.polimi.ingsw.model.characters.Character;

public class Assistant implements Comparable<Assistant> {

    private final int priority;
    private final int maxSteps;

    /**
     * Default constructor.
     */
    public Assistant(int priority, int maxSteps) {
        this.priority = priority;
        this.maxSteps = maxSteps;
    }

    /**
     * @return this assistant card's priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param activatedCharacter character of class CharacterStepsAdder which is active.
     * @return the maximum number of steps Mother Nature will be allowed to take.
     */
    public int getMaxSteps(Character activatedCharacter) {
        return maxSteps + activatedCharacter.getMoreSteps();
    }

    @Override
    public int compareTo(Assistant assistant) {
        return (this.priority - assistant.priority);
    }
}
