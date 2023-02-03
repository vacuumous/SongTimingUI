package model;

public class TimingSection {

    private int timeStamp;
    private double bpm;
    private TimeSignature timesig;

    public TimingSection(int time, double bpm, TimeSignature ts) {
        this.timeStamp = time;
        this.bpm = bpm;
        this.timesig = ts;
    }


}
