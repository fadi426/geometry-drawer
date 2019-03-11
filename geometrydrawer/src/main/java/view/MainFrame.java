package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private JPanel mainPanel;
    private JTextArea shapeInfoTA;
    private JPanel drawPanel;
    private JButton circleBtn;
    private JButton lineBtn;
    private JButton polygonBtn;
    private JButton rectangleBtn;
    private JButton clearBtn;
    private JButton selectBtn;

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

    public JTextArea getShapeInfoTA() {
        return shapeInfoTA;
    }

    public JButton getCircleBtn() {
        return circleBtn;
    }

    public JButton getLineBtn() { return lineBtn; }

    public JButton getPolygonBtn() { return polygonBtn; }

    public JButton getRectangleBtn() { return rectangleBtn; }

    public JButton getClearBtn() { return clearBtn; }

    public JButton getSelectBtn() { return selectBtn; }
}

