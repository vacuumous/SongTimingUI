package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Reader that reads Json and sets this to match
// Uses JsonSerializationDemo as a template
public class JsonReader {
    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader with the file path of the json to be read
    public JsonReader(String path) {
        this.source = path;
    }


    // EFFECTS: read the json at file path
    //          throws IOException if the file can not read file properly
    public Song read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSong(jsonObject);
    }


    // EFFECTS: returns source file as a string
    public String readFile(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Song from json data
    public Song parseSong(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        Song song = new Song(title, artist);
        addSections(song, jsonObject);
        return song;
    }

    // MODIFIES: song
    // EFFECTS: parses timing sections from JSON and adds them to song
    public void addSections(Song song, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sections");
        for (Object json : jsonArray) {
            JSONObject section = (JSONObject) json;
            addSection(song, section);
        }
    }

    // MODIFIES: song
    // EFFECTS: parses timing section from JSON and adds them to song
    public void addSection(Song song, JSONObject jsonObject) {
        int offset = jsonObject.getInt("offset");
        Double bpm = jsonObject.getDouble("BPM");
        String signature = jsonObject.getString("Signature");
        String[] split = signature.split("\\s/\\s", 0);
        int top = Integer.parseInt(split[0]);
        int bot = Integer.parseInt(split[1]);
        TimingSection section = new TimingSection(offset, bpm, new TimeSignature(top, bot));
        song.addSection(section);

    }
}
