package it.polimi.ingsw.model;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterID;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.gameBoard.Bag;
import it.polimi.ingsw.model.gameBoard.Cloud;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import it.polimi.ingsw.model.gameBoard.Professor;

import java.util.*;
import java.util.stream.Collectors;

// TODO modify methods' access modifiers after thorough testing
public class Round {

    private static final int FIRST_PLAYER_INDEX = 0;

    private Player currentPlayer;
    private ArrayList<Player> playingOrder;
    private final ArrayList<Cloud> clouds;
    private final HashMap<Player, Assistant> playerPriority;
    private final Bag bag;
    private Character activatedCharacter;

    /**
     * Default constructor
     */
    public Round(ArrayList<Player> players, int firstPlayerIndex) {
        currentPlayer = players.get(firstPlayerIndex);
        playingOrder = new ArrayList<>();
        clouds = new ArrayList<>();
        playerPriority = new HashMap<>();
        bag = Bag.getBag();
        activatedCharacter = new ConcreteCharacterFactory().createCharacter(CharacterID.CHARACTER_NONE.getID());

        for(int i = 0; i < players.size(); i++) {
            playingOrder.add(players.get((i + firstPlayerIndex) % players.size())); // TODO check
            clouds.add(new Cloud(i + 1));
        }
    }

    /**
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Used for testing purposes
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the list of players, in the order of playing
     */
    public ArrayList<Player> getPlayingOrder() {
        return new ArrayList<>(playingOrder);
    }

    /**
     * @return the list of cloud cards
     */
    public ArrayList<Cloud> getClouds() {
        return new ArrayList<>(clouds);
    }

    /**
     * @return the player-assistant map
     */
    public HashMap<Player, Assistant> getPlayerPriority() {
        return new HashMap<>(playerPriority);
    }

    // Used for testing purposes
    public void addPlayerPriorityEntry(Player player, Assistant assistant) {
        playerPriority.put(player, assistant);
    }

    /**
     * @return the player who played the Assistant card with the lowest priority number
     */
    public Player getFirstPlayer() {
        return playingOrder.get(FIRST_PLAYER_INDEX);
    }

    /**
     * @return active character - null if there's none active
     */
    public Character getActivatedCharacter() {
        return activatedCharacter;
    }

    // TODO check
    /**
     * Activate a certain character
     * @param character to be activated
     */
    public void useCharacter(Character character) {
        activatedCharacter = character;
    }

    /**
     * Manages the round's execution
     */
    public void play() {
        planning();
        action();
    }

    /**
     * Manages the planning phase of the round
     */
    public void planning() {
        int priority;
        Assistant assistant;

        fillClouds();

        // Modifications for testing purposes until client input
        int tempPriorities[] = {2, 3, 1};

        //for(Player player : playingOrder) {
        for(int i = 0; i < playingOrder.size(); i++) {
            //currentPlayer = player;
            currentPlayer = playingOrder.get(i);

            do {
                priority = tempPriorities[i]; // TODO receive input
            } while (checkAssistantPlayed(priority));

            assistant = currentPlayer.getWizard().playAssistant(priority);
            currentPlayer.getWizard().removeAssistant(priority);
            playerPriority.put(currentPlayer, assistant);
        }

        playingOrder = playerPriority.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Manages the action phase of the round
     */
    private void action() { // TODO check if it should do more
        actionPhaseOne();
        actionPhaseTwo();
        actionPhaseThree();
    }

    /**
     * Fills the cloud cards with the correct number of students
     */
    public void fillClouds() {
        for(Cloud cloud : clouds) {
            cloud.fill(bag, playingOrder.size());
        }
    }

    /**
     * Checks if the currentPlayer's chosen Assistant can be played or not
     * @param priority of the Assistant that the currentPlayer wants to play
     * @return whether the Assistant can be played or not
     */
    public boolean checkAssistantPlayed(int priority) {
        List<Integer> priorities = playerPriority.values()
                .stream()
                .map(Assistant::getPriority)
                .collect(Collectors.toList());

        List<Integer> assistantsToPlay = currentPlayer.getWizard().getAssistants()
                .stream()
                .map(Assistant::getPriority)
                .collect(Collectors.toList());

        if(priorities.contains(priority)) {
            if(priorities.containsAll(assistantsToPlay)) {
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Manages the first part of the action phase
     * (moving a certain number of students)
     */
    private void actionPhaseOne() {

    }

    /**
     * Manages the second part of the action phase
     * (move Mother Nature and check the island groups' state)
     */
    private void actionPhaseTwo() {

    }

    /**
     * Manages the third part of the action phase
     * (choice of the cloud card to empty)
     */
    private void actionPhaseThree() {

    }

    /**
     * Updates the owner of each professor
     */
    public void updateProfessors() {
        for(CreatureColor color: CreatureColor.values()) {
            updateProfessor(color);
        }
    }

    /**
     * Updates the owner of a certain professor
     * @param color of the professor whose owner might be in need of an update
     */
    public void updateProfessor(CreatureColor color) {
        int oldOwnerInfluence, maxInfluence = 0, maxInfluenceIndex = -1;
        Player owner = null;

        for(int i = 0; i < playingOrder.size(); i++) {
            if(playingOrder.get(i).getBoard().containsProfessor(color)) {
                owner = playingOrder.get(i);
                oldOwnerInfluence = owner.getBoard().getTableByColor(color).getLength();
                maxInfluence = oldOwnerInfluence;
                maxInfluenceIndex = i;
                break;
            }
        }

        for(int i = 0; i < playingOrder.size(); i++) {
            int tempInfluence = playingOrder.get(i).getBoard().getTableByColor(color).getLength();
            boolean characterEffectForCurrentPlayer = false;
            if(activatedCharacter != null) {
                characterEffectForCurrentPlayer =
                        playingOrder.get(i).equals(currentPlayer) &&
                        activatedCharacter.getCharacterID() == CharacterID.CHARACTER_TWO.getID();
            }

            if(tempInfluence > maxInfluence || (characterEffectForCurrentPlayer && tempInfluence >= maxInfluence)) {
                maxInfluence = tempInfluence;
                maxInfluenceIndex = i;
            }
        }

        // TODO check if owner == null; if so, take professor from game set of profs
        if(owner != null) {
            if(!playingOrder.get(maxInfluenceIndex).equals(owner)) {
                Professor professorToUpdate = owner.getBoard().loseProfessorByColor(color);
                playingOrder.get(maxInfluenceIndex).getBoard().winProfessor(professorToUpdate);
            }
        } else if(maxInfluenceIndex != -1) {
            Game.getGame().getProfessors();
            Professor professorToUpdate = Game.getGame().removeProfessor(color);
            playingOrder.get(maxInfluenceIndex).getBoard().winProfessor(professorToUpdate);
        }
    }
}
