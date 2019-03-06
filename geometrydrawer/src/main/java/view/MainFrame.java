package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private JPanel mainPanel;
    private JButton welcomeBtn;
    private JTextArea welcomeTA;
    private JPanel drawPanel;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(mainPanel);
        setSize(WIDTH, HEIGHT);
        setLocation(550, 25);
        setVisible(true);
    }

    public JPanel getDrawPanel() {
        return drawPanel;
    }

    public JButton getWelcomeBtn() {
        return welcomeBtn;
    }

    public JTextArea getWelcomeTA() {
        return welcomeTA;
    }
}

