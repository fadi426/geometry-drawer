package controller;


import model.Pentagon;
import model.Shape;

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

    public Class<?> shapeType;
    public Shape currShape;
    private Color drawingColor = Color.BLACK;

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
                            currShape = (Shape) shapeType.newInstance();
                            currShape.setCurrentColor(drawingColor);
                        } catch (InstantiationException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (NullPointerException e3) {
                            JOptionPane.showMessageDialog(getParent(), "No Shape Selected!");
                        }
                        if (currShape != null) {
                            currShape.setShapeStart(e.getPoint());
                        }
                        break;
                    case "select":
                        oldX = e.getX();
                        oldY = e.getY();
                        currentX = oldX;
                        currentY = oldY;
                        boolean shapeFound = false;

                        for (int i = listmodel.size() - 1; i >= 0; i = i - 1) {
                            Shape shape = listmodel.get(i);
                            if (shape.contain(currentX, currentY)) {
                                selectedShapes.add(shape);
                                shapeFound = true;
                                System.out.println("found shape");
                            }
                        }
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

    public void setCurrShape(Class<?> toDraw){
        shapeType = toDraw;
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
}
