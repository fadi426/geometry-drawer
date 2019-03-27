package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.*;
import model.Rectangle;
import view.MainFrame;

public class MainFrameController {

    private MainFrame mainFrame;
    private CommandManager commandManager;
    private JTextArea welcomeTA;

    private JButton circleBtn;
    private JButton rectangleBtn;
    private JButton clearBtn;
    private JButton selectBtn;
    private JButton undoBtn;
    private JButton redoBtn;
    private JButton groupBtn;

    private JPanel drawPanel;
    private CanvasController currCanvas;

    public MainFrameController() {
        initComponents();
        initListeners();
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        mainFrame = new MainFrame();
        currCanvas = SingletonCanvas.getInstance();

        welcomeTA = mainFrame.getShapeInfoTA();
        drawPanel = mainFrame.getDrawPanel();
        drawPanel.setLayout(new BorderLayout());
        circleBtn = mainFrame.getCircleBtn();
        rectangleBtn = mainFrame.getRectangleBtn();
        clearBtn = mainFrame.getClearBtn();
        selectBtn = mainFrame.getSelectBtn();
        undoBtn = mainFrame.getUndoBtn();
        redoBtn = mainFrame.getRedoBtn();
        groupBtn = mainFrame.getGroupBtn();
    }

    private void createCanvas(int width,int height){
        drawPanel.removeAll();
        currCanvas = SingletonCanvas.getInstance();
        currCanvas.setPreferredSize(new Dimension(width,height));
        drawPanel.add(currCanvas);
    }

    private void initListeners() {
        createCanvas(500,500);
        circleBtn.addActionListener(new circleBtnListener());
        rectangleBtn.addActionListener(new rectangleBtnListener());
        clearBtn.addActionListener(new clearBtnListener());
        selectBtn.addActionListener(new selectBtnListener());
        undoBtn.addActionListener(new undoBtnListener());
        redoBtn.addActionListener(new redoBtnListener());
        groupBtn.addActionListener(new groupBtnListener());
    }

    private class circleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currCanvas.setOperation("draw");
            if(currCanvas!=null){
                currCanvas.setCurrShape(new Circle());
            }
            welcomeTA.append("circle\n");
        }
    }
    private class rectangleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currCanvas.setOperation("draw");
            if(currCanvas!=null){
                currCanvas.setCurrShape(new Rectangle());
            }
            welcomeTA.append("rectangle\n");
        }
    }

    private class clearBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currCanvas!=null){
                currCanvas.clear();
            }
            welcomeTA.append("cleared\n");
        }
    }

    private class selectBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currCanvas.setOperation("select");
            welcomeTA.append("cleared\n");
        }
    }


    private class undoBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager = SingletonCmdMng.getInstance();
            commandManager.Undo();
        }
    }

    private class redoBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager = SingletonCmdMng.getInstance();
            commandManager.Redo();
        }
    }

    private class groupBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currCanvas.createGroup();
        }
    }
}
