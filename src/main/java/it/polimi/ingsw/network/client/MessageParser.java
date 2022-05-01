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

            int numberOfPlayers = Integer.parseInt(in[2]);
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
        System.out.println("login [nickname] [numberOfPlayers (2/3)] [gameMode (0 = EASY / 1 = EXPERT)]");

        return null;
    }

    private WizardRequestMessage prepareMessage(String[] in) {
        WizardName wizardName = null;

        switch(in[1].toUpperCase()) {
            case "DRUID" -> wizardName = WizardName.DRUID;
            case "KING" -> wizardName = WizardName.KING;
            case "WITCH" -> wizardName = WizardName.WITCH;
            case "SENSEI" -> wizardName = WizardName.SENSEI;
        }

        return new WizardRequestMessage(nickname, wizardName);
    }

    private AssistantRequestMessage planningMessage(String[] in) {
        int assistantId = Integer.parseInt(in[1]);

        return new AssistantRequestMessage(nickname, assistantId);
    }

    private MoveStudentMessage firstActionPhase(String[] in) {
        CreatureColor color = switchColor(in[1]);
        int destination = Integer.parseInt(in[2]);

        return new MoveStudentMessage(nickname, color, destination);
    }

    private MoveMotherNatureMessage secondActionPhaseMessage(String[] in) {
        int steps = Integer.parseInt(in[1]);

        return new MoveMotherNatureMessage(nickname, steps);
    }

    private PickCloudMessage thirdActionPhase(String[] in) {
        int cloudID = Integer.parseInt(in[1]);

        return new PickCloudMessage(nickname, cloudID);
    }

    private CharacterInfoRequestMessage characterInfoMessage(String[] in) {
        int characterID = Integer.parseInt(in[1]);

        return new CharacterInfoRequestMessage(nickname, characterID);
    }

    private Message characterMessage(String[] in) {
        int characterID = Integer.parseInt(in[1]);

        int numberOfParameters = in.length;
        //[0] -> command
        //[1] -> characterID
        // 2 parameters: CharacterMessage

        //[2] -> color / destination
        // 3 parameters: CharacterColorMessage / CharacterDestinationMessage

        //[3] -> color / destination
        // 4 parameters: CharacterDoubleColorMessage / CharacterColorDestinationMessage

        if(numberOfParameters == 2) {
            return new CharacterMessage(nickname, characterID);


        } else if(numberOfParameters <= 4) {
            String secondInput = in[2];
            CreatureColor color = null;
            Integer destination = null;

            color = switchColor(secondInput);

            if(color == null){
                destination = Integer.parseInt(secondInput);
            }

            //3 parameters: CharacterColor - CharacterDestination
            if(numberOfParameters == 3) {
                if(color != null) {
                    return new CharacterColorMessage(nickname, characterID, color);

                }
                return new CharacterDestinationMessage(nickname, characterID, destination);

            }

            //4 parameters: CharacterDoubleColor - CharacterColorDestination
            //the second input is a color
            if(color != null){
                String thirdInput = in[3];
                CreatureColor secondColor = null;

                secondColor = switchColor(thirdInput);

                if(secondColor == null){
                    destination = Integer.parseInt(thirdInput);
                }

                if(secondColor != null) {
                    return new CharacterDoubleColorMessage(nickname, characterID, color, secondColor);
                }
                return new CharacterColorDestinationMessage(nickname, characterID, color, destination);

            }
        }
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