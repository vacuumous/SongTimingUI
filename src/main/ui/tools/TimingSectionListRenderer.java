package ui.tools;

import model.TimeSignature;
import model.TimingSection;

import javax.swing.*;
import java.awt.*;

// Custom renderer to display TimingSection information in the JList
// Uses example from ListCellRenderer documentation as starting point
public class TimingSectionListRenderer implements ListCellRenderer<TimingSection> {


    // EFFECTS: renders timing section as offset: x, BPM: y, time signature: a/b
    @Override
    public Component getListCellRendererComponent(JList<? extends TimingSection> list, TimingSection value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel offsetLabel = new JLabel("Offset:" + value.getTime());
        JLabel bpmLabel = new JLabel("BPM:" + value.getBPM());
        TimeSignature sig = value.getTimesig();
        JLabel sigLabel = new JLabel("Time Signature:" + sig.getTop() + "/" + sig.getBot());
        panel.add(offsetLabel);
        panel.add(bpmLabel);
        panel.add(sigLabel);

        if (isSelected) {
            panel.setBackground(list.getSelectionBackground());
            panel.setForeground(list.getSelectionForeground());
        } else {
            panel.setBackground(list.getBackground());
            panel.setForeground(list.getForeground());
        }

        return panel;
    }
}
