package it.polimi.ingsw.network.client;

//gestisce gli input dell'utente trasformandoli in messaggi da mandare al server

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.NumberOfPlayers;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.clientToserver.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MessageParser implements PropertyChangeListener {

    SocketClient client;

    public MessageParser(SocketClient client) {
        this.client = client;
    }

    //per ora metto attributo nickname per creare i messaggi
    private String nickname;    //todo: togliere nickname dai messaggi

    //called when a client insert a command in Cli
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
            default -> {
                System.out.println("Unknown input, please try again!");
                return;
            }
        }

        if(message != null) {
            client.sendMessage(message);
        }
    }

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
        System.out.println("login [nickname] [number of players (2 / 3)] [game mode (0 = EASY / 1 = EXPERT)]");

        return null;
    }

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
        System.out.println("wizard [DRUID / KING / WITCH / SENSEI]");

        return null;
    }

    private AssistantRequestMessage planningMessage(String[] in) {
        if(in.length == 2) {
            int assistantId = Integer.parseInt(in[1]);

            if(assistantId >= 1 && assistantId <= 10) {
                return new AssistantRequestMessage(nickname, assistantId);
            }
        }

        System.out.println("Correct format for ASSISTANT message:");
        System.out.println("assistant [1..10]");

        return null;
    }

    private MoveStudentMessage firstActionPhase(String[] in) {
        if(in.length == 3) {
            CreatureColor color = switchColor(in[1]);
            int destination = Integer.parseInt(in[2]);

            if(color != null && (destination >= 0 && destination <= 12)) {
                return new MoveStudentMessage(nickname, color, destination);
            }
        }

        System.out.println("Correct format for MOVE STUDENT message:");
        System.out.println("movestudent [color from entrance (GREEN / RED / YELLOW / PINK / BLUE)] [destination (0 = hall / 1..12 = island)]");

        return null;
    }

    private MoveMotherNatureMessage secondActionPhaseMessage(String[] in) {
        if(in.length == 2) {
            int steps = Integer.parseInt(in[1]);

            if(steps >= 1 && steps <= 7) {
                return new MoveMotherNatureMessage(nickname, steps);
            }
        }

        System.out.println("Correct format for MOVE MOTHER NATURE message:");
        System.out.println("movemothernature [number of steps]");

        return null;
    }

    private PickCloudMessage thirdActionPhase(String[] in) {
        if(in.length == 2) {
            int cloudID = Integer.parseInt(in[1]);

            if(cloudID >= 1 && cloudID <= 3) {
                return new PickCloudMessage(nickname, cloudID);
            }
        }

        System.out.println("Correct format for PICK CLOUD message:");
        System.out.println("pickcloud [cloud id]");

        return null;
    }

    private CharacterInfoRequestMessage characterInfoMessage(String[] in) {
        if(in.length == 2) {
            int characterID = Integer.parseInt(in[1]);

            if(characterID >= 1 && characterID <= 12) {
                return new CharacterInfoRequestMessage(nickname, characterID);
            }
        }

        System.out.println("Correct format for CHARACTER INFO message:");
        System.out.println("characterinfo [character id (1..12)]");

        return null;
    }

    private Message characterMessage(String[] in) {


        int numberOfParameters = in.length;
        //[0] -> command
        //[1] -> characterID
        // 2 parameters: CharacterMessage

        //[2] -> color / destination
        // 3 parameters: CharacterColorMessage / CharacterDestinationMessage

        //[3] -> color / destination
        // 4 parameters: CharacterDoubleColorMessage / CharacterColorDestinationMessage

        if(numberOfParameters == 2) {
            int characterID = Integer.parseInt(in[1]);

            if(characterID >= 1 && characterID <= 12) {
                return new CharacterMessage(nickname, characterID);
            }

        } else if(numberOfParameters == 3 || numberOfParameters == 4) {
            int characterID = Integer.parseInt(in[1]);

            if(characterID >= 1 && characterID <= 12) {

                String secondInput = in[2];
                CreatureColor color = null;
                Integer destination = null;

                color = switchColor(secondInput);

                if (color == null) {
                    destination = Integer.parseInt(secondInput);
                }

                //3 parameters: CharacterColor - CharacterDestination
                if (numberOfParameters == 3) {
                    if (color != null) {
                        return new CharacterColorMessage(nickname, characterID, color);

                    }
                    return new CharacterDestinationMessage(nickname, characterID, destination);

                }

                //4 parameters: CharacterDoubleColor - CharacterColorDestination
                //the second input is a color
                if (color != null) {
                    String thirdInput = in[3];
                    CreatureColor secondColor = null;

                    secondColor = switchColor(thirdInput);

                    if (secondColor == null) {
                        destination = Integer.parseInt(thirdInput);
                    }

                    if (secondColor != null) {
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

    private DisconnectionRequestMessage disconnectionMessage() {
        return new DisconnectionRequestMessage(nickname);
    }

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