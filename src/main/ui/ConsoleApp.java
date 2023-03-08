package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
        start();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("/q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
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
        System.out.println("\t /view -> view timing sections");
        System.out.println("\t /bpm -> get BPM at certain time");
        System.out.println("\t /sig -> get time signature at certain time");
        System.out.println("\t /save -> save current song to JSON");
        System.out.println("\t /load -> load song from JSON");
        System.out.println("\t /q -> quit");


    }

    // EFFECTS: asks user if they want to load from a file, loads it if yes, otherwise initialize new song
    public void start() {
        System.out.println("Would you like to load a previous song?");
        boolean hasLoaded = false;
        command = input.nextLine().toLowerCase();
        if (command.equals("y") || command.equals("yes") || command.equals("load")) {
            hasLoaded = load();
        }
        if (!hasLoaded) {
            initialize();
        }

    }

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
        switch (c) {
            case "/t":
                titleCommand();
                break;
            case "/a":
                artistCommand();
                break;
            case "/add":
                addSection();
                break;
            case "/remove":
                removeSection();
                break;
            case "/view":
                viewSections();
                break;
            case "/bpm":
                findBPM();
                break;
            case "/sig":
                findSig();
            case "/save":
                save();
                break;
            case "/load":
                load();
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: changes title name if user wants to
    private void titleCommand() {
        System.out.println("Current title: " + song.getTitle());
        System.out.println("Enter new title or /q to cancel");
        String title = input.next();
        if (!title.equals("/q")) {
            song.setTitle(title);
        }
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: changes artist name if user wants to
    private void artistCommand() {
        System.out.println("Current artist: " + song.getArtist());
        System.out.println("Enter new artist or /q to cancel");
        String artist = input.next();
        if (!artist.equals("/q")) {
            song.setArtist(artist);
        }
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: add timing section from user input
    private void addSection() {
        System.out.println("\nEnter timestamp of new timing section (ms)");
        int timestamp = Integer.parseInt(input.next());
        System.out.println("Enter bpm");
        double bpm = Double.parseDouble(input.next());
        System.out.println("Enter time signature numerator");
        int top = input.nextInt();
        System.out.println("Enter time signature denominator");
        int bot = input.nextInt();
        song.addSection(new TimingSection(timestamp, bpm, new TimeSignature(top,bot)));

        song.sort();
        System.out.print("Timing section added!");
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: remove selected timing section
    private void removeSection() {
        System.out.println("Enter time where section is active to remove");
        int time = input.nextInt();
        song.removeSection(song.find(time));
        System.out.print("Timing section removed :(");
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: prints each timing section
    private void viewSections() {
        ArrayList<TimingSection> timingSections = song.getTimingSections();
        for (TimingSection ts : timingSections) {
            String sig = ts.getTimesig().getTop() + "/" + ts.getTimesig().getBot();
            System.out.println("Timestamp: " + ts.getTime() + "  BPM: " + ts.getBPM() + "  Time signature: " + sig);
        }
    }

    // REQUIRES: at least one timing section with timestamp < time
    // MODIFIES:
    // EFFECTS: prints bpm active at user specified time
    private void findBPM() {
        System.out.println("Enter time to find BPM for");
        double time = input.nextDouble();
        TimingSection ts = song.find(time);
        System.out.println("BPM at " + time + ": " + ts.getBPM());
    }

    // REQUIRES: at least one timing section with timestamp < time
    // MODIFIES:
    // EFFECTS: prints time signature active at user specified time
    private void findSig() {
        System.out.println("Enter time to find time signature for");
        double time = input.nextDouble();
        TimeSignature ts = song.find(time).getTimesig();
        System.out.println("Time signature at " + time + ": " + ts.getTop() + " / " + ts.getBot());
    }

    // EFFECTS: saves current song to ./data/ with filename given by user
    private void save() {
        System.out.println("Enter a file name (exclude .json) to save to, /q to cancel:");
        String fileName = input.next();
        if (fileName.equalsIgnoreCase("/q")) {
            System.out.println("Returning to main menu...");
        } else {
            try {
                JsonWriter writer = new JsonWriter("./data/" + fileName + ".json");
                writer.open();
                writer.write(song);
                writer.close();
            } catch (FileNotFoundException f) {
                System.err.println("Invalid file name, try again or cancel with /q");
                save();
            }
        }
    }

    // EFFECTS: loads song from JSON with filename from ./data/
    private boolean load() {
        System.out.println("Enter a file name (exclude .json) to load from, /q to cancel");
        String fileName = input.next();
        if (fileName.equalsIgnoreCase("/q")) {
            System.out.println("Cancelling loading...");
            return false;
        } else {
            try {
                JsonReader reader = new JsonReader("./data/" + fileName + ".json");
                song = reader.read();
                return true;
            } catch (IOException e) {
                System.err.println("Could not read file");
                load();
            }
        }
        return false;
    }
}
