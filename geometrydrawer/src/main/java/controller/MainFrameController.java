package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.DrawPanel;
import view.MainFrame;

public class MainFrameController {

    private MainFrame mainFrame;
    private JButton welcomeBtn;
    private JTextArea welcomeTA;
    private JPanel drawPanel;

    public MainFrameController() {
        initComponents();
        initListeners();
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        mainFrame = new MainFrame();

        welcomeBtn = mainFrame.getWelcomeBtn();
        welcomeTA = mainFrame.getWelcomeTA();
        drawPanel = mainFrame.getDrawPanel();
        drawPanel.setLayout(new BorderLayout());
        drawPanel.add(new DrawPanel(), BorderLayout.NORTH);
    }

    private void initListeners() {
        welcomeBtn.addActionListener(new WelcomeBtnLister());
    }

    private class WelcomeBtnLister implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            welcomeTA.append("Hello Hello\n");
        }
    }
}
