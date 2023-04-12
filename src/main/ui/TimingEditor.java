package ui;

import model.Song;
import model.TimeSignature;
import model.TimingSection;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.EditorWindowListener;
import ui.tools.SectionEditor;
import ui.tools.TimingSectionListRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

// A GUI to time songs.
// Used SimpleDrawingPlayer as a starting point
public class TimingEditor extends JFrame implements ActionListener, ListSelectionListener {

    public static final int WIDTH = 800;
    private static final int HEIGHT = 675;
    private Song song;
    private DefaultListModel<TimingSection> timeList;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem changeMetadata;
    private TimingSection selectedSection;
    private JList<TimingSection> timeJList;
    private JLabel titleLabel;
    private JLabel artistLabel;
    private JLabel timesigLabel;
    private JLabel bpmLabel;
    private JLabel offsetLabel;
    private JPanel sectionDetails;
    private final String addSection = "Add Section";
    private final String editSection = "Edit Section";
    private final String removeSection = "Remove Section";
    private ImageIcon background;


    // EFFECTS: constructs an editor to time a song in
    public TimingEditor() {
        super("Timing Editor");

        background = new ImageIcon("./data/Garden.jpg");
        setContentPane(new JLabel(background));

        initializeFields();
        initializeSong();
        initializeMenu();
        initializeGraphics();
        addWindowListener(new EditorWindowListener());
    }

    // MODIFIES: this
    // EFFECTS: sets up fields that would otherwise be null upon use
    private void initializeFields() {
        song = new Song("","");
        titleLabel = new JLabel("");
        artistLabel = new JLabel("");
    }

    // MODIFIES: this
    //EFFECTS: prompts user to set up a new song or load a previous one
    private void initializeSong() {
        String msg = "Load existing song?";
        JOptionPane loadQuestion = new JOptionPane(msg);
        int result = loadQuestion.showConfirmDialog(null, msg, "", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            loadSong();
        } else {
            changeSong();
        }
    }

