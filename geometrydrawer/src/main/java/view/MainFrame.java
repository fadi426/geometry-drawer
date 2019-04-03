package view;

import model.shapes.Ornament;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private JPanel mainPanel;
    private JTextArea shapeInfoTA;
    private JPanel drawPanel;
    private JButton circleBtn;
    private JButton rectangleBtn;
    private JButton clearBtn;
    private JButton selectBtn;
    private JButton UndoBtn;
    private JButton RedoBtn;
    private JButton groupBtn;
    private JButton moveBtn;
    private JButton resizeBtn;
    private JButton SaveBtn;
    private JButton LoadBtn;
    private JButton OrnamentBtn;

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
    public JButton getRectangleBtn() { return rectangleBtn; }
    public JButton getClearBtn() { return clearBtn; }
    public JButton getSelectBtn() { return selectBtn; }
    public JButton getUndoBtn() {return UndoBtn;}
    public JButton getRedoBtn() {return RedoBtn;}
    public JButton getGroupBtn() {return groupBtn;}
    public JButton getMoveBtn() {return moveBtn; }
    public JButton getResizeBtn() { return resizeBtn; }
    public JButton getSaveBtn(){return SaveBtn;}
    public JButton getLoadBtn(){return LoadBtn;}
    public JButton getOrnamentBtn(){ return OrnamentBtn;}
}

