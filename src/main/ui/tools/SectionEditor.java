package ui.tools;

import model.Song;
import model.TimeSignature;
import model.TimingSection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A popup menu that allows manipulation of a timing section
public class SectionEditor extends JOptionPane {

    private JTextField offset;
    private JTextField bpm;
    private JTextField timesig;
    private TimingSection timingSection;
    private Song song;

    // MODIFIES: this
    // EFFECTS: creates popup menu with field names
    public SectionEditor(Song song, TimingSection ts) {
        super("Edit selected section");
        setLayout(new GridLayout(3,1));
        this.song = song;
        this.timingSection = ts;
        editExisting();

    }

    public SectionEditor(Song song) {
        super("Make new section");
        setLayout(new GridLayout(3,1));
        this.song = song;
        makeNew();
    }

    // REQUIRES: offset is an integer and > 0
    //           bpm is a double and > 0
    //           both time signature fields are integers and > 0
    // MODIFIES: this
    // EFFECTS: sets up text inputs to modify section
    private void editExisting() {
        TimeSignature signature = timingSection.getTimesig();
        JTextField offset = new JTextField(Integer.toString(timingSection.getTime()));
        JTextField bpm = new JTextField(Double.toString(timingSection.getBPM()));
        JTextField timesig = new JTextField(signature.getTop() + "/" + signature.getBot());
        Object[] fields = {
                "Offset:", offset,
                "BPM:", bpm,
                "Time Signature:", timesig
        };
        JOptionPane.showConfirmDialog(null, fields, "Edit selected Section", OK_OPTION);
        int newOffset = Integer.parseInt(offset.getText());
        double newBPM = Double.parseDouble(bpm.getText());
        String newSignature = timesig.getText();
        String[] split = newSignature.split("/", 0);
        int newTop = Integer.parseInt(split[0]);
        int newBot = Integer.parseInt(split[1]);
        song.editSection(timingSection, newOffset, newBPM, newTop, newBot);
    }

    public void makeNew() {
        JTextField offset = new JTextField("0");
        JTextField bpm = new JTextField("120");
        JTextField timesig = new JTextField("4/4");


        Object[] fields = {
                "Offset:", offset,
                "BPM:", bpm,
                "Time Signature:", timesig
        };
        JOptionPane.showConfirmDialog(null, fields, "Create new section", OK_OPTION);
        int newOffset = Integer.parseInt(offset.getText());
        double newBPM = Double.parseDouble(bpm.getText());
        String newSignature = timesig.getText();
        String[] split = newSignature.split("/", 0);
        int newTop = Integer.parseInt(split[0]);
        int newBot = Integer.parseInt(split[1]);
        timingSection = new TimingSection(newOffset,newBPM,newTop,newBot);
        song.addSection(timingSection);

    }
}
