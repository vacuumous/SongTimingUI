package model;

import java.util.ArrayList;

// Song represents a song with metadata (title, artist, etc.) and timing sections
public class Song {
    ArrayList<TimingSection> timingSections;
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public ArrayList<TimingSection> getTimingSections() {
        return timingSections;
    }

    // REQUIRES: ts != null
    // MODIFIES: this
    // EFFECTS: adds ts to the list of timing sections
    public void addSection(TimingSection ts) {
        timingSections.add(ts);
    }

    // REQUIRES: ts is in timingSections
    // MODIFIES: this
    // EFFECTS: removes given section from the list
    public void removeSection(TimingSection ts) {
        timingSections.remove(ts);
    }

    // REQUIRES: time > 0, at least one timing section
    // MODIFIES:
    // EFFECTS: return the timing section active at the given time
    public TimingSection find(double time) {
        TimingSection result = null;
        for (int i = 0; i < timingSections.size(); i++) {
            if (timingSections.get(i).getTime() <= time) {
                result = timingSections.get(i);
            }
        }
        return result;
    }


}
