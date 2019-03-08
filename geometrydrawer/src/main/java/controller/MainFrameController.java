package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import model.Circle;
import model.Line;
import model.Polygon;
import model.Rectangle;
import model.Shape;
import view.MainFrame;

public class MainFrameController {

    private MainFrame mainFrame;
    private JTextArea welcomeTA;
    private JButton circleBtn;
    private JButton lineBtn;
    private JButton polygonBtn;
    private JButton rectangleBtn;
    private JButton clearBtn;
    private JPanel drawPanel;
    private CanvasController currCanvas = new CanvasController(Color.WHITE);

    public MainFrameController() {
        initComponents();
        initListeners();
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        mainFrame = new MainFrame();
        welcomeTA = mainFrame.getShapeInfoTA();
        drawPanel = mainFrame.getDrawPanel();
        drawPanel.setLayout(new BorderLayout());
        circleBtn = mainFrame.getCircleBtn();
        lineBtn = mainFrame.getLineBtn();
        polygonBtn = mainFrame.getPolygonBtn();
        rectangleBtn = mainFrame.getRectangleBtn();
        clearBtn = mainFrame.getClearBtn();
    }

    private void createCanvas(int width,int height){
        drawPanel.removeAll();
        currCanvas=new CanvasController(Color.WHITE);
        currCanvas.setPreferredSize(new Dimension(width,height));
        drawPanel.add(currCanvas);
    }

    private void initListeners() {
        createCanvas(500,500);
        circleBtn.addActionListener(new circleBtnLister());
        lineBtn.addActionListener(new lineBtnListener());
        polygonBtn.addActionListener(new polygonBtnListener());
        rectangleBtn.addActionListener(new rectangleBtnListener());
        clearBtn.addActionListener(new clearBtnListener());
    }

    private class circleBtnLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currCanvas!=null){
                currCanvas.setCurrShape(Circle.class);
            }
            welcomeTA.append("circle\n");
        }
    }
    private class lineBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currCanvas!=null){
                currCanvas.setCurrShape(Line.class);
            }
            welcomeTA.append("line\n");
        }
    }
    private class polygonBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currCanvas!=null){
                currCanvas.setCurrShape(Polygon.class);
            }
            welcomeTA.append("polygon\n");
        }
    }
    private class rectangleBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currCanvas!=null){
                currCanvas.setCurrShape(Rectangle.class);
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


}
