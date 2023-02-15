package model;

import java.util.ArrayList;
import java.util.Comparator;

// Song represents a song with metadata (title, artist, etc.) and timing sections
public class Song {
    private ArrayList<TimingSection> timingSections;
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        timingSections = new ArrayList<>();
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

    // REQUIRES: ts is not already in list
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

    // REQUIRES: time > 0
    //           at least one timing section where timestamp <= time
    //           timingSections is sorted in ascending order of timestamp
    // MODIFIES:
    // EFFECTS: return the timing section active at the given time
    public TimingSection find(double time) {
        TimingSection result = null;
        for (TimingSection ts: timingSections) {
            if (ts.getTime() > time) {
                break;
            }
            result = ts;
        }
        return result;
    }

    public TimeSignature findSig(double time) {
        return this.find(time).getTimesig();
    }

    // REQUIRES: !timingSections.isEmpty()
    // MODIFIES: this
    // EFFECTS: sorts timing sections in ascending order of timestamp
    public void sort() {
        timingSections.sort(Comparator.comparing(TimingSection::getTime));
    }


}
