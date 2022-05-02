package it.polimi.ingsw.util;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;

import java.util.ArrayList;

public class Constants {

    private static final String SEPARATOR = "\n============================================================================\n";
    
    // Message formats CLIENT -> SERVER
    private static final String LOGIN_FORMAT = "login [nickname] [number of players (2 / 3)] [game mode (0 = EASY / 1 = EXPERT)]";
    private static final String PREPARE_FORMAT = "wizard [DRUID / KING / WITCH / SENSEI]";
    private static final String PLANNING_FORMAT = "assistant [1..10]";
    private static final String MOVE_STUDENT_FORMAT = "movestudent [color from entrance (GREEN / RED / YELLOW / PINK / BLUE)] [destination (0 = hall / 1..12 = island)]";
    private static final String MOVE_MOTHER_NATURE_FORMAT = "movemothernature [number of steps]";
    private static final String PICK_CLOUD_FORMAT = "pickcloud [cloud id]";
    
    private static final String CHARACTER_INFO_FORMAT = "characterinfo [character id (1..12)]";
    private static final String CHARACTER_ONE_FORMAT = "character 1 [color from character] [island id (1..12)]";
    private static final String CHARACTER_TWO_FORMAT = "character 2";
    private static final String CHARACTER_THREE_FORMAT = "character 3 [island group index]";
    private static final String CHARACTER_FOUR_FORMAT = "character 4";
    private static final String CHARACTER_FIVE_FORMAT = "character 5 [island group index]";
    private static final String CHARACTER_SIX_FORMAT = "character 6";
    private static final String CHARACTER_SEVEN_FORMAT = "character 7 [color from character] [color from entrance]";
    private static final String CHARACTER_EIGHT_FORMAT = "character 8";
    private static final String CHARACTER_NINE_FORMAT = "character 9 [color (GREEN / RED / YELLOW / PINK / BLUE)]";
    private static final String CHARACTER_TEN_FORMAT = "character 10 [color form hall] [color from entrance]";
    private static final String CHARACTER_ELEVEN_FORMAT = "character 11 [color from character]";
    private static final String CHARACTER_TWELVE_FORMAT = "character 12 [color (GREEN / RED / YELLOW / PINK / BLUE)]";
    
    private static final String QUIT_FORMAT = "quit";

    private static String charactersDrawn = "";
    
    // Phase instructions
    private static String expertCharacterAction(GameMode gameMode) {
        return gameMode.equals(GameMode.EXPERT) ? "- Ask for characters' info\n\t" + CHARACTER_INFO_FORMAT : "";
    }

    private static final String LOGIN_PHASE_INSTRUCTIONS = SEPARATOR +
            "LOGIN PHASE\n" +
            "The actions you can take are the following:\n" +
            "- Login\n\t" + LOGIN_FORMAT + "\n" +
            "- Quit\n\t" + QUIT_FORMAT;

    private static final String PREPARE_PHASE_INSTRUCTIONS = SEPARATOR +
            "PREPARE PHASE\n" +
            "The actions you can take are the following:\n" +
            "- Choose a wizard\n\t" + PREPARE_FORMAT + "\n" +
            "- Quit\n\t" + QUIT_FORMAT;

    private static String planningPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "PLANNING PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Play an assistant\n\t" + PLANNING_FORMAT + "\n" +
                expertCharacterAction(gameMode) + "\n" +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    private static String moveStudentPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "MOVE STUDENT PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Move a student from your entrance to the hall or one of the islands\n\t" + MOVE_STUDENT_FORMAT + "\n" +
                expertCharacterAction(gameMode) + "\n" +
                charactersDrawn + "\n" +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    private static String moveMotherNaturePhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "MOVE MOTHER NATURE PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Move mother nature a certain amount of steps\n\t" + MOVE_MOTHER_NATURE_FORMAT + "\n" +
                expertCharacterAction(gameMode) + "\n" +
                charactersDrawn + "\n" +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    private static String pickCloudPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "PICK CLOUD PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Choose a cloud from which to get students\n\t" + PICK_CLOUD_FORMAT + "\n" +
                expertCharacterAction(gameMode) + "\n" +
                charactersDrawn + "\n" +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    public static String getPhaseInstructions(GameState gameState, GameMode gameMode) {
        return switch (gameState) {
            case LOBBY_PHASE -> LOGIN_PHASE_INSTRUCTIONS;
            case PREPARE_PHASE -> PREPARE_PHASE_INSTRUCTIONS;
            case PLANNING_PHASE -> planningPhaseInstructions(gameMode);
            case MOVE_STUDENT_PHASE -> moveStudentPhaseInstructions(gameMode);
            case MOVE_MOTHER_NATURE_PHASE -> moveMotherNaturePhaseInstructions(gameMode);
            case PICK_CLOUD_PHASE -> pickCloudPhaseInstructions(gameMode);
            default -> "";
        };
    }

    //set in game
    public static void setDrawnCharacters(ArrayList<Character> characters) {
        String characterDrawn = "- Play a character";

        for(Character character : characters) {
            if(character.getCharacterID() != 0) {
                characterDrawn = characterDrawn + "\n\t" + getCharacterFormat(character.getCharacterID());
            }
        }

        charactersDrawn = characterDrawn;
    }

    private static String getCharacterFormat(int characterID) {
        return switch (characterID) {
            case 1 -> CHARACTER_ONE_FORMAT;
            case 2 -> CHARACTER_TWO_FORMAT;
            case 3 -> CHARACTER_THREE_FORMAT;
            case 4 -> CHARACTER_FOUR_FORMAT;
            case 5 -> CHARACTER_FIVE_FORMAT;
            case 6 -> CHARACTER_SIX_FORMAT;
            case 7 -> CHARACTER_SEVEN_FORMAT;
            case 8 -> CHARACTER_EIGHT_FORMAT;
            case 9 -> CHARACTER_NINE_FORMAT;
            case 10 -> CHARACTER_TEN_FORMAT;
            case 11 -> CHARACTER_ELEVEN_FORMAT;
            case 12 -> CHARACTER_TWELVE_FORMAT;
            default -> "";
        };
    }












}
