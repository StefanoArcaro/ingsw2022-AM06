package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.util.Constants;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.gui.GUIMain;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class of the game.
 * This class is the one that gets executed when the program starts.
 */
public class Eriantys {

    public static void main(String[] args) {
        System.out.println("Welcome to Eriantys!\nWhat do you want to launch?");
        System.out.println("0 - SERVER\n1 - CLI (Command Line Interface)\n2 - GUI (Graphical User Interface)\n");
        System.out.println("Please type the number of the desired option!");
        System.out.print(Constants.PROMPT);

        int launchInput;

        do {
            Scanner scanner = new Scanner(System.in);

            try {
                launchInput = scanner.nextInt();

                if(launchInput < 0 || launchInput > 2) {
                    System.out.println("Please enter either 0, 1 or 2.");
                    System.out.print(Constants.PROMPT);
                    launchInput = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Numeric format requested, please enter either 0, 1 or 2.");
                System.out.print(Constants.PROMPT);
                launchInput = -1;
            }
        } while(launchInput < 0);

        switch(launchInput) {
            case 0 -> {
                System.out.println(Constants.SEPARATOR);
                Server.main(null);
            }
            case 1 -> {
                System.out.println("You selected the CLI, have fun!");
                System.out.print("Starting");
                Constants.countdown(400);
                CLI.main(null);
            }
            case 2 -> {
                System.out.println("You selected the GUI, have fun!");
                System.out.print("Starting");
                Constants.countdown(400);
                System.out.println(Constants.SEPARATOR);
                System.out.println("GUI main running!");
                GUIMain.callGUI();
            }
            default -> System.err.println("Invalid argument, please run the executable again and select one of the available options.");
        }
    }
}
