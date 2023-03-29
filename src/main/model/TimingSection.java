package model;


import org.json.JSONObject;
import persistence.Writable;

// TimingSection represents a section with an initial timestamp, bpm, and time signature (top / bot)
public class TimingSection implements Writable {
    private int timestamp;
    private double bpm;
    private TimeSignature timesig;


    public TimingSection(int time, double bpm, TimeSignature ts) {
        this.timestamp = time;
        this.bpm = bpm;
        timesig = ts;
    }

    public TimingSection(int time, double bpm, int top, int bot) {
        timestamp = time;
        this.bpm = bpm;
        timesig = new TimeSignature(top, bot);
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

    // REQUIRES: newOffset, newBPM, newTop, and newBot >= 0
    // MODIFIES: this
    // EFFECTS: edits provided timing section with new values
    public void editSection(int newOffset, double newBPM, int newTop, int newBot) {
        setTime(newOffset);
        setBPM(newBPM);
        setTimeSig(newTop, newBot);
    }


    // REQUIRES: bpm > 0
    // MODIFIES:
    // EFFECTS: returns length of a quarter note in milliseconds
    public double beatLength() {
        return 60000 / this.getBPM();
    }


    @Override
    // EFFECTS: returns JSON version of timing section
    public JSONObject toJson() {
        JSONObject jsonSection = new JSONObject();
        jsonSection.put("offset", timestamp);
        jsonSection.put("BPM", bpm);
        String signature = this.timesig.getTop() + " / " + this.timesig.getBot();
        jsonSection.put("Signature", signature);
        return jsonSection;
    }


}
