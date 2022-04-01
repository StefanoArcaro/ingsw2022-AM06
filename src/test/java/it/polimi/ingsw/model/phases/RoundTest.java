package it.polimi.ingsw.model.phases;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.characters.CharacterID;
import it.polimi.ingsw.model.characters.ConcreteCharacterFactory;
import it.polimi.ingsw.model.gameBoard.CreatureColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    ArrayList<Player> players;
    Player player;
    int firstPlayerIndex;
    Round round;
    Assistant assistant;
    Character character;
    ConcreteCharacterFactory cf;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Stefano", PlayerColor.BLACK));
        players.add(new Player("Chiara", PlayerColor.WHITE));
    }

    @AfterEach
    void tearDown() {
        players = null;
        round = null;
    }

    @Test
    void getCurrentPlayer_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Stefano", round.getCurrentPlayer().getNickname());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Chiara", round.getCurrentPlayer().getNickname());
    }

    @Test
    void getCurrentPlayer_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Stefano", round.getCurrentPlayer().getNickname());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Chiara", round.getCurrentPlayer().getNickname());

        firstPlayerIndex = 2;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Nick", round.getCurrentPlayer().getNickname());
    }

    @Test
    void getPlayingOrder_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Stefano", round.getPlayingOrder().get(0).getNickname());
        assertEquals("Chiara", round.getPlayingOrder().get(1).getNickname());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Chiara", round.getPlayingOrder().get(0).getNickname());
        assertEquals("Stefano", round.getPlayingOrder().get(1).getNickname());
    }

    @Test
    void getPlayingOrder_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Stefano", round.getPlayingOrder().get(0).getNickname());
        assertEquals("Chiara", round.getPlayingOrder().get(1).getNickname());
        assertEquals("Nick", round.getPlayingOrder().get(2).getNickname());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Chiara", round.getPlayingOrder().get(0).getNickname());
        assertEquals("Nick", round.getPlayingOrder().get(1).getNickname());
        assertEquals("Stefano", round.getPlayingOrder().get(2).getNickname());

        firstPlayerIndex = 2;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Nick", round.getPlayingOrder().get(0).getNickname());
        assertEquals("Stefano", round.getPlayingOrder().get(1).getNickname());
        assertEquals("Chiara", round.getPlayingOrder().get(2).getNickname());
    }

    @Test
    void getClouds_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);

        assertEquals(1, round.getClouds().get(0).getCloudID());
        assertEquals(2, round.getClouds().get(1).getCloudID());

        assertEquals(0, round.getClouds().get(0).getStudents().size());
        assertEquals(0, round.getClouds().get(1).getStudents().size());
    }

    @Test
    void getClouds_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);

        assertEquals(1, round.getClouds().get(0).getCloudID());
        assertEquals(2, round.getClouds().get(1).getCloudID());
        assertEquals(3, round.getClouds().get(2).getCloudID());

        assertEquals(0, round.getClouds().get(0).getStudents().size());
        assertEquals(0, round.getClouds().get(1).getStudents().size());
        assertEquals(0, round.getClouds().get(2).getStudents().size());
    }

    @Test
    void getPlayerPriority_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getPlayerPriority().size());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getPlayerPriority().size());

        player = players.get(0);
        player.setWizard(new Wizard(player, WizardName.DRUID));
        assistant = player.getWizard().playAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(1, round.getPlayerPriority().size());

        player = players.get(1);
        player.setWizard(new Wizard(player, WizardName.DRUID));
        assistant = player.getWizard().playAssistant(4);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(2, round.getPlayerPriority().size());

        assertTrue(round.getPlayerPriority().containsKey(players.get(0)));
        assertTrue(round.getPlayerPriority().containsKey(players.get(1)));

        assertEquals(7, round.getPlayerPriority().get(players.get(0)).getPriority());
        assertEquals(4, round.getPlayerPriority().get(players.get(1)).getPriority());
    }

    @Test
    void getPlayerPriority_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getPlayerPriority().size());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getPlayerPriority().size());

        firstPlayerIndex = 2;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getPlayerPriority().size());

        player = players.get(0);
        player.setWizard(new Wizard(player, WizardName.SENSEI));
        assistant = player.getWizard().playAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(1, round.getPlayerPriority().size());

        player = players.get(1);
        player.setWizard(new Wizard(player, WizardName.KING));
        assistant = player.getWizard().playAssistant(4);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(2, round.getPlayerPriority().size());

        player = players.get(2);
        player.setWizard(new Wizard(player, WizardName.WITCH));
        assistant = player.getWizard().playAssistant(5);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(3, round.getPlayerPriority().size());

        assertTrue(round.getPlayerPriority().containsKey(players.get(0)));
        assertTrue(round.getPlayerPriority().containsKey(players.get(1)));
        assertTrue(round.getPlayerPriority().containsKey(players.get(2)));

        assertEquals(7, round.getPlayerPriority().get(players.get(0)).getPriority());
        assertEquals(4, round.getPlayerPriority().get(players.get(1)).getPriority());
        assertEquals(5, round.getPlayerPriority().get(players.get(2)).getPriority());
    }

    @Test
    void getFirstPlayer_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Stefano", round.getFirstPlayer().getNickname());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Chiara", round.getFirstPlayer().getNickname());
    }

    @Test
    void getFirstPlayer_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Stefano", round.getFirstPlayer().getNickname());

        firstPlayerIndex = 1;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Chiara", round.getFirstPlayer().getNickname());

        firstPlayerIndex = 2;
        round = new Round(players, firstPlayerIndex);
        assertEquals("Nick", round.getFirstPlayer().getNickname());
    }

    @Test
    void useCharacter_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertNull(round.getActivatedCharacter());

        cf = new ConcreteCharacterFactory();

        character = cf.createCharacter(CharacterID.CHARACTER_NONE.getID());
        round.useCharacter(character);
        assertNull(round.getActivatedCharacter());

        character = cf.createCharacter(CharacterID.CHARACTER_ONE.getID());
        round.useCharacter(character);
        assertEquals(1, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_TWO.getID());
        round.useCharacter(character);
        assertEquals(2, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_THREE.getID());
        round.useCharacter(character);
        assertEquals(3, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_FOUR.getID());
        round.useCharacter(character);
        assertEquals(4, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_FIVE.getID());
        round.useCharacter(character);
        assertEquals(5, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_SIX.getID());
        round.useCharacter(character);
        assertEquals(6, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_SEVEN.getID());
        round.useCharacter(character);
        assertEquals(7, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_EIGHT.getID());
        round.useCharacter(character);
        assertEquals(8, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_NINE.getID());
        round.useCharacter(character);
        assertEquals(9, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_TEN.getID());
        round.useCharacter(character);
        assertEquals(10, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_ELEVEN.getID());
        round.useCharacter(character);
        assertEquals(11, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_TWELVE.getID());
        round.useCharacter(character);
        assertEquals(12, round.getActivatedCharacter().getCharacterID());
    }

    @Test
    void useCharacter_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertNull(round.getActivatedCharacter());

        cf = new ConcreteCharacterFactory();

        character = cf.createCharacter(CharacterID.CHARACTER_NONE.getID());
        round.useCharacter(character);
        assertNull(round.getActivatedCharacter());

        character = cf.createCharacter(CharacterID.CHARACTER_ONE.getID());
        round.useCharacter(character);
        assertEquals(1, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_TWO.getID());
        round.useCharacter(character);
        assertEquals(2, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_THREE.getID());
        round.useCharacter(character);
        assertEquals(3, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_FOUR.getID());
        round.useCharacter(character);
        assertEquals(4, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_FIVE.getID());
        round.useCharacter(character);
        assertEquals(5, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_SIX.getID());
        round.useCharacter(character);
        assertEquals(6, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_SEVEN.getID());
        round.useCharacter(character);
        assertEquals(7, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_EIGHT.getID());
        round.useCharacter(character);
        assertEquals(8, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_NINE.getID());
        round.useCharacter(character);
        assertEquals(9, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_TEN.getID());
        round.useCharacter(character);
        assertEquals(10, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_ELEVEN.getID());
        round.useCharacter(character);
        assertEquals(11, round.getActivatedCharacter().getCharacterID());

        character = cf.createCharacter(CharacterID.CHARACTER_TWELVE.getID());
        round.useCharacter(character);
        assertEquals(12, round.getActivatedCharacter().getCharacterID());
    }

    @Test
    void play() {
    }

    @Test
    void planning() {

    }

    @Test
    void fillClouds_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getClouds().get(0).getStudents().size());
        assertEquals(0, round.getClouds().get(1).getStudents().size());

        round.fillClouds();
        assertEquals(3, round.getClouds().get(0).getStudents().size());
        assertEquals(3, round.getClouds().get(1).getStudents().size());
    }

    @Test
    void fillClouds_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals(0, round.getClouds().get(0).getStudents().size());
        assertEquals(0, round.getClouds().get(1).getStudents().size());
        assertEquals(0, round.getClouds().get(2).getStudents().size());

        round.fillClouds();
        assertEquals(4, round.getClouds().get(0).getStudents().size());
        assertEquals(4, round.getClouds().get(1).getStudents().size());
        assertEquals(4, round.getClouds().get(2).getStudents().size());
    }

    @Test
    void checkAssistantPlayed_choice_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);

        player = players.get(0);
        player.setWizard(new Wizard(player, WizardName.SENSEI));
        assertFalse(round.checkAssistantPlayed(7));
        assistant = player.getWizard().playAssistant(7);
        player.getWizard().removeAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);

        player = players.get(1);
        round.setCurrentPlayer(player);
        player.setWizard(new Wizard(player, WizardName.KING));
        assertTrue(round.checkAssistantPlayed(7));
        assertFalse(round.checkAssistantPlayed(4));
        assistant = player.getWizard().playAssistant(4);
        player.getWizard().removeAssistant(4);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(2, round.getPlayerPriority().size());
    }

    @Test
    void checkAssistantPlayed_no_choice_2P() {
        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);

        player = players.get(0);
        player.setWizard(new Wizard(player, WizardName.SENSEI));
        assertFalse(round.checkAssistantPlayed(7));
        assistant = player.getWizard().playAssistant(7);
        player.getWizard().removeAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);

        player = players.get(1);
        round.setCurrentPlayer(player);
        player.setWizard(new Wizard(player, WizardName.KING));

        player.getWizard().removeAssistant(1);
        player.getWizard().removeAssistant(2);
        player.getWizard().removeAssistant(3);
        player.getWizard().removeAssistant(4);
        player.getWizard().removeAssistant(5);
        player.getWizard().removeAssistant(6);
        player.getWizard().removeAssistant(8);
        player.getWizard().removeAssistant(9);
        player.getWizard().removeAssistant(10);

        assertFalse(round.checkAssistantPlayed(7));
        assistant = player.getWizard().playAssistant(7);
        player.getWizard().removeAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(2, round.getPlayerPriority().size());
    }

    @Test
    void checkAssistantPlayed_choice_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);

        player = players.get(0);
        player.setWizard(new Wizard(player, WizardName.SENSEI));
        assertFalse(round.checkAssistantPlayed(7));
        assistant = player.getWizard().playAssistant(7);
        player.getWizard().removeAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);

        player = players.get(1);
        round.setCurrentPlayer(player);
        player.setWizard(new Wizard(player, WizardName.KING));
        assertTrue(round.checkAssistantPlayed(7));
        assertFalse(round.checkAssistantPlayed(4));
        assistant = player.getWizard().playAssistant(4);
        player.getWizard().removeAssistant(4);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(2, round.getPlayerPriority().size());

        player = players.get(2);
        round.setCurrentPlayer(player);
        player.setWizard(new Wizard(player, WizardName.WITCH));
        assertTrue(round.checkAssistantPlayed(7));
        assertTrue(round.checkAssistantPlayed(4));
        assertFalse(round.checkAssistantPlayed(5));
        assistant = player.getWizard().playAssistant(5);
        player.getWizard().removeAssistant(5);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(3, round.getPlayerPriority().size());
    }

    @Test
    void checkAssistantPlayed_no_choice_3P() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);

        player = players.get(0);
        player.setWizard(new Wizard(player, WizardName.SENSEI));
        assertFalse(round.checkAssistantPlayed(7));
        assistant = player.getWizard().playAssistant(7);
        player.getWizard().removeAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);

        player = players.get(1);
        round.setCurrentPlayer(player);
        player.setWizard(new Wizard(player, WizardName.KING));

        player.getWizard().removeAssistant(1);
        player.getWizard().removeAssistant(2);
        player.getWizard().removeAssistant(3);
        player.getWizard().removeAssistant(5);
        player.getWizard().removeAssistant(6);
        player.getWizard().removeAssistant(8);
        player.getWizard().removeAssistant(9);
        player.getWizard().removeAssistant(10);

        assertTrue(round.checkAssistantPlayed(7));
        assertFalse(round.checkAssistantPlayed(4));
        assistant = player.getWizard().playAssistant(4);
        player.getWizard().removeAssistant(4);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(2, round.getPlayerPriority().size());

        player = players.get(2);
        round.setCurrentPlayer(player);
        player.setWizard(new Wizard(player, WizardName.WITCH));

        player.getWizard().removeAssistant(1);
        player.getWizard().removeAssistant(2);
        player.getWizard().removeAssistant(3);
        player.getWizard().removeAssistant(5);
        player.getWizard().removeAssistant(6);
        player.getWizard().removeAssistant(8);
        player.getWizard().removeAssistant(9);
        player.getWizard().removeAssistant(10);

        assertFalse(round.checkAssistantPlayed(7));
        assistant = player.getWizard().playAssistant(7);
        player.getWizard().removeAssistant(7);
        round.addPlayerPriorityEntry(player, assistant);
        assertEquals(3, round.getPlayerPriority().size());
    }

    @Test
    void updateProfessors() {
        players.add(new Player("Nick", PlayerColor.GRAY));

        Game game = Game.getGame();

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals(5, game.getProfessors().size());
        assertEquals(0, players.get(0).getBoard().getProfessors().size());
        assertEquals(0, players.get(1).getBoard().getProfessors().size());
        assertEquals(0, players.get(2).getBoard().getProfessors().size());

        player = players.get(0);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.GREEN);
        round.updateProfessors();
        assertEquals(3, game.getProfessors().size());
        assertEquals(2, player.getBoard().getProfessors().size());
        assertEquals(0, players.get(1).getBoard().getProfessors().size());
        assertEquals(0, players.get(2).getBoard().getProfessors().size());
        assertTrue(player.getBoard().containsProfessor(CreatureColor.RED));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.GREEN));

        player = players.get(1);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.GREEN);
        player.getBoard().addStudentToHall(CreatureColor.GREEN);
        player.getBoard().addStudentToHall(CreatureColor.BLUE);
        round.updateProfessors();
        assertEquals(2, game.getProfessors().size());
        assertEquals(1, players.get(0).getBoard().getProfessors().size());
        assertEquals(2, player.getBoard().getProfessors().size());
        assertEquals(0, players.get(2).getBoard().getProfessors().size());
        assertTrue(players.get(0).getBoard().containsProfessor(CreatureColor.RED));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.GREEN));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.BLUE));

        player = players.get(2);
        player.getBoard().addStudentToHall(CreatureColor.PINK);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.YELLOW);
        player.getBoard().addStudentToHall(CreatureColor.YELLOW);
        player.getBoard().addStudentToHall(CreatureColor.YELLOW);
        round.updateProfessors();
        assertEquals(0, game.getProfessors().size());
        assertEquals(0, players.get(0).getBoard().getProfessors().size());
        assertEquals(2, players.get(1).getBoard().getProfessors().size());
        assertEquals(3, player.getBoard().getProfessors().size());
        assertTrue(players.get(1).getBoard().containsProfessor(CreatureColor.GREEN));
        assertTrue(players.get(1).getBoard().containsProfessor(CreatureColor.BLUE));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.RED));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.PINK));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.YELLOW));

        cf = new ConcreteCharacterFactory();
        character = cf.createCharacter(CharacterID.CHARACTER_TWO.getID());
        round.useCharacter(character);
        player = players.get(0);
        player.getBoard().addStudentToHall(CreatureColor.GREEN);
        player.getBoard().addStudentToHall(CreatureColor.GREEN);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        player.getBoard().addStudentToHall(CreatureColor.YELLOW);
        player.getBoard().addStudentToHall(CreatureColor.YELLOW);
        player.getBoard().addStudentToHall(CreatureColor.PINK);
        player.getBoard().addStudentToHall(CreatureColor.BLUE);
        player.getBoard().addStudentToHall(CreatureColor.BLUE);
        round.updateProfessors();
        assertEquals(0, game.getProfessors().size());
        assertEquals(4, player.getBoard().getProfessors().size());
        assertEquals(0, players.get(1).getBoard().getProfessors().size());
        assertEquals(1, players.get(2).getBoard().getProfessors().size());
        assertTrue(player.getBoard().containsProfessor(CreatureColor.GREEN));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.RED));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.PINK));
        assertTrue(player.getBoard().containsProfessor(CreatureColor.BLUE));
        assertTrue(players.get(2).getBoard().containsProfessor(CreatureColor.YELLOW));
    }

    /*
    @Test
    void updateProfessor() {
        Game game = Game.getGame();

        firstPlayerIndex = 0;
        round = new Round(players, firstPlayerIndex);
        assertEquals(5, game.getProfessors().size());
        assertEquals(0, players.get(0).getBoard().getProfessors().size());
        assertEquals(0, players.get(1).getBoard().getProfessors().size());

        player = players.get(0);
        player.getBoard().addStudentToHall(CreatureColor.RED);
        round.updateProfessor(CreatureColor.RED);
        assertEquals(4, game.getProfessors().size());
        assertEquals(1, player.getBoard().getProfessors().size());
        assertEquals(0, players.get(1).getBoard().getProfessors().size());
        assertEquals(true, player.getBoard().containsProfessor(CreatureColor.RED));
        assertEquals(false, players.get(1).getBoard().containsProfessor(CreatureColor.RED));

        player = players.get(1);
        round.setCurrentPlayer(player);
        for(int i = 0; i < 10; i++) {
            player.getBoard().addStudentToHall(CreatureColor.RED);
        }
        round.updateProfessor(CreatureColor.RED);
        assertEquals(4, game.getProfessors().size());
        assertEquals(0, players.get(0).getBoard().getProfessors().size());
        assertEquals(1, player.getBoard().getProfessors().size());
        assertEquals(false, players.get(0).getBoard().containsProfessor(CreatureColor.RED));
        assertEquals(true, player.getBoard().containsProfessor(CreatureColor.RED));

        player = players.get(0);
        round.setCurrentPlayer(player);
        for(int i = 0; i < 8; i++) {
            player.getBoard().addStudentToHall(CreatureColor.RED);
        }
        round.updateProfessor(CreatureColor.RED);
        assertEquals(4, game.getProfessors().size());
        assertEquals(0, player.getBoard().getProfessors().size());
        assertEquals(1, players.get(1).getBoard().getProfessors().size());
        assertEquals(false, player.getBoard().containsProfessor(CreatureColor.RED));
        assertEquals(true, players.get(1).getBoard().containsProfessor(CreatureColor.RED));

        player.getBoard().addStudentToHall(CreatureColor.RED);
        round.updateProfessor(CreatureColor.RED);
        assertEquals(4, game.getProfessors().size());
        assertEquals(0, player.getBoard().getProfessors().size());
        assertEquals(1, players.get(1).getBoard().getProfessors().size());
        assertEquals(false, player.getBoard().containsProfessor(CreatureColor.RED));
        assertEquals(true, players.get(1).getBoard().containsProfessor(CreatureColor.RED));

        cf = new ConcreteCharacterFactory();
        character = cf.createCharacter(CharacterID.CHARACTER_TWO.getID());
        round.useCharacter(character);
        round.updateProfessor(CreatureColor.RED);
        assertEquals(4, game.getProfessors().size());
        assertEquals(1, player.getBoard().getProfessors().size());
        assertEquals(0, players.get(1).getBoard().getProfessors().size());
        assertEquals(true, player.getBoard().containsProfessor(CreatureColor.RED));
        assertEquals(false, players.get(1).getBoard().containsProfessor(CreatureColor.RED));
    }
    */
}