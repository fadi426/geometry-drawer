package controller;


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
    private CommandManager commandManager = new CommandManager();

    public DefaultListModel<Shape> listmodel = new DefaultListModel<Shape>();
    public static List<Shape> selectedShapes = new ArrayList<>();

    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    private String operation;

    public CanvasController(Color bg, CanvasController canvas){
        this.setBackground(bg);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                switch (operation) {
                    case "draw":
                        try {
                            Class c=Class.forName(shapeType.getClass().getName());
                            currShape =(Shape)c.newInstance();
                            commandManager.Execute(new MakeShapeCommand(currShape, canvas));
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
                    System.out.println(listmodel.size());
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
            System.out.println(listmodel);
            listmodel.removeElementAt(last);
            System.out.println(listmodel);
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

    public CommandManager getCommandManager(){
        return commandManager;
    }

}
