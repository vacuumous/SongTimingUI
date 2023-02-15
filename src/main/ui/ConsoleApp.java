package ui;

import model.*;

import java.util.Locale;
import java.util.Scanner;

// Console UI for timing editor
// uses TellerApp as a template
public class ConsoleApp {

    private Song song;
    private Scanner input;
    String command;

    public ConsoleApp() {
        runEditor();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: processes user input
    private void runEditor() {
        input = new Scanner(System.in);
        boolean keepGoing = true;

        System.out.println("Welcome!");
        initialize();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("/q")) {
                keepGoing = false;
            }
            processCommand(command);
        }

        System.out.println("\nSee you next time");
    }

    // MODIFIES:
    // REQUIRES:
    // EFFECTS: prints menu screen and commands
    private void displayMenu() {
        System.out.println("\nCurrent song: " + song.getTitle() + " - " + song.getArtist());
        System.out.println("Commands:");
        System.out.println("\t /t -> title");
        System.out.println("\t /a -> artist");
        System.out.println("\t /add -> add timing section");
        System.out.println("\t /remove -> remove timing section");
        System.out.println("\t /ts -> view timing sections");
        System.out.println("\t /q -> quit");


    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: prompt user for title and artist, create Song with those parameters
    private void initialize() {
        System.out.println("Please enter a song title.");
        String title = input.nextLine();
        System.out.println("Please enter an artist.");
        String artist = input.nextLine();
        System.out.println("Song title: " + title);
        System.out.println("Song artist: " + artist);
        System.out.println("Confirm? y / n");
        String confirm = input.nextLine().toLowerCase();
        if (confirm.equals("n")) {
            initialize();
        }
        song = new Song(title, artist);
    }

    // REQUIRES: command != null
    // MODIFIES:
    // EFFECTS: performs function corresponding to command
    private void processCommand(String c) {
        if (c.equals("t")) {
            titleCommand();
        }
        if (c.equals("a")) {
            artistCommand();
        }
        if (c.equals("add")) {
            addSection();
        }
        if (c.equals("remove")) {
            removeSection();
        }
        if (c.equals("ts")) {
            viewSections();
        }
    }

    // TODO
    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: changes title name if user wants to
    private void titleCommand() {

    }

    // TODO
    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: changes artist name if user wants to
    private void artistCommand() {

    }

    // TODO
    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: add timing section from user input
    private void addSection() {

    }

    // TODO
    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: remove selected timing section
    private void removeSection() {

    }

    // TODO
    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private void viewSections() {

    }
}
