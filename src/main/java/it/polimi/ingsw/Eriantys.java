package it.polimi.ingsw;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.CLI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Eriantys {

    public static void main(String[] args) {
        System.out.println("Welcome to Eriantys!\nWhat do you want to launch?");
        System.out.println("0 - SERVER\n1 - CLI (Command Line Interface)\n2 - GUI (Graphical User Interface)");
        System.out.println("Please type the number of the desired option!");
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        int launchInput = 0;
        try {
            launchInput = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }

        switch(launchInput) {
            case 0 -> {
                System.out.println("\n=============================================================\n");
                Server.main(null);
            }
            // TODO
            case 1 -> {
                System.out.println("You selected the CLI, have fun!");
                countdown();
                System.out.println("\n=============================================================\n");
                CLI.main(null);
            }
            case 2 -> {
                System.out.println("You selected the GUI, have fun!");
                countdown();
                System.out.println("\n=============================================================\n");
                System.out.println("GUI main running!");
                //GUI.main(null);
            }
            default -> System.err.println("Invalid argument, please run the executable again and select one of the available options.");
        }
    }

    private static void countdown() {
        System.out.print("Starting");
        for(int i = 3; i > 0; i--) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("There's been an error with the thread management");
                System.err.println("The application will now close...");
                System.exit(-1);
            }
        }
        System.out.println();
    }
}
