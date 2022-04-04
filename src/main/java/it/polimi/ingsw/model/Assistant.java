package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.CharacterStepsAdder;

public class Assistant implements Comparable<Assistant> {

    private final int priority;
    private final int maxSteps;

    /**
     * Default constructor
     */
    public Assistant(int priority, int maxSteps) {
        this.priority = priority;
        this.maxSteps = maxSteps;
    }

    /**
     * @return this Assistant card's priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return the maximum number of steps Mother Nature will be allowed
     *         to take if this Assistant card is played
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Overloading
     * @param activatedCharacter character of class CharacterStepsAdder which is active
     * @return the maximum number of steps Mother Nature will be allowed to take
     */
    public int getMaxSteps(CharacterStepsAdder activatedCharacter) {
        return maxSteps + activatedCharacter.getMoreSteps();
    }

    @Override
    public int compareTo(Assistant assistant) {
        return (this.priority - assistant.priority);
    }
}
