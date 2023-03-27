package ui.tools;

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

    // MODIFIES: this
    // EFFECTS: creates popup menu with field names
    public SectionEditor(TimingSection ts) {
        super("Edit selected section");
        setLayout(new GridLayout(3,1));
        timingSection = ts;
        initializeFields();
    }

    // REQUIRES: offset is an integer and > 0
    //           bpm is a double and > 0
    //           both time signature fields are integers and > 0
    // MODIFIES: this
    // EFFECTS: sets up text inputs to modify section
    private void initializeFields() {
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
        timingSection.setTime(newOffset);
        timingSection.setBPM(newBPM);
        timingSection.setTimeSig(newTop, newBot);
    }
}
