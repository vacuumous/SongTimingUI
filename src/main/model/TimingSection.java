package model;

import java.sql.Time;

// TimingSection represents a section with an initial timestamp, bpm, and time signature (top / bot)
public class TimingSection {

    private int timestamp;
    private double bpm;
    private TimeSignature timesig;


    public TimingSection(int time, double bpm, TimeSignature ts) {
        this.timestamp = time;
        this.bpm = bpm;
        timesig = ts;
    }


    public void setTime(int time) {
        this.timestamp = time;
    }


    public void setBPM(double bpm) {
        this.bpm = bpm;
    }




    // REQUIRES: top > 0 and bot > 0
    // MODIFIES: this
    // EFFECTS: change time signature to be (top / bot)
    public void setTimeSig(int top, int bot) {
        timesig = new TimeSignature(top, bot);
    }


    public int getTime() {
        return timestamp;
    }


    public double getBPM() {
        return bpm;
    }

    public TimeSignature getTimesig() {
        return timesig;
    }

    // REQUIRES: bpm > 0
    // MODIFIES:
    // EFFECTS: returns how long a single beat is in milliseconds
    public double beatLength() {
        return 60000 / this.getBPM();
    }


}
