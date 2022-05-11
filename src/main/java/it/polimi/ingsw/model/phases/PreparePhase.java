package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.exceptions.InvalidWizardException;
import it.polimi.ingsw.exceptions.WizardTakenException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.util.Constants;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class PreparePhase extends Phase {

    private static final int MIN_WIZARD_ID = 0;
    private static final int MAX_WIZARD_ID = 3;

    private static final int TWO_PLAYERS_TOWERS = 8;
    private static final int THREE_PLAYERS_TOWERS = 6;

    private static final int FIRST_ISLAND_ID = 1;
    private static final int LAST_ISLAND_ID = 12;
    private static final int ISLAND_OFFSET = 6;

    private static final int TWO_PLAYERS_ENTRANCE_STUDENTS = 7;
    private static final int THREE_PLAYERS_ENTRANCE_STUDENTS = 9;

    private static final int NUMBER_OF_CHARACTERS = 12;
    private static final int CHARACTERS_TO_DRAW = 3;

    private static final int TOTAL_COINS = 20;

    private int turns;

    /**
     * Default constructor.
     * @param game reference to the game.
     */
    public PreparePhase(Game game) {
        this.game = game;
        this.phaseFactory = new PhaseFactory(game);
        this.turns = 0;
    }

    /**
     * Prepare phase of the game.
     * It 'sets the table' in order to begin playing.
     */
    @Override
    public void play() throws WizardTakenException, InvalidWizardException {
        assignWizard();

        if(turns == game.getNumberOfPlayers().getNum()) {
            islandGroupSetup();
            int motherNaturePosition = placeMotherNature();
            placeInitialStudents(motherNaturePosition);
            initialBagFill();
            instantiateClouds();
            instantiateProfessors();
            instantiateTowers();
            initializeEntranceStudents();

            setDefaultCharacter();

            if(game.getGameMode().equals(GameMode.EXPERT)) {
                drawCharacters();
                distributeCoins();
            }

            String oldPlayer = game.getCurrentPlayer().getNickname();
            drawFirstPlayer();
            game.getListeners().firePropertyChange(Constants.GAME_STARTED_LISTENER, null, game);
            game.getListeners().firePropertyChange(Constants.PLAYER_LISTENER, oldPlayer, game.getCurrentPlayer().getNickname());

            game.setGameState(GameState.PLANNING_PHASE);
            game.setCurrentPhase(phaseFactory.createPhase(game.getGameState()));
            game.getListeners().firePropertyChange(Constants.PHASE_LISTENER, null, new AbstractMap.SimpleEntry<>(game.getGameState(), game.getGameMode()));

            game.getListeners().firePropertyChange(Constants.ASSISTANTS_AVAILABLE_LISTENER, null, game.getCurrentPlayer().getWizard().getAssistants()); //todo fix other players
        }
    }

    /**
     * Pairs a wizard with each player.
     * The wizard must be unique to the player.
     */
    private void assignWizard() throws WizardTakenException, InvalidWizardException {
        WizardName wizardName;

        if(checkValidWizardID(wizardID)) {
            if(!checkWizardTaken(wizardID)) {
                turns += 1;
                wizardName = getWizardNameByID(wizardID);
                game.getCurrentPlayer().setWizard(wizardName);

                if(turns < game.getNumberOfPlayers().getNum()) {
                    game.setCurrentPlayer(game.getNextPlayer());
                    game.getListeners().firePropertyChange(Constants.PLAYER_LISTENER, null, game.getCurrentPlayer().getNickname());
                }
            } else {
                throw new WizardTakenException();
            }
        } else {
            throw new InvalidWizardException();
        }
    }

    /**
     * Checks if the inputted ID corresponds to a valid wizard.
     * @param wizardID to check.
     * @return whether the ID is a correct one.
     */
    private boolean checkValidWizardID(int wizardID) {
        return wizardID >= MIN_WIZARD_ID && wizardID <= MAX_WIZARD_ID;
    }

    /**
     * Checks whether a wizard has already been chosen by another player.
     * @param wizardID to be checked.
     * @return whether the wizard has been chosen already.
     */
    private boolean checkWizardTaken(int wizardID) {
        for(Player player : game.getPlayers()) {
            if(player.getWizard() != null) {
                if(player.getWizard().getName().getId() == wizardID) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the wizard corresponding to such ID, null if it doesn't exist.
     * @param wizardID of the wizard to return.
     * @return the wizard whose ID is the same as the inputted parameter.
     */
    private WizardName getWizardNameByID(int wizardID) {
        for(WizardName wizardName : WizardName.values()) {
            if(wizardName.getId() == wizardID) {
                return wizardName;
            }
        }

        return null;
    }

    /**
     * Initializes the 12 islands and the beginning island groups.
     */
    private void islandGroupSetup() {
        for(int i = FIRST_ISLAND_ID; i <= LAST_ISLAND_ID; i++) {
            Island island = new Island(i);
            IslandGroup islandGroup = new IslandGroup();
            islandGroup.addIsland(island);
            game.addIslandGroup(islandGroup);
        }
    }

    /**
     * Randomly places Mother Nature on one of the initial island groups.
     * @return the index of the island group Mother Nature was placed on.
     */
    private int placeMotherNature() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(LAST_ISLAND_ID);

        IslandGroup initialIslandGroup = game.getIslandGroups().get(randomIndex);
        game.getMotherNature().setCurrentIslandGroup(initialIslandGroup);

        return randomIndex;
    }

    /**
     * Places two students of each color on the islands (one student for each island) except for
     * the island Mother Nature was placed on and the one on the opposite side.
     * @param motherNaturePosition the index of the island group Mother Nature was placed on.
     */
    private void placeInitialStudents(int motherNaturePosition) {
        Stack<Student> initialStudents = new Stack<>();

        for(CreatureColor color : CreatureColor.values()) {
            initialStudents.add(new Student(color));
            initialStudents.add(new Student(color));
        }

        Collections.shuffle(initialStudents);

        for(int i = 0; i < game.getIslandGroups().size(); i++) {
            if(i != motherNaturePosition && i != ((motherNaturePosition + ISLAND_OFFSET) % LAST_ISLAND_ID)) {
                game.getIslandGroups().get(i).getIslands().get(0).receiveStudent(initialStudents.pop().getColor());
            }
        }
    }

    /**
     * Fills the bag with the rest of the students and shuffles it.
     */
    private void initialBagFill() {
        for(CreatureColor color : CreatureColor.values()) {
            for(int i = 0; i < 24; i++) {
                game.getBag().receiveStudent(color);
            }
        }
    }

    /**
     * Instantiates the cloud cards ('numberOfPlayers' many).
     */
    private void instantiateClouds() {
        for(int i = 0; i < game.getNumberOfPlayers().getNum(); i++) {
            game.addCloud(new Cloud(i + 1));
        }
    }

    /**
     * Instantiates a professor for each color.
     */
    private void instantiateProfessors() {
        for(CreatureColor color : CreatureColor.values()) {
            game.addProfessor(new Professor(color));
        }
    }

    /**
     * Instantiates a certain number of towers for each player.
     * 2P Mode -> 8 towers
     * 3P Mode -> 6 towers
     */
    private void instantiateTowers() {
        int numberOfTowers = game.getNumberOfPlayers().equals(NumberOfPlayers.TWO_PLAYERS)
                ? TWO_PLAYERS_TOWERS : THREE_PLAYERS_TOWERS;

        for(Player player: game.getPlayers()) {
            player.getBoard().setTowers(numberOfTowers);
        }
    }

    /**
     * Prepares the initial number of students each player should have on
     * their board at the beginning of the game.
     * 2P -> 7 students
     * 3P -> 9 students
     */
    private void initializeEntranceStudents() {
        for(Player player : game.getPlayers()) {
            int numberOfEntranceStudents = switch (game.getNumberOfPlayers()) {
                case TWO_PLAYERS -> TWO_PLAYERS_ENTRANCE_STUDENTS;
                case THREE_PLAYERS -> THREE_PLAYERS_ENTRANCE_STUDENTS;
            };

            for(int i = 0; i < numberOfEntranceStudents; i++) {
                Student studentToMove = game.getBag().drawStudent();
                player.getBoard().getEntrance().receiveStudent(studentToMove.getColor());
            }
        }
    }


    /**
     * Randomly selects one of the players to be the first to play.
     */
    private void drawFirstPlayer() {
        Random random = new Random();
        int firstPlayerIndex = random.nextInt(game.getNumberOfPlayers().getNum());
        game.setFirstPlayerIndex(firstPlayerIndex);
        game.setCurrentPlayer(game.getPlayers().get(firstPlayerIndex));
    }

    /**
     * Initializes the game's active character to be the CharacterNone.
     */
    private void setDefaultCharacter() {
        ConcreteCharacterFactory characterFactory = new ConcreteCharacterFactory(game);
        Character defaultCharacter = characterFactory.createCharacter(CharacterID.CHARACTER_NONE.getID());

        game.addDrawnCharacter(defaultCharacter);
        game.setActivatedCharacter(defaultCharacter);
    }

    /**
     * Randomly selects 3 characters from the total of 12.
     * Also calls the initialPreparation() method for each of them, in case
     * that some characters picked have some kind of effect to be activated
     * before the game begins.
     */
    private void drawCharacters() {
        Random random = new Random();
        ConcreteCharacterFactory characterFactory = new ConcreteCharacterFactory(game);
        int randomCharacterID;

        for(int i = 0; i < CHARACTERS_TO_DRAW; i++) {
            do {
                // The '+ 1' is for offsetting CHARACTER_NONE, which is null
                randomCharacterID = random.nextInt(NUMBER_OF_CHARACTERS) + 1;
            } while(checkCharacterDrawn(randomCharacterID));

            Character characterToAdd = characterFactory.createCharacter(randomCharacterID);
            game.addDrawnCharacter(characterToAdd);

            game.getListeners().firePropertyChange(Constants.CHARACTER_DRAWN_LISTENER, null, characterToAdd);

            characterToAdd.initialPreparation();
        }

        // Set Constants
        Constants.setDrawnCharacters(game.getDrawnCharacters());
        Constants.setPlayCharacters(game.getDrawnCharacters());
    }

    /**
     * Checks whether a certain character has already been selected to be
     * part of the 3 for the game.
     * @param characterID ID of the character to be checked.
     * @return whether the character has already been selected.
     */
    private boolean checkCharacterDrawn(int characterID) {
        for(Character character : game.getDrawnCharacters()) {
            if(character.getCharacterID() == characterID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Each player receives 1 coin at the beginning of the game.
     * The rest of the coins end up in the game 'treasury'.
     */
    private void distributeCoins() {
        for(Player player : game.getPlayers()) {
            player.receiveCoin();
        }

        game.setTreasury(TOTAL_COINS - game.getNumberOfPlayers().getNum());
    }
}
