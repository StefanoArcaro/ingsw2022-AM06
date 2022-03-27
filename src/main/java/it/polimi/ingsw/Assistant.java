package it.polimi.ingsw;

public class Assistant {

    private boolean playable;
    private int priority;
    private int maxSteps;
    private Wizard wizard;

    /**
     * Default constructor
     */
    public Assistant() {
        this.playable = true;
    }

    /**
     * @return whether this Assistant card is playable or not
     */
    public boolean isPlayable() {
        return playable;
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
     * overloading
     * @param activatedCharacter character of class CharacterStepsAdder which is active
     * @return the maximum number of steps Mother Nature will be allowed to take
     */
    public int getMaxSteps(CharacterStepsAdder activatedCharacter){
        return maxSteps+activatedCharacter.getMoreSteps();
    }

    /**
     * @return the Wizard whose Assistant this is
     */
    public Wizard getWizard() {
        return wizard;
    }
}
