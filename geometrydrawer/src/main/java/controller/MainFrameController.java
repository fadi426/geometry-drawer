package controller;

import model.adapters.Mouse;
import model.commands.*;
import model.shapes.Circle;
import model.shapes.Rectangle;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCanvas;
import model.singleObjects.SingletonCmdMng;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController {

    private MainFrame mainFrame;
    private CommandManager commandManager;
    private JTextArea shapeInfoTA;

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
    private JButton deleteBtn;

    private JPanel drawPanel;
    private CanvasController currCanvas;
    private Mouse mouse;

    public MainFrameController() {
        initComponents();
        initListeners();
    }

    /**
     * Makes the mainFrame visible
     */
    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    /**
     * Initialize the components from the MainFrame.form
     */
    private void initComponents() {
        mainFrame = new MainFrame();
        currCanvas = SingletonCanvas.getInstance();
        commandManager = SingletonCmdMng.getInstance();
        mouse = SingleMouse.getInstance();
        mouse.setCanvas(currCanvas);

        shapeInfoTA = mainFrame.getShapeInfoTA();
        drawPanel = mainFrame.getDrawPanel();
        drawPanel.setLayout(new BorderLayout());
        circleBtn = mainFrame.getCircleBtn();
        rectangleBtn = mainFrame.getRectangleBtn();
        clearBtn = mainFrame.getClearBtn();
        selectBtn = mainFrame.getSelectBtn();
        undoBtn = mainFrame.getUndobtn();
        redoBtn = mainFrame.getRedoBtn();
        groupBtn = mainFrame.getGroupBtn();
        moveBtn = mainFrame.getMoveBtn();
        resizeBtn = mainFrame.getResizeBtn();
        saveBtn = mainFrame.getSaveBtn();
        loadBtn = mainFrame.getLoadBtn();
        ornamentBtn = mainFrame.getOrnamentBtn();
        deleteBtn = mainFrame.getDeleteBtn();
    }

    /**
     * Creates the drawingCanvas to be used to draw figures on
     * @param width is the width of the canvas
     * @param height is the height of the canvas
     */

    private void createCanvas(int width, int height) {
        drawPanel.removeAll();
        currCanvas = SingletonCanvas.getInstance();
        currCanvas.setPreferredSize(new Dimension(width, height));
        drawPanel.add(currCanvas);
        currCanvas.setShapeInfoTA(shapeInfoTA);
    }

    /**
     * Initialize the Buttonlisteners for the buttons
     */
    private void initListeners() {
        createCanvas(900, 900);
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
        deleteBtn.addActionListener(new deleteBtnListener());
    }

    private class circleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("draw");
            currCanvas.setCurrentShapeType(new Circle());
        }
    }

    private class rectangleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("draw");
            currCanvas.setCurrentShapeType(new Rectangle());
        }
    }

    private class clearBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new ClearCommand(currCanvas.toList()));
            shapeInfoTA.append("cleared\n");
        }
    }

    private class selectBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("select");
            currCanvas.clearSelect();
            currCanvas.repaint();
        }
    }


    private class undoBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Undo();
            shapeInfoTA.append("Undo");
            currCanvas.repaint();
        }
    }

    private class redoBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Redo();
            shapeInfoTA.append("Redo");
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
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("move");
        }
    }

    private class resizeBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mouse.setOperation("resize");
        }
    }

    private class saveBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shapeInfoTA.append("Save file");
            commandManager.Execute(new SaveCommand());
        }
    }

    private class loadBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shapeInfoTA.append("Load file");
            commandManager.Execute(new LoadCommand());
        }
    }

    private class ornamentBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new OrnamentCommand());
        }
    }

    private class deleteBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandManager.Execute(new DeleteCommand());
        }
    }
}
