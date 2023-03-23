package ui;

import javax.swing.*;
import java.awt.*;

public class TimingEditor extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;


    public TimingEditor() {
        super("Timing Editor");
        initializeGraphics();
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
