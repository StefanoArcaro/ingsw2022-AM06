package it.polimi.ingsw.util;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.GameState;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    // Default network constants
    public static final String IPV4_PATTERN = "^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)";

    public static final String DEFAULT_IP_ADDRESS = "localhost";
    public static final int DEFAULT_PORT = 1234;

    public static final int PING_INITIAL_DELAY = 0;
    public static final int PING_PERIOD = 1000;
    public static final int SOCKET_TIMEOUT = 2000;

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
    private static final String SEPARATOR = "\n━━━━━━━━━━━━━━━━━━ ✦ ━━━━━━━━━━━━━━━━━━\n\n";

    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_YELLOW = "\u001b[33m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_MAGENTA = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_RESET = "\u001b[0m";

    /*
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";
     */

    // Unicode text
    public static final String PROMPT = "\u25b7 ";
    public static final String PROMPT_PHASE = "\u007e";
    public static final String CIRCLE_FULL = "\u25cf";
    public static final String CIRCLE_EMPTY = "\u25ef";
    public static final String DIAMOND_FULL = "\u25c6";
    public static final String DIAMOND_EMPTY = "\u25c7";
    public static final String SQUARE_FULL = "\u25a0";
    public static final String SQUARE_EMPTY = "\u25a1";

    public static String getCircleFullByColor(CreatureColor color) {
        return getAnsiByColor(color) + CIRCLE_FULL + ANSI_RESET;
    }

    public static String getCircleEmptyByColor(CreatureColor color) {
        return getAnsiByColor(color) + CIRCLE_EMPTY + ANSI_RESET;
    }

    public static String getDiamondFullByColor(CreatureColor color) {
        return getAnsiByColor(color) + DIAMOND_FULL + ANSI_RESET;
    }

    public static String getDiamondEmptyByColor(CreatureColor color) {
        return getAnsiByColor(color) + DIAMOND_EMPTY + ANSI_RESET;
    }

    private static String getAnsiByColor(CreatureColor color) {
        String result = "";

        switch (color) {
            case GREEN -> result = result + ANSI_GREEN;
            case RED -> result = result + ANSI_RED;
            case YELLOW -> result = result + ANSI_YELLOW;
            case PINK -> result = result + ANSI_MAGENTA;
            case BLUE -> result = result + ANSI_BLUE;
        }

        return result;
    }

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
    private static final String CHARACTER_TEN_FORMAT = "character 10 [color from hall] [color from entrance]";
    private static final String CHARACTER_ELEVEN_FORMAT = "character 11 [color from character]";
    private static final String CHARACTER_TWELVE_FORMAT = "character 12 [color (GREEN / RED / YELLOW / PINK / BLUE)]";
    
    public static final String QUIT_FORMAT = "quit";

    // Phase instructions
    private static String expertCharacterAction(GameMode gameMode) {
        return gameMode.equals(GameMode.EXPERT) ? "- Ask for characters' info\n\t" + CHARACTER_INFO_FORMAT + "\n" : "";
    }

    private static final String LOGIN_PHASE_INSTRUCTIONS = ANSI_YELLOW + PROMPT_PHASE + " LOGIN PHASE " + PROMPT_PHASE + ANSI_RESET + "\n" +
            "The actions you can take are the following:\n" +
            "- Login\n\t" + LOGIN_FORMAT + "\n\n" +
            "You can always quit the game by typing: " + QUIT_FORMAT;

    private static final String PREPARE_PHASE_INSTRUCTIONS = SEPARATOR +
            ANSI_YELLOW + PROMPT_PHASE + " PREPARE PHASE " + PROMPT_PHASE + ANSI_RESET + "\n" +
            "The actions you can take are the following:\n" +
            "- Choose a wizard\n\t" + PREPARE_FORMAT + "\n";

    private static String planningPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                ANSI_YELLOW + PROMPT_PHASE + " PLANNING PHASE " + PROMPT_PHASE + ANSI_RESET + "\n" +
                "The actions you can take are the following:\n" +
                "- Play an assistant\n\t" + PLANNING_FORMAT + "\n" +
                expertCharacterAction(gameMode);
    }

    private static String moveStudentPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                ANSI_YELLOW + PROMPT_PHASE + " MOVE STUDENT PHASE " + PROMPT_PHASE + ANSI_RESET + "\n" +
                "The actions you can take are the following:\n" +
                "- Move a student from your entrance to the hall or one of the islands\n\t" + MOVE_STUDENT_FORMAT + "\n" +
                expertCharacterAction(gameMode);
    }

    private static String moveMotherNaturePhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                ANSI_YELLOW + PROMPT_PHASE + " MOVE MOTHER NATURE PHASE " + PROMPT_PHASE + ANSI_RESET + "\n" +
                "The actions you can take are the following:\n" +
                "- Move mother nature a certain amount of steps\n\t" + MOVE_MOTHER_NATURE_FORMAT + "\n" +
                expertCharacterAction(gameMode);
    }

    private static String pickCloudPhaseInstructions(GameMode gameMode) {
        return SEPARATOR +
                ANSI_YELLOW + PROMPT_PHASE + " PICK CLOUD PHASE " + PROMPT_PHASE + ANSI_RESET + "\n" +
                "The actions you can take are the following:\n" +
                "- Choose a cloud from which to get students\n\t" + PICK_CLOUD_FORMAT + "\n" +
                expertCharacterAction(gameMode);
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

    public static String getCharacterFormat(int characterID) {
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

    // Characters' descriptions
    private static final String CHARACTER_ONE_DESCRIPTION = """
            Take 1 Student from this card and place it on
            an Island of your choice.
            """;

    private static final String CHARACTER_TWO_DESCRIPTION = """
            During this turn, you take control of any
            number of Professors even if you have the same
            number of Students as the player who currently
            controls them.
            """;

    private static final String CHARACTER_THREE_DESCRIPTION = """
            Choose an Island and resolve the Island as if
            Mother Nature had ended her movement there. Mother
            Nature will still move and the Island where she ends
            her movement will also be resolved.
            """;

    private static final String CHARACTER_FOUR_DESCRIPTION = """
            You may move Mother Nature up to 2
            additional Islands than is indicated by the Assistant
            card you've played.
            """;

    private static final String CHARACTER_FIVE_DESCRIPTION = """
            In Setup, put the 4 Ban Cards on this card.
            Place a Ban Card on an Island Group of your choice.
            The first time Mother Nature ends her movement
            there, the Ban Card is removed and the influence is not calculated in that Island Group.
            """;

    private static final String CHARACTER_SIX_DESCRIPTION = """
            When resolving a Conquering on an Island,
            Towers do not count towards influence.
            """;

    private static final String CHARACTER_SEVEN_DESCRIPTION = """
            You may take up to 3 Students from this card
            and replace them with the same number of Students
            from your Entrance.
            """;

    private static final String CHARACTER_EIGHT_DESCRIPTION = """
            During the influence calculation this turn, you
            count as having 2 more influence.
            """;

    private static final String CHARACTER_NINE_DESCRIPTION = """
            Choose a color of Student: during the influence
            calculation this turn, that color adds no influence.
            """;

    private static final String CHARACTER_TEN_DESCRIPTION = """
            You may exchange up to 2 Students between
            your Entrance and your Hall.
            """;

    private static final String CHARACTER_ELEVEN_DESCRIPTION = """
            Take 1 Student from this card and place it in
            your Dining Room. Then, draw a new Student from the
            Bag and place it on this card.
            """;

    private static final String CHARACTER_TWELVE_DESCRIPTION = """
            Choose a type of Student: every player
            (including yourself) must return 3 Students of that type
            from their Dining Room to the bag. If any player has
            fewer than 3 Students of that type, return as many
            Students as they have.
            """;

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

    //Listeners
    public static final String ISLAND_GROUPS_LISTENER = "islandGroupsListener";
    public static final String BOARD_LISTENER = "boardListener";
    public static final String PHASE_LISTENER = "phaseListener";
    public static final String COIN_LISTENER = "coinListener";
    public static final String ISLAND_LISTENER = "islandListener";
    public static final String ASSISTANTS_AVAILABLE_LISTENER = "assistantsAvailableListener";
    public static final String ASSISTANT_PLAYED_LISTENER = "assistantPlayedListener";
    public static final String WIN_LISTENER = "winListener";
    public static final String PLAYER_LISTENER = "playerListener";
    public static final String CHARACTER_DRAWN_LISTENER = "characterDrawnListener";
    public static final String CHARACTER_PLAYED_LISTENER = "characterPlayedListener";
    public static final String CLOUD_LISTENER = "cloudListener";
    public static final String GAME_READY_LISTENER = "gameReadyListener";

    public static final String FX_CLOUD_LISTENER = "fxCloudListener";
    public static final String FX_WIZARD_LISTENER = "fxWizardListener";
    public static final String FX_BAN_CARD_LISTENER = "fxBanCardListener";

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

    //GUI
    public static final String MENU = "MainMenu.fxml";
    public static final String SETUP = "setup.fxml";
    public static final String LOGIN = "login.fxml";
    public static final String LOBBY = "lobby.fxml";
    public static final String WIZARD = "wizard.fxml";
    public static final String BOARD_AND_ISLANDS = "boardANDislands.fxml";
    public static final String ASSISTANTS = "playAssistant.fxml";
    public static final String MOTHER_NATURE = "moveMotherNature.fxml";
    public static final String OPPONENT_BOARD_1 = "opponentBoard1.fxml";
    public static final String OPPONENT_BOARD_2 = "opponentBoard2.fxml";
    public static final String CHARACTERS = "playCharacter.fxml";
    public static final String CHARACTER_DETAILS = "characterDetails.fxml";

    public static final ArrayList<String> SCENES = new ArrayList<>(
            Arrays.asList(Constants.MENU,
                    Constants.SETUP,
                    Constants.LOGIN,
                    Constants.LOBBY,
                    Constants.WIZARD,
                    Constants.BOARD_AND_ISLANDS,
                    Constants.ASSISTANTS,
                    Constants.MOTHER_NATURE,
                    Constants.OPPONENT_BOARD_1,
                    Constants.OPPONENT_BOARD_2,
                    Constants.CHARACTERS,
                    Constants.CHARACTER_DETAILS
            ));


}
