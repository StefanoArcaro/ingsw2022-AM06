package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enumerations.CharacterID;
import it.polimi.ingsw.model.enumerations.WizardName;
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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class for the Command Line Interface client.
 * This is executed when the client chooses the CLI option at the beginning of the game.
 */
public class CLI {

    private SocketClient socketClient;
    private final ExecutorService keyboardQueue;
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);
    private ModelView modelView;

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
    public SocketClient getSocketClient() {
        return this.socketClient;
    }

    /**
     * Sets the socket attribute of this client to the specified one.
     * @param socketClient socket to set.
     */
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    /**
     * Adds a listener to the CLI.
     * @param propertyName name of the observed property of the CLI.
     * @param listener listener added to the CLI.
     */
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



    public void loginReplyHandler(LoginReplyMessage msg) {
        System.out.println(msg.getMessage());
    }

    public void wizardsHandler(WizardsAvailableMessage msg) {
        ArrayList<WizardName> wizardNames = msg.getWizards();
        StringBuilder wizards = new StringBuilder("Available wizards: ");

        for(WizardName wizard : wizardNames) {
            wizards.append(wizard.getName()).append(" ");
        }

        System.out.println(wizards);
    }

    public void assistantsHandler(AssistantsMessage msg) {
        ArrayList<Assistant> assistants = msg.getAssistants();
        StringBuilder assistantString = new StringBuilder();

        for(Assistant assistant : assistants) {
            assistantString.append("Priority = ").append(assistant.getPriority()).append("- Number of steps = ").append(assistant.getNumberOfSteps()).append("\n");
        }

        System.out.println(assistantString);
    }

    public void activePlayersHandler(ActivePlayersMessage msg) {
        modelView.setPlayers(msg.getActivePlayers());
        showActivePlayers();
    }

    public void showActivePlayers() {
        List<String> players = modelView.getPlayers();

        String playersInGame = "Active players:\n";
        for(String p : players) {
            playersInGame = playersInGame + p + "\t";
        }
        System.out.println(playersInGame + "\n");
    }


    public void boardHandler(BoardMessage msg) {
        modelView.setBoard(msg);
        showBoard(msg.getNickname());
    }

    public void showBoard(String nickname) {
        Board board = modelView.getBoard(nickname);

        String owner = nickname + "'s board.\n";

        StringBuilder entranceStudents = new StringBuilder("Entrance:\n");
        for(Student student : board.getEntrance().getStudents()) {
            entranceStudents.append(student.getColor().getColorName()).append(" ");
        }
        entranceStudents.append("\n");

        StringBuilder hallStudents = new StringBuilder("Hall:\n");
        for(Table table : board.getHall().getStudents()) {
            hallStudents.append(table.getColor().getColorName()).append(" ").append(table.getLength()).append("\n");
        }

        StringBuilder professorsInBoard = new StringBuilder("Professors: ");
        if(board.getProfessors().size() > 0) {
            for (Professor professor : board.getProfessors()) {
                professorsInBoard.append(professor.getColor().getColorName()).append(" ");
            }
        }
        professorsInBoard.append("\n");

        String numTowers = "Number of towers: " + board.getTowers();

        System.out.println(owner + entranceStudents + hallStudents + professorsInBoard + numTowers);
    }


    public void islandGroupsHandler(IslandGroupsMessage msg) {
        modelView.setIslandGroups(msg.getIslandGroup(), msg.getMotherNatureIndex());
        showIslandGroups();
    }

    public void showIslandGroups() {
        ArrayList<IslandGroup> islandGroups = modelView.getIslandGroups();

        String islands = "\n";
        int islandGroupIndex = 0;

        for(IslandGroup iG : islandGroups) {
            int islandGroupId = islandGroupIndex + 1;
            islands = islands + "Island group " + islandGroupId + " composed by islands: \n";

            for(Island i : iG.getIslands()) {
                islands = "\t" + islands + i.getIslandID() + " -> ";
                for(Student s : i.getStudents()) {
                    islands = islands + s.getColor().getColorName() + " ";
                }
            }
            islands = islands + "\n";


            if(iG.getConquerorColor() != null) {
                islands = islands + "\tConquered by -> " + iG.getConquerorColor() + "\n";
            }

            if(iG.getNumberOfBanCardPresent() > 0) {
                islands = islands + "\tThere are " + iG.getNumberOfBanCardPresent() + " ban card on this island group.\n";
            }

            if(modelView.getMotherNatureIndex() == islandGroupIndex){
                islands = islands + Constants.ANSI_CYAN + "Mother nature is on this island group!\n" + Constants.ANSI_RESET;
            }

            islands = islands + "\n";

            islandGroupIndex += 1;
        }

        System.out.println(islands);
    }


    public void islandHandler(IslandMessage msg) {
        modelView.setIsland(msg.getIsland());
        showIsland(msg.getIsland().getIslandID());
    }

    public void showIsland(int islandID) {
        Island island = modelView.getIsland(islandID);

        String message = "Island: " + island.getIslandID() + ":";

        for(Student s : island.getStudents()) {
            message = message + "\t" + s.getColor().getColorName();
        }
        message = message + "\n";

        if(island.getTower() != null) {
            message = message + "\t\tConquered by -> " + island.getTower() + "\n";
        }

        System.out.println(message);
    }


    public void cloudsAvailableHandler(CloudsAvailableMessage msg) {
        modelView.setClouds(msg.getClouds());

        if(!modelView.getCurrentPhase().equals("Planning phase")) {
            showAvailableClouds();
        }
    }

    public void showAvailableClouds() {
        ArrayList<Cloud> clouds = modelView.getClouds();
        String message = "Available clouds\n";

        for(Cloud c : clouds) {
            if(!c.isEmpty()) {
                message = message + "- Cloud " + c.getCloudID() + ":";
                for (Student s : c.getStudents()) {
                    message = message + "\t" + s.getColor().getColorName();
                }
                message = message + "\n";
            }
        }

        System.out.println(message);
    }


    public void cloudChosenHandler(CloudChosenMessage msg) {
         showChosenCloud(msg.getCloud());
    }

    public void showChosenCloud(Cloud cloud) {
        String message = modelView.getCurrentPlayer() + "has chosen cloud " + cloud.getCloudID() + ":";

        for (Student s : cloud.getStudents()) {
            message = message + "\t" + s.getColor().getColorName();
        }
        message = message + "\n";

        System.out.println(message);
    }


    public void coinsHandler(CoinMessage msg) {
        modelView.setCoins(msg);
        showCoins(msg.getNickname());
    }

    public void showCoins(String nickname) {
        // TODO personalized messages
        System.out.println(nickname + " has " + modelView.getCoinsByNickname(nickname) + " coins.\n");
    }


    public void currentPlayerHandler(CurrentPlayerMessage msg) {
        modelView.setCurrentPlayer(msg.getCurrentPlayer());
        showCurrentPlayer();
    }

    public void showCurrentPlayer() {
        System.out.println("The current player is: " + modelView.getCurrentPlayer());
    }


    public void currentPhaseHandler(CurrentPhaseMessage msg) {
        modelView.setCurrentPhase(msg.getCurrentPhase());
        showCurrentPhase(msg);
    }

    public void showCurrentPhase(CurrentPhaseMessage msg) {
        System.out.println(msg.getInstructions());

        List<CharacterView> characterViews = modelView.getIdToCharacter().values().stream().toList();
        if(characterViews.size() > 0) {
            showDrawnCharacters(msg.getCurrentPhase(), characterViews);
        }
    }

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
                        characters.append(" ").append(s.getColor().getColorName());
                    }
                }

                if(c.getCharacterID() == CharacterID.CHARACTER_FIVE.getID()) {
                    characters.append(", ban cards: ").append(c.getBanCards());
                }

                characters.append("\n");

                // Format
                characters.append("\t\t").append(Constants.getCharacterFormat(c.getCharacterID())).append("\n");
            }
            System.out.println(characters);
        }
    }



    public void charactersDrawnHandler(CharacterDrawnMessage msg) {
        modelView.setDrawnCharacter(msg);
    }


    public void characterInfoHandler(CharacterInfoMessage msg) {
        System.out.println(msg.getDescription());
    }


    public void characterPlayedHandler(CharacterPlayedMessage msg) {
        modelView.setPlayedCharacter(msg);
        showPlayedCharacter(msg.getCharacterID());
    }

    public void showPlayedCharacter(int characterID) {
        CharacterView characterView = modelView.getCharacterViewById(characterID);

        String played = "Character played: " + characterID;

        String banCard = "";
        if(characterView.getBanCards() > 0) {
            banCard = "Number of ban cards: " + characterView.getBanCards();
        }

        String studentString = "";
        if(characterView.getStudents() != null) {
            studentString = "Students: ";
            for(Student student : characterView.getStudents()) {
                studentString = studentString + student.getColor().getColorName() + " ";
            }
        }

        System.out.println(played + studentString + banCard);
    }


    public void winnerHandler(WinnerMessage msg) {
        modelView.setWinner(msg.getWinnerNickname());
        showWinner(msg.getWinnerNickname());
    }

    public void showWinner(String nickname) {
        System.out.println("The winner is " + nickname + "!\n");
    }


    public void genericMessageHandler(GenericMessage msg) {
        System.out.println(msg.getMessage());
    }

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

        System.out.println("> Please insert the IP address of the server (format: x.y.z.w).");
        System.out.print("> ");

        IPAddress = setIPAddress(scanner.nextLine());

        System.out.println("> Please insert the port that the server is listening on.");
        System.out.print("> ");

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