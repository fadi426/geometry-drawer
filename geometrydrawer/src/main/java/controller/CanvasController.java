package controller;


import com.sun.org.apache.bcel.internal.generic.Select;
import model.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CanvasController extends JPanel {

    public Shape shapeType;
    public Shape currShape;
    private Color drawingColor = Color.BLACK;
    private CommandManager commandManager = SingletonCmdMng.getInstance();;

    public DefaultListModel<Shape> listmodel = new DefaultListModel<Shape>();
    public static List<Shape> selectedShapes = new ArrayList<>();

    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    private String operation;

    public CanvasController(Color bg){
        this.setBackground(bg);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                switch (operation) {
                    case "draw":
                        try {
                            Class c=Class.forName(shapeType.getClass().getName());
                            currShape =(Shape)c.newInstance();
                            commandManager.Execute(new MakeShapeCommand(currShape));
                            currShape.setCurrentColor(drawingColor);
                        } catch (NullPointerException e3) {
                            JOptionPane.showMessageDialog(getParent(), "No Shape Selected!");
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (InstantiationException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        if (currShape != null) {
                            currShape.setShapeStart(e.getPoint());
                        }
                        break;
                    case "select":
                        selectShape(e);
                        break;
                }
            }

            public void mouseReleased(MouseEvent e){
                if(currShape != null){
                    listmodel.addElement(currShape);
                    currShape=null;
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e){
                if(currShape!= null){
                    currShape.setShapeEnd(e.getPoint());
                    repaint();
                }
            }
        });
    }


    public void setCurrShape(Shape toDraw){
        shapeType = toDraw;
    }

    public void removeLastElement(){
        if (listmodel.size() > 0) {
            int last = listmodel.size() - 1;
            listmodel.removeElementAt(last);
            repaint();
        }
    }

    public void addElementToList(Shape shape){
        listmodel.addElement(shape);
        repaint();
    }

    public void clear(){
        listmodel.clear();
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0;i < listmodel.getSize();i++){
            listmodel.elementAt(i).draw(g);
            if(listmodel.elementAt(i).isFilled()){
                listmodel.elementAt(i).fill(g);
            }
        }

        if(currShape != null){
            currShape.draw(g);
        }
    }

    public void setOperation(String o){
        operation = o;
    }


    public void changeColor(Shape shape, Color color){
        shape.setCurrentColor(color);
        repaint();
    }

    public void selectShape(MouseEvent e){
        oldX = e.getX();
        oldY = e.getY();
        currentX = oldX;
        currentY = oldY;
        int unSelectedCounter = 0;

        for (int i = listmodel.size() - 1; i >= 0; i = i - 1) {
            Shape shape = listmodel.get(i);

            if (!shape.contain(currentX, currentY)) {
                unSelectedCounter++;
                System.out.println(unSelectedCounter);
                continue;
            }

            if (selectedShapes.contains(shape)) {
                selectedShapes.remove(shape);
                changeColor(shape, Color.BLACK);
                break;
            }

            selectedShapes.add(shape);
            changeColor(shape, Color.RED);
            break;
        }

        if (unSelectedCounter == listmodel.size() ) {
            for (Shape s : selectedShapes){
                changeColor(s, Color.BLACK);
            }
            selectedShapes.clear();
        }
    }
}
