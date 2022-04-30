package it.polimi.ingsw.controller;

//gestisce gli input dell'utente trasformandoli in messaggi da mandare al server

import it.polimi.ingsw.model.enumerations.CreatureColor;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.enumerations.WizardName;
import it.polimi.ingsw.network.message.clientToserver.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MessageParser implements PropertyChangeListener {

    //attributo per la connessione -> collegamento al client che manda il messaggio ?

    //per ora metto attributo nickname per creare i messaggi
    private String nickname;



    //called when a client insert a command in Cli
    public boolean parseInput(String input) {
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
            default -> {
                System.out.println("Unknown input, please try again!");
                return false;
            }
        }

        if(message != null){
            //connection send al server(message)
            return true;
        }
        return false;

    }


    private LoginRequestMessage loginMessage(String [] in) {
        String nickname = in[1];
        int numberOfPlayer;
        GameMode gameMode;

        try{
            numberOfPlayer = Integer.parseInt(in[2]);
            gameMode = Integer.parseInt(in[3]) == 0 ? GameMode.EASY : GameMode.EXPERT;
        }catch(NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

        return new LoginRequestMessage(nickname, numberOfPlayer, gameMode);
    }

    private WizardRequestMessage prepareMessage(String [] in) {
        WizardName wizardName = null;

        switch(in[1].toUpperCase()) {
            case "DRUID": wizardName = WizardName.DRUID;
            case "KING": wizardName = WizardName.KING;
            case "WITCH": wizardName = WizardName.WITCH;
            case "SENSEI": wizardName = WizardName.SENSEI;
        }

        return new WizardRequestMessage(nickname, wizardName);
    }


    private AssistantRequestMessage planningMessage(String [] in) {
        int assistantId;

        try{
            assistantId = Integer.parseInt(in[1]);
        }catch (NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

        return new AssistantRequestMessage(nickname, assistantId);
    }

    private MoveStudentMessage firstActionPhase(String [] in) {
        CreatureColor color = switchColor(in[1]);

        int destination;

        try{
            destination = Integer.parseInt(in[2]);
        }catch(NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

        return new MoveStudentMessage(nickname, color, destination);

    }

    private MoveMotherNatureMessage secondActionPhaseMessage(String [] in) {
        int steps;

        try{
            steps = Integer.parseInt(in[1]);
        }catch(NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

        return new MoveMotherNatureMessage(nickname, steps);
    }

    private PickCloudMessage thirdActionPhase(String [] in) {
        int cloudID;

        try{
            cloudID = Integer.parseInt(in[1]);
        }catch(NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

        return new PickCloudMessage(nickname, cloudID);
    }

    private CharacterInfoRequestMessage characterInfoMessage(String [] in) {
        int characterID;

        try{
            characterID = Integer.parseInt(in[1]);
        }catch(NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

        return new CharacterInfoRequestMessage(nickname, characterID);
    }

    private Message characterMessage(String [] in) {
        int characterID;

        try{
            characterID = Integer.parseInt(in[1]);
        }catch(NumberFormatException e){
            System.err.println("Wrong parameter format: it must be a numeric value.");
            return null;
        }

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

        }else if(numberOfParameters <= 4){
            String secondInput = in[2];
            CreatureColor color = null;
            Integer destination = null;

            color = switchColor(secondInput);

            if(color == null){
                try {
                    destination = Integer.parseInt(secondInput);
                }catch(NumberFormatException e){
                    System.err.println("Wrong parameter format: it must be a numeric value.");
                    return null;
                }
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
                    try {
                        destination = Integer.parseInt(thirdInput);
                    }catch(NumberFormatException e){
                        System.err.println("Wrong parameter format: it must be a numeric value.");
                        return null;
                    }
                }

                if(secondColor != null) {
                    return new CharacterDoubleColorMessage(nickname, characterID, color, secondColor);
                }
                return new CharacterColorDestinationMessage(nickname, characterID, color, destination);
            }
        }
        return null;
    }


    private CreatureColor switchColor(String colorString){
        CreatureColor color = null;

        switch (colorString.toUpperCase()) {
            case "GREEN": color = CreatureColor.GREEN;
            case "RED": color = CreatureColor.RED;
            case "YELLOW": color = CreatureColor.YELLOW;
            case "PINK": color = CreatureColor.PINK;
            case "BLUE": color = CreatureColor.BLUE;
        }
        return color;
    }




    //todo - notifica dalla view che è stato scritto qualcosa - parso - mando al server
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        parseInput(evt.getNewValue().toString());
    }
}
