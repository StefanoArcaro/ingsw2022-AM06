package it.polimi.ingsw.util;

import it.polimi.ingsw.model.characters.Character;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Constants {

    // Default network constants
    public static final String IPV4_PATTERN = "^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)";

    public static final String DEFAULT_IP_ADDRESS = "localhost";
    public static final int DEFAULT_PORT = 1234;

    public static final int PING_INITIAL_DELAY = 0;
    public static final int PING_PERIOD = 2000;
    public static final int SOCKET_TIMEOUT = 5000;

    // Text util
    public static final String ERIANTYS = "\n" +Constants.BOX_TOP + "\n" +
        "  ███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗" + "\n" +
        "  ██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝" + "\n" +
        "  █████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗" + "\n" +
        "  ██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║" + "\n" +
        "  ███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║" + "\n" +
        "  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝" + "\n" +
        Constants.BOX_BOTTOM + "\n";

    private static final String BOX_TOP = "╔═════════════════════════════════════════════════════════════════╗";
    private static final String BOX_BOTTOM = "╚═════════════════════════════════════════════════════════════════╝";

    public static final String ANSI_BLACK = "\u001b[30m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_YELLOW = "\u001b[33m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_MAGENTA = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE = "\u001b[37m";
    public static final String ANSI_RESET = "\u001b[0m";

    private static final String SEPARATOR = "\n==================================================================\n\n";

    // Message formats CLIENT -> SERVER
    public static final String LOGIN_FORMAT = "login [nickname] [number of players (2 / 3)] [game mode (0 = EASY / 1 = EXPERT)]";
    public static final String PREPARE_FORMAT = "wizard [DRUID / KING / WITCH / SENSEI]";
    public static final String PLANNING_FORMAT = "assistant [1..10]";
    public static final String MOVE_STUDENT_FORMAT = "movestudent [color from entrance (GREEN / RED / YELLOW / PINK / BLUE)] [destination (0 = hall / 1..12 = island)]";
    public static final String MOVE_MOTHER_NATURE_FORMAT = "movemothernature [number of steps]";
    public static final String PICK_CLOUD_FORMAT = "pickcloud [cloud id]";
    
    public static final String CHARACTER_INFO_FORMAT = "characterinfo [character id (1..12)]";
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
    
    public static final String QUIT_FORMAT = "quit";

    private static String charactersDrawn = "- Drawn characters:";

    private static String playCharacters = "- Play a character";
    
    // Phase instructions
    private static String expertCharacterAction(GameMode gameMode) {
        return gameMode.equals(GameMode.EXPERT) ? "- Ask for characters' info\n\t" + CHARACTER_INFO_FORMAT + "\n" : "";
    }

    private static String expertCharactersDrawn(GameMode gameMode) {
        return gameMode.equals(GameMode.EXPERT) ? charactersDrawn + "\n" : "";
    }

    private static String expertPlayCharacters(GameMode gameMode) {
        return gameMode.equals(GameMode.EXPERT) ? playCharacters + "\n" : "";
    }

    private static final String LOGIN_PHASE_INSTRUCTIONS = ANSI_GREEN + "LOGIN PHASE\n" + ANSI_RESET +
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
                expertCharacterAction(gameMode) +
                expertCharactersDrawn(gameMode) +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    private static String moveStudentPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "MOVE STUDENT PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Move a student from your entrance to the hall or one of the islands\n\t" + MOVE_STUDENT_FORMAT + "\n" +
                expertCharacterAction(gameMode) +
                expertPlayCharacters(gameMode) +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    private static String moveMotherNaturePhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "MOVE MOTHER NATURE PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Move mother nature a certain amount of steps\n\t" + MOVE_MOTHER_NATURE_FORMAT + "\n" +
                expertCharacterAction(gameMode) +
                expertPlayCharacters(gameMode) +
                "- Quit\n\t" + QUIT_FORMAT;
    }

    private static String pickCloudPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                "PICK CLOUD PHASE\n" +
                "The actions you can take are the following:\n" +
                "- Choose a cloud from which to get students\n\t" + PICK_CLOUD_FORMAT + "\n" +
                expertCharacterAction(gameMode) +
                expertPlayCharacters(gameMode) +
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

    public static void setDrawnCharacters(ArrayList<Character> characters) {
        for(Character character : characters) {
            if(character.getCharacterID() != 0) {
                charactersDrawn = charactersDrawn + " " + character.getCharacterID();
            }
        }
    }

    //set in game
    public static void setPlayCharacters(ArrayList<Character> characters) {
        for(Character character : characters) {
            if(character.getCharacterID() != 0) {
                playCharacters = playCharacters + "\n\t" + getCharacterFormat(character.getCharacterID());
            }
        }
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

    // characters descriptions
    private static final String CHARACTER_ONE_DESCRIPTION = "Take 1 Student from this card and place it on\n" +
            "an Island of your choice\n";

    private static final String CHARACTER_TWO_DESCRIPTION = "During this turn, you take control of any\n" +
            "number of Professors even if you have the same\n" +
            "number of Students as the player who currently\n" +
            "controls them.\n";

    private static final String CHARACTER_THREE_DESCRIPTION = "Choose an Island and resolve the Island as if\n" +
            "Mother Nature had ended her movement there. Mother\n" +
            "Nature will still move and the Island where she ends\n" +
            "her movement will also be resolved.\n";

    private static final String CHARACTER_FOUR_DESCRIPTION = "You may move Mother Nature up to 2\n" +
            "additional Islands than is indicated by the Assistant\n" +
            "card you've played.\n";

    private static final String CHARACTER_FIVE_DESCRIPTION = "In Setup, put the 4 Ban Cards on this card.\n" +
            "Place a Ban Card on an Island Group of your choice.\n" +
            "The first time Mother Nature ends her movement\n" +
            "there, the Ban Card is removed and the influence is not calculated in that Island Group.\n";

    private static final String CHARACTER_SIX_DESCRIPTION = "When resolving a Conquering on an Island,\n" +
            "Towers do not count towards influence.\n";

    private static final String CHARACTER_SEVEN_DESCRIPTION = "You may take up to 3 Students from this card\n" +
            "and replace them with the same number of Students\n" +
            "from your Entrance.\n";

    private static final String CHARACTER_EIGHT_DESCRIPTION = "During the influence calculation this turn, you\n" +
            "count as having 2 more influence.\n";

    private static final String CHARACTER_NINE_DESCRIPTION = "Choose a color of Student: during the influence\n" +
            "calculation this turn, that color adds no influence.\n";

    private static final String CHARACTER_TEN_DESCRIPTION = "You may exchange up to 2 Students between\n" +
            "your Entrance and your Hall.\n";

    private static final String CHARACTER_ELEVEN_DESCRIPTION = "Take 1 Student from this card and place it in\n" +
            "your Dining Room. Then, draw a new Student from the\n" +
            "Bag and place it on this card.\n";

    private static final String CHARACTER_TWELVE_DESCRIPTION = "Choose a type of Student: every player\n" +
            "(including yourself) must return 3 Students of that type\n" +
            "from their Dining Room to the bag. If any player has\n" +
            "fewer than 3 Students of that type, return as many\n" +
            "Students as they have.\n";

    public static String getCharacterInformation(int characterID) {
        return switch (characterID) {
            case 1 -> CHARACTER_ONE_DESCRIPTION;
            case 2 -> CHARACTER_TWO_DESCRIPTION;
            case 3 -> CHARACTER_THREE_DESCRIPTION;
            case 4 -> CHARACTER_FOUR_DESCRIPTION;
            case 5 -> CHARACTER_FIVE_DESCRIPTION;
            case 6 -> CHARACTER_SIX_DESCRIPTION;
            case 7 -> CHARACTER_SEVEN_DESCRIPTION;
            case 8 -> CHARACTER_EIGHT_DESCRIPTION;
            case 9 -> CHARACTER_NINE_DESCRIPTION;
            case 10 -> CHARACTER_TEN_DESCRIPTION;
            case 11 -> CHARACTER_ELEVEN_DESCRIPTION;
            case 12 -> CHARACTER_TWELVE_DESCRIPTION;
            default -> "";
        };
    }

    public static void countdown(int milliseconds) {
        for(int i = 3; i > 0; i--) {
            System.out.print(".");
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                System.err.println("There's been an error with the thread management");
                System.err.println("The application will now close...");
                System.exit(-1);
            }
        }
        System.out.println("\n");
    }
}
