package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.clientToserver.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Manages user inputs, transforming them into messages to be sent to the server.
 */
public class MessageParser implements PropertyChangeListener {

    SocketClient client;

    public MessageParser(SocketClient client) {
        this.client = client;
    }

    /**
     * The method is called when the user insert a command on the CLI.
     * It transforms the command into a message to send to the server.
     * @param input the message entered by the client.
     */
    public void parseInput(String input) {
        String [] in = input.split(" ");
        String command = in[0];
        Message message;

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
            default -> {
                System.out.println("Unknown input, please try again!");
                return;
            }
        }

        if(message != null) {
            client.sendMessage(message);
        }
    }

    /**
     * Handles the login request message.
     * @param in the input entered by the client.
     * @return a LoginRequestMessage to send to the server.
     */
    private LoginRequestMessage loginMessage(String[] in) {
        if(in.length == 4) {
            String nickname = in[1];

            int numberOfPlayers = 0;
            try {
                numberOfPlayers = Integer.parseInt(in[2]);
            } catch (NumberFormatException ignored) {}

            NumberOfPlayers playerNum = null;
            if(numberOfPlayers == 2) {
                playerNum = NumberOfPlayers.TWO_PLAYERS;
            } else if(numberOfPlayers == 3) {
                playerNum = NumberOfPlayers.THREE_PLAYERS;
            }

            int mode = -1;
            try {
                mode = Integer.parseInt(in[3]);
            } catch (NumberFormatException ignored) {}

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

        return null;
    }

    /**
     * Handles the wizard request message.
     * @param in the input entered by the client.
     * @return a WizardRequestMessage to send to the server.
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
                return new WizardRequestMessage(wizardName);
            }
        }

        return null;
    }

    /**
     * Handles the assistant request message.
     * @param in the input entered by the client.
     * @return a AssistantRequestMessage to send to the server.
     */
    private AssistantRequestMessage planningMessage(String[] in) {
        if(in.length == 2) {
            int assistantId = -1;

            try{
                assistantId = Integer.parseInt(in[1]);
            } catch (NumberFormatException ignored) {}

            if(assistantId >= 1 && assistantId <= 10) {
                return new AssistantRequestMessage(assistantId);
            }
        }

        return null;
    }

    /**
     * Handles the message sent by the client in the first action phase: move student.
     * @param in the input entered by the client.
     * @return a MoveStudentMessage containing the student to move and the destination to send to the server.
     */
    private MoveStudentMessage firstActionPhase(String[] in) {
        if(in.length == 3) {
            CreatureColor color = switchColor(in[1]);
            int destination = -1;

            try{
                destination = Integer.parseInt(in[2]);
            } catch (NumberFormatException ignored) {}

            if(color != null && (destination >= 0 && destination <= 12)) {
                return new MoveStudentMessage(color, destination);
            }
        }

        return null;
    }

    /**
     * Handles the message sent by the client in the second action phase: move mother nature.
     * @param in the input entered by the client.
     * @return a MoveMotherNatureMessage containing the number of steps that mother nature has to take
     *                                  to send to the server.
     */
    private MoveMotherNatureMessage secondActionPhaseMessage(String[] in) {
        if(in.length == 2) {
            int steps = -1;

            try{
                steps = Integer.parseInt(in[1]);
            } catch (NumberFormatException ignored) {}

            if(steps >= 1 && steps <= 7) {
                return new MoveMotherNatureMessage(steps);
            }
        }

        return null;
    }

    /**
     * Handles the message sent by the client in the third action phase: pick cloud.
     * @param in the input entered by the client.
     * @return a PickCloudMessage containing the cloud id to pick to send to the server.
     */
    private PickCloudMessage thirdActionPhase(String[] in) {
        if(in.length == 2) {
            int cloudID = -1;

            try{
                cloudID = Integer.parseInt(in[1]);
            } catch (NumberFormatException ignored) {}

            if(cloudID >= 1 && cloudID <= 3) {
                return new PickCloudMessage(cloudID);
            }
        }

        return null;
    }

    /**
     * Handles the message sent by the client to request info about a character.
     * @param in the input entered by the client.
     * @return a CharacterInfoRequestMessage to send to the server.
     */
    private CharacterInfoRequestMessage characterInfoMessage(String[] in) {
        if(in.length == 2) {
            int characterID = -1;

            try{
                characterID = Integer.parseInt(in[1]);
            } catch (NumberFormatException ignored) {}

            if(characterID >= 1 && characterID <= 12) {
                return new CharacterInfoRequestMessage(characterID);
            }
        }

        return null;
    }

    /**
     * Handles the message sent by the client to play a character.
     * @param in the input entered by the client.
     * @return a CharacterMessage to send to the server.
     */
    private Message characterMessage(String[] in) {
        int numberOfParameters = in.length;
        //[0] -> command
        //[1] -> characterID
        // 2 parameters: CharacterMessage: [2] -> color / destination
        // 3 parameters: CharacterColorMessage / CharacterDestinationMessage: [3] -> color / destination
        // 4 parameters: CharacterDoubleColorMessage / CharacterColorDestinationMessage

        if(numberOfParameters == 2) {
            return characterTwoParameters(in);
        } else if(numberOfParameters == 3 || numberOfParameters == 4) {

            int characterID = -1;
            try{ characterID = Integer.parseInt(in[1]);
            } catch (NumberFormatException ignored) {}

            if(characterID >= 1 && characterID <= 12) {
                String secondInput = in[2];
                CreatureColor color;
                Integer destination = null;
                color = switchColor(secondInput);

                if (color == null) {
                    try { destination = Integer.parseInt(secondInput);
                    }catch (NumberFormatException ignored) {}
                }

                //3 parameters: CharacterColor - CharacterDestination
                if (numberOfParameters == 3) {
                    return characterThreeParameters(characterID, color, destination);
                }

                //4 parameters: CharacterDoubleColor - CharacterColorDestination
                if (color != null) {
                    String thirdInput = in[3];
                    CreatureColor secondColor;
                    secondColor = switchColor(thirdInput);

                    if (secondColor == null) {
                        try { destination = Integer.parseInt(thirdInput);
                        }catch (NumberFormatException ignored) {}
                    }
                    return characterFourParameters(characterID, color, secondColor, destination);
                }
            }
        }

        return null;
    }

    /**
     * Manages the message to be created in case the client wants to play a character
     * that doesn't require any extra parameter.
     * @param in the input entered by the client.
     * @return a CharacterMessage containing the information needed.
     */
    private Message characterTwoParameters(String[] in) {
        int characterID = -1;

        try{
            characterID = Integer.parseInt(in[1]);
        } catch (NumberFormatException ignored) {}

        if(characterID >= 1 && characterID <= 12) {
            return new CharacterMessage(characterID);
        }
        return null;
    }

    /**
     * Manages the message to be created in case the client wants to play a character that requires
     * only one extra parameter.
     * @param characterID the id of the character to play.
     * @param color the possible color inserted by the client.
     * @param destination the possible destination inserted by the client.
     * @return a CharacterColorMessage or a CharacterDestinationMessage containing the information needed.
     */
    private Message characterThreeParameters(int characterID, CreatureColor color, Integer destination) {

        if (color != null) {
            return new CharacterColorMessage(characterID, color);

        }

        if (destination != null) {
            return new CharacterDestinationMessage(characterID, destination);
        }

        return null;
    }

    /**
     * Manages the message to be created in case the client wants to play a character that requires
     * two extra parameters.
     * @param characterID the id of the character to play.
     * @param color the first color inserted by the client.
     * @param secondColor the second possible color inserted by the client.
     * @param destination the possible destination inserted by the client.
     * @return a CharacterDoubleColorMessage or a CharacterColorDestinationMessage containing the information needed.
     */
    private Message characterFourParameters(int characterID, CreatureColor color, CreatureColor secondColor, Integer destination) {

        if (secondColor != null) {
            return new CharacterDoubleColorMessage(characterID, color, secondColor);
        }

        if(destination != null) {
            return new CharacterColorDestinationMessage(characterID, color, destination);
        }

        return null;
    }

    /**
     * Handles the disconnection request message.
     * @return a DisconnectionRequestMessage to send to the server.
     */
    private DisconnectionRequestMessage disconnectionMessage() {
        return new DisconnectionRequestMessage();
    }

    /**
     * Switch on the color name to return the right CreatureColor.
     * @param colorString color in input.
     * @return the CreatureColor corresponding to the color in input.
     */
    private CreatureColor switchColor(String colorString){
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