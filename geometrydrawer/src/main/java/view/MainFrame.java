package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private JPanel mainPanel;
    private JTextArea shapeInfoTA;
    private JPanel drawPanel;
    private JButton ellipseBtn;
    private JButton rectangleBtn;
    private JButton clearBtn;
    private JButton selectBtn;
    private JButton undobtn;
    private JButton redoBtn;
    private JButton groupBtn;
    private JButton moveBtn;
    private JButton resizeBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton ornamentBtn;
    private JButton deleteBtn;

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

    public JButton getEllipseBtn() {
        return ellipseBtn;
    }

    public JButton getRectangleBtn() {
        return rectangleBtn;
    }

    public JButton getClearBtn() {
        return clearBtn;
    }

    public JButton getSelectBtn() {
        return selectBtn;
    }

    public JButton getUndobtn() {
        return undobtn;
    }

    public JButton getRedoBtn() {
        return redoBtn;
    }

    public JButton getGroupBtn() {
        return groupBtn;
    }

    public JButton getMoveBtn() {
        return moveBtn;
    }

    public JButton getResizeBtn() {
        return resizeBtn;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getLoadBtn() {
        return loadBtn;
    }

    public JButton getOrnamentBtn() {
        return ornamentBtn;
    }

    public JButton getDeleteBtn() { return deleteBtn; }
}

