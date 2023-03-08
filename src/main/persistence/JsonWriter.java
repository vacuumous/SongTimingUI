package persistence;

import model.Song;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writer that writes this in Json format
// Uses JsonSerializationDemo as a template
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer that will write json at destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if file path is invalid
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes json version of song to the file
    public void write(Song song) {
        JSONObject json = song.toJson();
        saveToFile(json.toString(4));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    public void saveToFile(String json) {
        writer.print(json);
    }
}