    // REQUIRES: selected JSON file is in correct format
    // MODIFIES: this
    // EFFECTS: loads a song from JSON file
    private void loadSong() {
        JFileChooser browser = new JFileChooser("./data/");
        int select = browser.showOpenDialog(this);
        if (select == JFileChooser.CANCEL_OPTION) {
            changeSong();
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
        if (timeList != null) {
            timeList.removeAllElements();
            for (TimingSection ts : song.getTimingSections()) {
                timeList.addElement(ts);
            }
        }
    }

    // EFFECTS: saves current song
    private void saveSong() {
        JFileChooser browser = new JFileChooser("./data/");
        browser.setDialogTitle("Save current song");
        if (browser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String saveToPath = browser.getSelectedFile().getPath();
            JsonWriter writer = new JsonWriter(saveToPath);
            try {
                writer.open();
                writer.write(song);
                writer.close();
            } catch (FileNotFoundException e) {
                saveSong();
            }
        }
    }

    // EFFECTS: creates menu with save and load options
    private void initializeMenu() {
        JMenu menu = new JMenu("File");
        JMenuBar menuBar = new JMenuBar();
        save = new JMenuItem("Save to...");
        load = new JMenuItem("Load from...");
        changeMetadata = new JMenuItem("Change song title/artist");
        menu.add(save);
        menu.add(load);
        menu.add(changeMetadata);
        save.addActionListener(this);
        load.addActionListener(this);
        changeMetadata.addActionListener(this);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    // EFFECTS: displays current title and artist
    private void viewMetadata() {
        JPanel metadata = new JPanel();
        metadata.setLayout(new GridLayout(2,1));
        add(metadata, BorderLayout.NORTH);
        metadata.setVisible(true);

        titleLabel = new JLabel("Title: " + song.getTitle(), JLabel.CENTER);
        titleLabel.setVisible(true);
        metadata.add(titleLabel);

        artistLabel = new JLabel("Artist: " + song.getArtist(), JLabel.CENTER);
        artistLabel.setVisible(true);
        metadata.add(artistLabel);

    }

    // EFFECTS: shows details for currently selected section
    private void viewSection() {
        sectionDetails = new JPanel();
        sectionDetails.setLayout(new FlowLayout());

        offsetLabel = new JLabel("Offset: ");
        sectionDetails.add(offsetLabel);

        bpmLabel = new JLabel("BPM: ");
        sectionDetails.add(bpmLabel);

        timesigLabel = new JLabel("Time Signature: ");
        sectionDetails.add(timesigLabel);

        add(sectionDetails, BorderLayout.SOUTH);
    }

    // EFFECTS: updates view section details
    private void updateViewSection() {
        offsetLabel.setText("Offset: " + selectedSection.getTime());
        bpmLabel.setText("BPM: " + selectedSection.getBPM());
        TimeSignature ts = selectedSection.getTimesig();
        timesigLabel.setText("Time Signature: " + ts.getTop() + "/" + ts.getBot());
    }

    // EFFECTS: Sets up the layout for window
    private void initializeGraphics() {
        setLayout(new BorderLayout(30, 30));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));



        createButtons();
        viewSection();
        initializeList();
        viewMetadata();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);




    }

    // EFFECTS: creates panel with buttons to manipulate timing sections
    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,1));
        buttonPanel.setSize(new Dimension(0,0));
        add(buttonPanel, BorderLayout.EAST);

        JButton addButton = new JButton("Add section");
        addButton.setActionCommand(addSection);
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit section");
        editButton.addActionListener(this);
        editButton.setActionCommand(editSection);
        buttonPanel.add(editButton);

        JButton removeButton = new JButton("Remove section");
        removeButton.addActionListener(this);
        removeButton.setActionCommand(removeSection);
        buttonPanel.add(removeButton);

        buttonPanel.setVisible(true);

    }

    // Creates a pane to view the list of timing section offsets
    private void initializeList() {
        timeList = new DefaultListModel<>();
        for (TimingSection ts : song.getTimingSections()) {
            timeList.addElement(ts);
        }

        timeJList = new JList<>(timeList);
        timeJList.setCellRenderer(new TimingSectionListRenderer());

        timeJList.setVisible(true);
        timeJList.setLayoutOrientation(JList.VERTICAL);
        timeJList.setSelectionMode(SINGLE_SELECTION);
        timeJList.addListSelectionListener(this);


        JScrollPane scrollableList = new JScrollPane();
        scrollableList.setViewportView(timeJList);
        scrollableList.setPreferredSize(new Dimension(300,0));
        add(scrollableList, BorderLayout.WEST);

    }

    // REQUIRES: ts != null
    // MODIFIES: this
    // EFFECTS: Edits an existing section and updates related components
    private void editExistingSection(TimingSection ts) {
        int sectionIndex = timeList.indexOf(ts);
        JOptionPane editor = new SectionEditor(song, ts);
        timeList.set(sectionIndex, ts);
    }

    // EFFECTS: Creates a new section and adds it to the list of sections
    //          if new section has the same time as an already existing section, offset will be incremented by 1
    private void makeNewSection() {
        JOptionPane editor = new SectionEditor(song);
        for (TimingSection ts : song.getTimingSections()) {
            if (!timeList.contains(ts)) {
                timeList.addElement(ts);
            }
        }

    }

    // EFFECTS: removes timing section from the list
    private void removeSelectedSection(TimingSection ts) {
        int sectionIndex = timeList.indexOf(ts);
        timeList.remove(sectionIndex);
    }

    // EFFECTS: prompts user to change song title and/or artist
    private void changeSong() {
        JTextField title = new JTextField(song.getTitle());
        JTextField artist = new JTextField(song.getArtist());
        Object[] fields = {
                "Title:", title,
                "Artist:", artist
        };
        JOptionPane.showConfirmDialog(null, fields, "Edit metadata", OK_OPTION);
        song.setTitle(title.getText());
        song.setArtist(artist.getText());
        titleLabel.setText("Title:" + title.getText());
        artistLabel.setText("Artist:" + artist.getText());
    }

    // EFFECTS: catch-all for handling actions
    public void actionPerformed(ActionEvent e) {
        if (timeJList.getSelectedIndex() != -1) {
            selectedSection = timeJList.getSelectedValue();
        }
        if (e.getActionCommand().equals(addSection)) {
            makeNewSection();
        }
        if (e.getActionCommand().equals(editSection)) {
            editExistingSection(selectedSection);
        }
        if (e.getActionCommand().equals(removeSection)) {
            removeSelectedSection(selectedSection);
        }
        if (e.getSource() == save) {
            saveSong();
        }
        if (e.getSource() == load) {
            loadSong();
        }
        if (e.getSource() == changeMetadata) {
            changeSong();
        }
        repaint();
    }

    // EFFECTS: handles list selection events
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (timeJList.getSelectedValue() != null) {
            selectedSection = timeJList.getSelectedValue();
            updateViewSection();
        }
    }

}
