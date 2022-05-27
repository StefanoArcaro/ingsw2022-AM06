package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.gameBoard.*;
import it.polimi.ingsw.network.client.MessageParser;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.message.serverToclient.*;
import it.polimi.ingsw.util.Constants;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class for the Command Line Interface client.
 * This is executed when the client chooses the CLI option at the beginning of the game.
 */
public class CLI implements View {

    public static final int ENTRANCE_2P = 7;
    public static final int ENTRANCE_3P = 9;
    public static final int MAX_TOWERS_2P = 8;
    public static final int MAX_TOWERS_3P = 6;

    private SocketClient socketClient;
    private final ExecutorService keyboardQueue;
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);
    private final ModelView modelView;

    /**
     * Default constructor.
     */
    public CLI() {
        this.keyboardQueue = Executors.newSingleThreadExecutor();
        this.modelView = new ModelView();
    }

    /**
     * @return the socket attribute of this client.
     */
    @Override
    public SocketClient getSocketClient() {
        return this.socketClient;
    }

    /**
     * Sets the socket attribute of this client to the specified one.
     * @param socketClient socket to set.
     */
    @Override
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    /**
     * Adds a listener to the CLI.
     * @param propertyName name of the observed property of the CLI.
     * @param listener listener added to the CLI.
     */
    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        this.listener.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Listen asynchronously for keyboard input from the client.
     * When input is received, this method passes it to the parser so that it
     * can be sent as a message to the server, if the format is correct.
     */
    public void listenToKeyboard() {
        keyboardQueue.execute(() -> {
            while(!keyboardQueue.isShutdown()) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                listener.firePropertyChange("MessageParser", null, message);
            }
        });
    }

    /**
     * Handles the LoginReplyMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void loginReplyHandler(LoginReplyMessage msg) {
        System.out.println(msg.getMessage());
        modelView.setNickname(msg.getNickname());
        modelView.setNumberOfPlayers(msg.getNumberOfPlayers().getNum());
        modelView.setGameMode(msg.getGameMode());
    }

    /**
     * Handles the GameStartedMessage sent by the server.
     */
    @Override
    public void gameStartingHandler() {

    }

    /**
     * Handles the GameReadyMessage sent by the server.
     */
    @Override
    public void gameReadyHandler() {

    }

    /**
     * Handles the WizardsAvailableMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void wizardsHandler(WizardsAvailableMessage msg) {
        ArrayList<WizardName> wizardNames = msg.getWizards();
        StringBuilder wizards = new StringBuilder("Available wizards:");

        for(WizardName wizard : wizardNames) {
            wizards.append(" ").append(wizard.getName());
        }

        System.out.println(wizards);
    }

    /**
     * Handles the AssistantsMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void assistantsHandler(AssistantsMessage msg) {
        ArrayList<Assistant> assistants = msg.getAssistants();
        StringBuilder assistantString = new StringBuilder();

        for(Assistant assistant : assistants) {
            assistantString.append("\t-> Priority = ").append(assistant.getPriority()).append(" - Number of steps = ").append(assistant.getNumberOfSteps()).append("\n");
        }

        System.out.println(assistantString);
    }

    /**
     * Handles the ActivePlayersMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void activePlayersHandler(ActivePlayersMessage msg) {
        modelView.setPlayers(msg.getActivePlayers());
        showActivePlayers();
    }

    /**
     * Displays the active players on the console.
     */
    public void showActivePlayers() {
        Map<String, PlayerColor> players = modelView.getPlayers();

        StringBuilder playersInGame = new StringBuilder("Active players:\n");
        for(Map.Entry<String, PlayerColor> p : players.entrySet()) {
            playersInGame.append("\t- ").append(p.getKey()).append(" -> ").append(p.getValue().getColorName());
            if(p.getKey().equals(modelView.getNickname())) {
                playersInGame.append(" (You)");
            }
            playersInGame.append("\n");
        }
        System.out.println(playersInGame + "\n");
    }

    /**
     * Handles the BoardMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void boardHandler(BoardMessage msg) {
        modelView.setBoard(msg);
        showBoard(msg.getNickname());
    }

    /**
     * Displays the players' boards horizontally.
     */
    public void showBoards() {
        ArrayList<String> players = modelView.getPlayersSorted();

        if(players.size() > 0) {
            // Names
            String owners = showOwners(players);

            // Entrances
            String entrances = showEntrances(players);

            // Halls
            String halls = showHalls(players);

            // Towers
            String towers = showTowers(players);

            System.out.println(owners + entrances + halls + towers);
        }
    }

    /**
     * @param players list of the active players.
     * @return the string of the players' names.
     */
    public String showOwners(ArrayList<String> players) {
        StringBuilder owners = new StringBuilder();

        for(String p : players) {
            if(p.equals(modelView.getNickname())) {
                owners.append("Your board").append("\t\t\t\t\t\t");
            } else {
                owners.append(p).append("'s board").append("\t\t\t\t\t\t");
            }
        }

        owners.append("\n");

        return owners.toString();
    }

    /**
     * @param players list of the active players.
     * @return the string of the players' entrances.
     */
    public String showEntrances(ArrayList<String> players) {
        int maxStudents = modelView.getNumberOfPlayers() ==
                NumberOfPlayers.TWO_PLAYERS.getNum() ? ENTRANCE_2P : ENTRANCE_3P;

        StringBuilder entrances = new StringBuilder();

        for(String p : players) {
            Board board = modelView.getBoard(p);
            entrances.append("Entrance:");
            int i = 0;
            for(Student student : board.getEntrance().getStudents()) {
                entrances.append(" ").append(Constants.getCircleFullByColor(student.getColor()));
                i++;
            }
            while(i < maxStudents) {
                entrances.append(" ").append(Constants.CIRCLE_EMPTY);
                i++;
            }
            entrances.append("\t\t");

            if(maxStudents < ENTRANCE_3P) {
                entrances.append("\t");
            }
        }

        entrances.append("\n");

        return entrances.toString();
    }

    /**
     * @param players list of the active players.
     * @return the string of the players' halls.
     */
    public String showHalls(ArrayList<String> players) {
        ArrayList<Board> boards = new ArrayList<>();

        for(String p: players) {
            boards.add(modelView.getBoard(p));
        }

        StringBuilder halls = new StringBuilder();

        // "Hall"s
        for(String ignored : players) {
            halls.append("Hall:").append("\t\t\t\t\t\t\t");
        }
        halls.append("\n");

        // Tables
        for(CreatureColor color: CreatureColor.values()) {
            for(Board b : boards) {
                int i;
                for(i = 0; i < b.getHall().getTableByColor(color).getLength(); i++) {
                    halls.append(Constants.getCircleFullByColor(color)).append(" ");
                }
                while(i < 10) {
                    halls.append(Constants.getCircleEmptyByColor(color)).append(" ");
                    i++;
                }

                halls.append("| ");

                boolean found = false;
                for(Professor p : b.getProfessors()) {
                    if(p.getColor().equals(color)) {
                        halls.append(Constants.getDiamondFullByColor(color));
                        found = true;
                    }
                }
                if(!found) {
                    halls.append(Constants.getDiamondEmptyByColor(color));
                }

                halls.append("\t\t\t");
            }

            halls.append("\n");
        }

        return halls.toString();
    }

    /**
     * @param players list of the active players.
     * @return the string of the players' towers.
     */
    public String showTowers(ArrayList<String> players) {
        StringBuilder towers = new StringBuilder();

        int maxTowers = modelView.getNumberOfPlayers() ==
                NumberOfPlayers.TWO_PLAYERS.getNum() ? MAX_TOWERS_2P : MAX_TOWERS_3P;

        for(String p : players) {
            int actualTowers = modelView.getBoard(p).getTowers();
            int i;

            towers.append("Towers:");

            for(i = 0; i < actualTowers; i++) {
                towers.append(" ").append(Constants.SQUARE_FULL);
            }

            for(i = actualTowers; i < maxTowers; i++) {
                towers.append(" ").append(Constants.SQUARE_EMPTY);
            }

            if(i < MAX_TOWERS_2P) {
                towers.append("\t");
            }

            towers.append("\t\t\t");
        }

        towers.append("\n");

        return towers.toString();
    }

    /**
     * Displays the coins of all the active players.
     * @param players list of the active players.
     */
    public void showCoins(ArrayList<String> players) {
        StringBuilder coins = new StringBuilder();

        for(String p : players) {
            int numberOfCoins = modelView.getCoinsByNickname(p);

            if(numberOfCoins > 0) {
                if(p.equals(modelView.getNickname())) {
                    coins.append("Your coins:");
                } else {
                    coins.append(p).append("'s coins:");
                }

                for(int i = 0; i < numberOfCoins; i++) {
                    coins.append(" ").append(Constants.CIRCLE_FULL);
                }

            } else {
                if(p.equals(modelView.getNickname())) {
                    coins.append("You have no coins");
                } else {
                    coins.append(p).append(" has no coins");
                }
            }

            coins.append("\n");
        }

        System.out.println(coins);
    }

    /**
     * Displays the board of the player with the specified nickname to the console.
     * @param nickname nickname of the player.
     */
    public void showBoard(String nickname) {
        Board board = modelView.getBoard(nickname);

        // Names
        String owner = (nickname.equals(modelView.getNickname()) ? "Your" : nickname + "'s") + " board.\n";

        // Entrance
        String entrance = showEntrance(board);

        // Hall & professors
        String hall = showHall(board);

        // Towers
        String towers = showTowers(board);

        System.out.println(owner + entrance + hall + towers + "\n");
    }

    /**
     * Creates a string representation of the specified board's entrance.
     * @param board whose entrance is displayed.
     * @return the formatted string.
     */
    public String showEntrance(Board board) {
        StringBuilder entranceStudents = new StringBuilder("Entrance:");
        for(Student student : board.getEntrance().getStudents()) {
            entranceStudents.append(" ").append(Constants.getCircleFullByColor(student.getColor()));
        }
        entranceStudents.append("\n");

        return entranceStudents.toString();
    }

    /**
     * Creates a string representation of the specified board's hall.
     * @param board whose hall is displayed.
     * @return the formatted string.
     */
    public String showHall(Board board) {
        StringBuilder hallStudents = new StringBuilder("Hall:\n");
        for(Table table : board.getHall().getStudents()) {
            int i;
            for(i = 0; i < table.getLength(); i++) {
                hallStudents.append(Constants.getCircleFullByColor(table.getColor())).append(" ");
            }
            while(i < 10) {
                hallStudents.append(Constants.getCircleEmptyByColor(table.getColor())).append(" ");
                i++;
            }

            hallStudents.append("| ");

            boolean found = false;
            for(Professor p : board.getProfessors()) {
                if(p.getColor().equals(table.getColor())) {
                    hallStudents.append(Constants.getDiamondFullByColor(table.getColor()));
                    found = true;
                }
            }
            if(!found) {
                hallStudents.append(Constants.getDiamondEmptyByColor(table.getColor()));
            }

            hallStudents.append("\n");
        }

        return hallStudents.toString();
    }

    /**
     * Creates a string representation of the specified board's towers.
     * @param board whose towers are displayed.
     * @return the formatted string.
     */
    public String showTowers(Board board) {
        int maxTowers = modelView.getNumberOfPlayers() ==
                NumberOfPlayers.TWO_PLAYERS.getNum() ? MAX_TOWERS_2P : MAX_TOWERS_3P;
        int actualTowers = board.getTowers();

        StringBuilder towers = new StringBuilder("Towers:");
        for(int i = 0; i < actualTowers; i++) {
            towers.append(" ").append(Constants.SQUARE_FULL);
        }

        for(int i = actualTowers; i < maxTowers; i++) {
            towers.append(" ").append(Constants.SQUARE_EMPTY);
        }

        towers.append("\n");

        return towers.toString();
    }

    /**
     * Displays the coins a player has.
     * @param coins StringBuilder to be modified and returned.
     * @param numberOfCoins the number of coins the player has.
     */
    public void showCoins(StringBuilder coins, int numberOfCoins) {
        for(int i = 0; i < numberOfCoins; i++) {
            coins.append(" ").append(Constants.CIRCLE_FULL);
        }
    }

    /**
     * Handles the IslandGroupsMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void islandGroupsHandler(IslandGroupsMessage msg) {
        modelView.setIslandGroups(msg.getIslandGroups(), msg.getMotherNatureIndex());
        showIslandGroups();
    }

    /**
     * Displays the island groups' state.
     */
    public void showIslandGroups() {
        ArrayList<IslandGroup> islandGroups = modelView.getIslandGroups();
        int islandGroupIndex = 0;

        StringBuilder islands = new StringBuilder("\n");
        for(IslandGroup iG : islandGroups) {
            int islandGroupId = islandGroupIndex + 1;
            islands.append("Island group ").append(islandGroupId).append(" composed by islands:\n");

            for(Island i : iG.getIslands()) {
                islands = new StringBuilder("\t" + islands + i.getIslandID() + " ->");
                for(Student s : i.getStudents()) {
                    islands.append(" ").append(Constants.getCircleFullByColor(s.getColor()));
                }
            }
            islands.append("\n");

            if(iG.getConquerorColor() != null) {
                islands.append(Constants.ANSI_GREEN).append("Conquered by -> ").append(modelView.getPlayerByColor(iG.getConquerorColor())).append(Constants.ANSI_RESET).append("\n");
            }

            if(iG.getNumberOfBanCardPresent() > 0) {
                islands.append(Constants.ANSI_YELLOW).append("There are ").append(iG.getNumberOfBanCardPresent()).append(" ban card(s) on this island group.").append(Constants.ANSI_RESET).append("\n");
            }

            if(modelView.getMotherNatureIndex() == islandGroupIndex){
                islands.append(Constants.ANSI_CYAN).append("Mother nature is on this island group!\n").append(Constants.ANSI_RESET);
            }

            islands.append("\n");

            islandGroupIndex += 1;
        }

        System.out.println(islands);
    }

    /**
     * Handles the IslandMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void islandHandler(IslandMessage msg) {
        modelView.setIsland(msg.getIsland());
        showIsland(msg.getIsland().getIslandID());
    }

    /**
     * Displays the status of the island whose ID corresponds to the specified one.
     * @param islandID the ID of the island to display.
     */
    public void showIsland(int islandID) {
        Island island = modelView.getIsland(islandID);

        StringBuilder message = new StringBuilder("Island " + island.getIslandID() + ":");
        for(Student s : island.getStudents()) {
            message.append(" ").append(Constants.getCircleFullByColor(s.getColor()));
        }
        message.append("\n");

        if(island.getTower() != null) {
            message.append(Constants.ANSI_GREEN).append("\t\tConquered by -> ").append(modelView.getPlayerByColor(island.getTower())).append(Constants.ANSI_RESET).append("\n");
        }

        System.out.println(message);
    }

    /**
     * Handles the CloudsAvailableMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void cloudsAvailableHandler(CloudsAvailableMessage msg) {
        modelView.setClouds(msg.getClouds());

        if(!modelView.getCurrentPhase().equals("Planning phase")) {
            showAvailableClouds();
        }
    }

    /**
     * Displays the available cloud cards with their respective students.
     */
    public void showAvailableClouds() {
        ArrayList<Cloud> clouds = modelView.getClouds();
        StringBuilder message = new StringBuilder("Available clouds\n");

        for(Cloud c : clouds) {
            if(!c.isEmpty()) {
                message.append("- Cloud ").append(c.getCloudID()).append(":");
                for (Student s : c.getStudents()) {
                    message.append(" ").append(Constants.getCircleFullByColor(s.getColor()));
                }
                message.append("\n");
            }
        }

        System.out.println(message);
    }

    /**
     * Handles the CloudChosenMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void cloudChosenHandler(CloudChosenMessage msg) {
         showChosenCloud(msg.getCloud());
    }

    /**
     * Displays the specified cloud with its students.
     * @param cloud to display.
     */
    public void showChosenCloud(Cloud cloud) {
        StringBuilder message = new StringBuilder(modelView.getCurrentPlayer() + "has chosen cloud " + cloud.getCloudID() + ":");

        for (Student s : cloud.getStudents()) {
            message.append(" ").append(Constants.getCircleFullByColor(s.getColor()));
        }
        message.append("\n");

        System.out.println(message);
    }

    /**
     * Handles the CoinMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void coinsHandler(CoinMessage msg) {
        modelView.setCoins(msg);
        int numberOfCoins = modelView.getCoinsByNickname(msg.getNickname());

        if(numberOfCoins > 0) {
            StringBuilder coins = new StringBuilder();
            if(msg.getNickname().equals(modelView.getNickname())) {
                coins.append("Your coins:");
            } else {
                coins.append(msg.getNickname()).append("'s coins:");
            }
            showCoins(coins, numberOfCoins);
            System.out.println(coins);
        } else {
            if(msg.getNickname().equals(modelView.getNickname())) {
                System.out.println("You have no coins left.");
            } else {
                System.out.println(msg.getNickname() + " has no coins left.");
            }
        }
    }

    /**
     * Handles the CurrentPlayerMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void currentPlayerHandler(CurrentPlayerMessage msg) {
        modelView.setCurrentPlayer(msg.getCurrentPlayer());
        showCurrentPlayer();
    }

    /**
     * Displays the current player.
     */
    public void showCurrentPlayer() {
        System.out.println("The current player is: " + modelView.getCurrentPlayer());
    }

    /**
     * Handles the CurrentPhaseMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void currentPhaseHandler(CurrentPhaseMessage msg) {
        modelView.setCurrentPhase(msg.getCurrentPhase());
        showCurrentPhase(msg);
    }

    /**
     * Displays the instructions of the specified phase.
     * If the chosen game mode is expert, it also displays the drawn characters.
     * @param msg the message containing the phase information.
     */
    public void showCurrentPhase(CurrentPhaseMessage msg) {
        System.out.println(msg.getInstructions());

        List<CharacterView> characterViews = modelView.getIdToCharacter().values().stream().toList();
        if(characterViews.size() > 0) {
            showDrawnCharacters(msg.getCurrentPhase(), characterViews);
        }
    }

    /**
     * Displays the drawn characters, with a different format based on the
     * current phase.
     * @param currentPhase the current phase.
     * @param characterViews the drawn characters.
     */
    public void showDrawnCharacters(String currentPhase, List<CharacterView> characterViews) {
        if(currentPhase.equals("Planning phase")) {
            StringBuilder characters = new StringBuilder("- Drawn characters:");
            for(CharacterView c : characterViews) {
                characters.append(" ").append(c.getCharacterID());
            }
            characters.append("\n");

            System.out.println(characters);
        } else if(currentPhase.equals("Move student phase") || currentPhase.equals("Move mother nature phase")
                || currentPhase.equals("Pick cloud phase")) {
            StringBuilder characters = new StringBuilder("- Play a character:\n");

            for(CharacterView c : characterViews) {
                characters.append("\tCharacter ID: ").append(c.getCharacterID());

                if(c.getStudents().size() > 0) {
                    characters.append(", students:");
                    for(Student s : c.getStudents()) {
                        characters.append(" ").append(Constants.getCircleFullByColor(s.getColor()));
                    }
                }

                if(c.getCharacterID() == CharacterID.CHARACTER_FIVE.getID()) {
                    characters.append(", ban cards: ").append(c.getBanCards());
                }

                characters.append(" (cost: ").append(c.getCost()).append(")");

                characters.append("\n");

                // Format
                characters.append("\t\t").append(Constants.getCharacterFormat(c.getCharacterID())).append("\n");
            }
            System.out.println(characters);
        }
    }

    /**
     * Handles the CharacterDrawnMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void charactersDrawnHandler(CharacterDrawnMessage msg) {
        modelView.setDrawnCharacter(msg);
    }

    /**
     * Handles the CharacterInfoMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void characterInfoHandler(CharacterInfoMessage msg) {
        System.out.println(msg.getDescription());
        System.out.println("Cost: " + modelView.getCharacterViewById(msg.getId()).getCost());
    }

    /**
     * Handles the CharacterPlayedMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void characterPlayedHandler(CharacterPlayedMessage msg) {
        modelView.setPlayedCharacter(msg);

        int characterID = msg.getCharacterID();

        if(characterID > 0) {
            showPlayedCharacter(characterID);
        }
    }

    /**
     * Displays the played character whose ID is specified.
     * @param characterID the ID of the played character.
     */
    public void showPlayedCharacter(int characterID) {
        CharacterView characterView = modelView.getCharacterViewById(characterID);

        String played = "Character played: " + characterID + " (cost: " + characterView.getCost() + ")";

        String banCard = "";
        if(characterView.getBanCards() > 0) {
            banCard = " Number of ban cards: " + characterView.getBanCards();
        }

        StringBuilder studentString = new StringBuilder();
        if(characterView.getStudents().size() > 0) {
            studentString = new StringBuilder(" Students:");
            for(Student student : characterView.getStudents()) {
                studentString.append(" ").append(Constants.getCircleFullByColor(student.getColor()));
            }
        }

        System.out.println(played + studentString + banCard);
    }

    /**
     * Displays the match's current state.
     */
    @Override
    public void matchInfoHandler() {
        // Display players
        showActivePlayers();

        // Display the boards and coins (if EXPERT game mode)
        showBoards();

        // Coins (if EXPERT mode)
        if(modelView.getGameMode().equals(GameMode.EXPERT)) {
            showCoins(modelView.getPlayersSorted());
        }

        // Display the island groups
        showIslandGroups();

        // Display drawn characters
        List<CharacterView> characterViews = modelView.getIdToCharacter().values().stream().toList();
        if(characterViews.size() > 0) {
            showDrawnCharacters(modelView.getCurrentPhase(), characterViews);
        }

        // Display active character (if present)
        int characterID = modelView.getActiveCharacterID();
        if(characterID > 0) {
            showPlayedCharacter(characterID);
        }
    }

    /**
     * Handles the WinnerMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void winnerHandler(WinnerMessage msg) {
        modelView.setWinner(msg.getWinnerNickname());
        System.out.println("You won! Congratulations!\n");
    }

    /**
     * Handles the LoserMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void loserHandler(LoserMessage msg) {
        modelView.setWinner(msg.getWinnerNickname());
        System.out.println("The winner is " + msg.getWinnerNickname() + "!\n");
    }

    /**
     * Handles the end of the game
     */
    @Override
    public void gameEndedHandler() {
        System.out.print("The game has ended, the application will close soon");
        Constants.countdown(500);

        socketClient.disconnect();
    }

    /**
     * Handles the GenericMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void genericMessageHandler(GenericMessage msg) {
        System.out.println(msg.getMessage());
    }

    /**
     * Handles the ErrorMessage sent by the server.
     * @param msg the message to handle.
     */
    @Override
    public void errorMessageHandler(ErrorMessage msg) {
        System.out.println(msg.getError());
    }

    /**
     * Asks for the IP address and port of the server in order to
     * establish a connection with it.
     * @return the Socket instantiated for the connection with the server.
     */
    private static Socket askServerInfo() {
        Scanner scanner = new Scanner(System.in);
        String IPAddress;
        int port;

        System.out.println("Please insert the IP address of the server (format: x.y.z.w).");
        System.out.print(Constants.PROMPT);

        IPAddress = setIPAddress(scanner.nextLine());

        System.out.println("Please insert the port that the server is listening on.");
        System.out.print(Constants.PROMPT);

        try {
            port = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            port = Constants.DEFAULT_PORT;
            System.out.println("Invalid port, the server will be listening on the default port: " + port + ".");
        }

        try {
            return new Socket(IPAddress, port);
        } catch (IOException e) {
            System.err.println("A problem occurred while trying to instantiate the connection.");
            System.err.println("The application will now close...");
            System.exit(0);
        }

        return null;
    }

    /**
     * Parses the IP address inputted by the client.
     * If the parsing fails, it returns the default IP address - localhost.
     * @param input the keyboard input.
     * @return the IP address parsed from the input; if invalid, the return value is
     *         the one defined in the Constants class.
     */
    private static String setIPAddress(String input) {
        if(input.matches(Constants.IPV4_PATTERN)) {
            return input;
        }
        System.out.println("Invalid IP address, setting it to the default: " + Constants.DEFAULT_IP_ADDRESS + ".\n");
        return Constants.DEFAULT_IP_ADDRESS;
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        Socket socket = askServerInfo();
        cli.setSocketClient(new SocketClient(cli, socket));
        SocketClient client = cli.getSocketClient();

        System.out.println(Constants.ERIANTYS + "\n");

        cli.addListener("MessageParser", new MessageParser(client));
        cli.listenToKeyboard(); // Asynchronous read (keyboard input)

        client.readMessage(); // Asynchronous read (messages from server)
        client.enablePinger(true);
    }
}