package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.Mouse;
import model.commands.*;
import model.shapes.Circle;
import model.shapes.Rectangle;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCanvas;
import model.singleObjects.SingletonCmdMng;
import model.strategies.RectangleStrategy;
import model.strategies.ShapeContext;
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
    private JButton moveBtn;
    private JButton resizeBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton ornamentBtn;

    private JPanel drawPanel;
    private CanvasController currCanvas;
    private Mouse mouse;

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
        commandManager = SingletonCmdMng.getInstance();
        mouse = SingleMouse.getInstance();
        mouse.setCanvas(currCanvas);

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
        moveBtn = mainFrame.getMoveBtn();
        resizeBtn = mainFrame.getResizeBtn();
        saveBtn = mainFrame.getSaveBtn();
        loadBtn = mainFrame.getLoadBtn();
        ornamentBtn = mainFrame.getOrnamentBtn();
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
        moveBtn.addActionListener(new moveBtnListener());
        resizeBtn.addActionListener(new resizeBtnListener());
        saveBtn.addActionListener(new saveBtnListener());
        loadBtn.addActionListener(new loadBtnListener());
        ornamentBtn.addActionListener(new ornamentBtnListener());
    }

    private class circleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("draw");
            currCanvas.setCurrShape(new Circle());
            welcomeTA.append("circle\n");
        }
    }
    private class rectangleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("draw");
            currCanvas.setCurrShape(new Rectangle());
            welcomeTA.append("rectangle\n");
        }
    }

    private class clearBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new ClearCommand(currCanvas.toList()));
            welcomeTA.append("cleared\n");
        }
    }

    private class selectBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("select");
            welcomeTA.append("cleared\n");
            currCanvas.clearSelect();
            currCanvas.repaint();
        }
    }


    private class undoBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Undo();
            currCanvas.repaint();
        }
    }

    private class redoBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Redo();
            currCanvas.repaint();
        }
    }

    private class groupBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currCanvas.createGroup();
        }
    }

    private class moveBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { mouse.setOperation("move"); }
    }

    private class resizeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { mouse.setOperation("resize"); }
    }

    private class saveBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new SaveCommand());
        }
    }

    private class loadBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new LoadCommand());
        }
    }

    private class ornamentBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new OrnamentCommand());
        }
    }
}
