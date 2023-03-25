package ui;

import model.Song;
import model.TimeSignature;
import model.TimingSection;
import persistence.JsonReader;
import ui.tools.AddTool;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class TimingEditor extends JFrame implements ActionListener {

    public static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private Song song;

    private enum AddRemove {
        AddSection,
        RemoveSection
    }

    public TimingEditor() {
        super("Timing Editor");
        initializeSong();
        initializeList();
        initializeGraphics();
        System.out.println(song.getTitle());
    }

    //EFFECTS: prompts user to set up a new song or load a previous one
    private void initializeSong() {
        String msg = "Load existing song?";
        JOptionPane loadQuestion = new JOptionPane(msg);
        int result = loadQuestion.showConfirmDialog(null, msg, "", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            loadSong();
        } else {
            newSong();
        }
    }

    // EFFECTS: makes new song from user input with no timing sections yet
    private void newSong() {
        System.out.println("new Song");
    }

    // REQUIRES: selected JSON file is in correct format
    // MODIFIES: this
    // EFFECTS: loads a song from JSON file
    private void loadSong() {
        JFileChooser browser = new JFileChooser("./data/");
        int select = browser.showOpenDialog(this);
        if (select == JFileChooser.CANCEL_OPTION) {
            newSong();
        }
        if (select == JFileChooser.APPROVE_OPTION) {
            String filePath = browser.getSelectedFile().getPath();
            JsonReader reader = new JsonReader(filePath);
            try {
                song = reader.read();
            } catch (IOException e) {
                System.err.println("Could not read file");
            }

        }
    }

    // EFFECTS: Sets up the layout for window
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createButtons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // EFFECTS: creates panel with buttons to manipulate timing sections
    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,1));
        buttonPanel.setSize(new Dimension(0,0));
        add(buttonPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add section");
        addButton.setActionCommand(AddRemove.AddSection.name());
        addButton.addActionListener(this);

        JButton removeButton = new JButton("Remove section");
        removeButton.addActionListener(this);
        removeButton.setActionCommand(AddRemove.RemoveSection.name());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.setVisible(true);

    }

    // TODO
    // Create a pane to view the list of timing sections
    private void initializeList() {
        DefaultListModel<String> list = new DefaultListModel<>();
        list.addElement("A");
        list.addElement("A");
        JList<String> sectionList = new JList<>(list);
        add(sectionList, BorderLayout.WEST);
        sectionList.setSize(new Dimension(0,0));
        sectionList.setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == AddRemove.AddSection.name()) {
            newSection();
        }
        if (e.getActionCommand() == AddRemove.RemoveSection.name()) {
            deleteSection(new TimingSection(0,0, 0, 0));
        }
    }

    // TODO:
    // MODIFIES: this
    // EFFECTS: adds a new section to song
    private void newSection() {

    }

    // TODO:
    // MODIFIES: this
    // EFFECTS: remove selected section from song
    private void deleteSection(TimingSection ts) {

    }

}
