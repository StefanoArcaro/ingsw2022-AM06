package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.clientToserver.*;
import it.polimi.ingsw.util.Constants;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MessageParser implements PropertyChangeListener {

    SocketClient client;

    /**
     * Default constructor.
     * @param client the socket of the client this parser is a listener of.
     */
    public MessageParser(SocketClient client) {
        this.client = client;
    }

    //per ora metto attributo nickname per creare i messaggi
    private String nickname;    //todo: togliere nickname dai messaggi

    /**
     * Parses the input based on the keyword at the beginning of the command.
     * If the input has an invalid format, the client is notified and no
     * further action id taken.
     * @param input from the client.
     */
    public void parseInput(String input) {
        String [] in = input.split(" ");
        String command = in[0];
        Message message = null;

        switch (command.toUpperCase()) {
            case "LOGIN" -> message = loginMessage(in);
            case "WIZARD" -> message = prepareMessage(in);
            case "ASSISTANT" -> message = planningMessage(in);
            case "MOVESTUDENT" -> message = firstActionPhase(in);
            case "MOVEMOTHERNATURE" -> message = secondActionPhaseMessage(in);
            case "PICKCLOUD" -> message = thirdActionPhase(in);
            case "CHARACTERINFO" -> message = characterInfoMessage(in);
            case "CHARACTER" -> message = characterMessage(in);
            case "QUIT" -> message = disconnectionMessage();
            default -> System.out.println("Unknown input, please try again!");
        }

        if(message != null) {
            client.sendMessage(message);
        }
    }

    /**
     * Parses a login request message.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private LoginRequestMessage loginMessage(String[] in) {
        if(in.length == 4) {
            String nickname = in[1];
            int numberOfPlayers = 0;

            //todo: fix all NumberFormatException
            try {
                numberOfPlayers = Integer.parseInt(in[2]);
            } catch (NumberFormatException ignored) {}

            NumberOfPlayers playerNum = null;
            if(numberOfPlayers == 2) {
                playerNum = NumberOfPlayers.TWO_PLAYERS;
            } else if(numberOfPlayers == 3) {
                playerNum = NumberOfPlayers.THREE_PLAYERS;
            }

            int mode = Integer.parseInt(in[3]);
            GameMode gameMode = null;
            if(mode == 0) {
                gameMode = GameMode.EASY;
            } else if(mode == 1) {
                gameMode = GameMode.EXPERT;
            }

            if(playerNum != null && gameMode != null) {
                return new LoginRequestMessage(nickname, playerNum, gameMode);
            }
        }

        System.out.println("Correct format for LOGIN message:");
        System.out.println(Constants.LOGIN_FORMAT);

        return null;
    }

    /**
     * Parses a wizard request message.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private WizardRequestMessage prepareMessage(String[] in) {
        if(in.length == 2) {

            WizardName wizardName = null;

            switch (in[1].toUpperCase()) {
                case "DRUID" -> wizardName = WizardName.DRUID;
                case "KING" -> wizardName = WizardName.KING;
                case "WITCH" -> wizardName = WizardName.WITCH;
                case "SENSEI" -> wizardName = WizardName.SENSEI;
            }

            if (wizardName != null) {
                return new WizardRequestMessage(nickname, wizardName);
            }
        }

        System.out.println("Correct format for WIZARD message:");
        System.out.println(Constants.PREPARE_FORMAT);

        return null;
    }

    /**
     * Parses an assistant request message.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private AssistantRequestMessage planningMessage(String[] in) {
        if(in.length == 2) {
            int assistantId = Integer.parseInt(in[1]);

            if(assistantId >= 1 && assistantId <= 10) {
                return new AssistantRequestMessage(nickname, assistantId);
            }
        }

        System.out.println("Correct format for ASSISTANT message:");
        System.out.println(Constants.PLANNING_FORMAT);

        return null;
    }

    /**
     * Parses a message for moving a student from the entrance to the hall / an island.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private MoveStudentMessage firstActionPhase(String[] in) {
        if(in.length == 3) {
            CreatureColor color = switchColor(in[1]);
            int destination = Integer.parseInt(in[2]);

            if(color != null && (destination >= 0 && destination <= 12)) {
                return new MoveStudentMessage(nickname, color, destination);
            }
        }

        System.out.println("Correct format for MOVE STUDENT message:");
        System.out.println(Constants.MOVE_STUDENT_FORMAT);

        return null;
    }

    /**
     * Parses a message for moving Mother Nature a certain number of steps.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private MoveMotherNatureMessage secondActionPhaseMessage(String[] in) {
        if(in.length == 2) {
            int steps = Integer.parseInt(in[1]);

            if(steps >= 1 && steps <= 7) {
                return new MoveMotherNatureMessage(nickname, steps);
            }
        }

        System.out.println("Correct format for MOVE MOTHER NATURE message:");
        System.out.println(Constants.MOVE_MOTHER_NATURE_FORMAT);

        return null;
    }

    /**
     * Parses a message to pick a cloud to get students from.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private PickCloudMessage thirdActionPhase(String[] in) {
        if(in.length == 2) {
            int cloudID = Integer.parseInt(in[1]);

            if(cloudID >= 1 && cloudID <= 3) {
                return new PickCloudMessage(nickname, cloudID);
            }
        }

        System.out.println("Correct format for PICK CLOUD message:");
        System.out.println(Constants.PICK_CLOUD_FORMAT);

        return null;
    }

    /**
     * Parses a message for asking info about a certain character.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private CharacterInfoRequestMessage characterInfoMessage(String[] in) {
        if(in.length == 2) {
            int characterID = Integer.parseInt(in[1]);

            if(characterID >= 1 && characterID <= 12) {
                return new CharacterInfoRequestMessage(nickname, characterID);
            }
        }

        System.out.println("Correct format for CHARACTER INFO message:");
        System.out.println(Constants.CHARACTER_INFO_FORMAT);

        return null;
    }

    /**
     * Parses a message for playing a certain character.
     * If the input is invalid, it suggests the correct format.
     * @param in the client's input.
     * @return the parsed message is the format is correct, null otherwise.
     */
    private Message characterMessage(String[] in) {
        int numberOfParameters = in.length;
        // [0] -> command
        // [1] -> characterID
        // 2 parameters: CharacterMessage
        if(numberOfParameters == 2) {
            int characterID = Integer.parseInt(in[1]);

            if(characterID >= 1 && characterID <= 12) {
                return new CharacterMessage(nickname, characterID);
            }
        } else if(numberOfParameters == 3 || numberOfParameters == 4) {
            int characterID = Integer.parseInt(in[1]);

            if(characterID >= 1 && characterID <= 12) {
                String secondInput = in[2];
                CreatureColor color = switchColor(secondInput);
                Integer destination = null;

                if (color == null) {
                    destination = Integer.parseInt(secondInput);
                }

                // [2] -> color / destination
                // 3 parameters: CharacterColorMessage / CharacterDestinationMessage
                if (numberOfParameters == 3) {
                    if (color != null) {
                        return new CharacterColorMessage(nickname, characterID, color);
                    }
                    return new CharacterDestinationMessage(nickname, characterID, destination);
                }

                // [3] -> color / destination
                // 4 parameters: CharacterDoubleColorMessage / CharacterColorDestinationMessage
                if (color != null) {
                    String thirdInput = in[3];
                    CreatureColor secondColor = switchColor(thirdInput);

                    if (secondColor == null) {
                        destination = Integer.parseInt(thirdInput);
                    } else {
                        return new CharacterDoubleColorMessage(nickname, characterID, color, secondColor);
                    }
                    return new CharacterColorDestinationMessage(nickname, characterID, color, destination);
                }
            }
        }

        // TODO change with drawn characters info
        System.out.println("Correct format for CHARACTER message:");
        System.out.println("character [character id] [parameters...........]");

        return null;
    }

    /**
     * Parses a message for quitting the application.
     * @return the parsed message.
     */
    private DisconnectionRequestMessage disconnectionMessage() {
        return new DisconnectionRequestMessage(nickname);
    }

    /**
     * Handles the parsing of a color parameter in a command.
     * @param colorString the parameter to parse.
     * @return the color's enum value if valid, null otherwise.
     */
    private CreatureColor switchColor(String colorString) {
        CreatureColor color = null;

        switch (colorString.toUpperCase()) {
            case "GREEN" -> color = CreatureColor.GREEN;
            case "RED" -> color = CreatureColor.RED;
            case "YELLOW" -> color = CreatureColor.YELLOW;
            case "PINK" -> color = CreatureColor.PINK;
            case "BLUE" -> color = CreatureColor.BLUE;
        }
        return color;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        parseInput(evt.getNewValue().toString());
    }
}