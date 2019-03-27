package controller;


import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private CommandManager commandManager = SingletonCmdMng.getInstance();

    public Group mainGroup = new Group();
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
                        draw(e);
                        break;
                    case "select":
                        selectShape(e);
                        break;
                }
            }

            public void mouseReleased(MouseEvent e){
                if(currShape != null){
                    listmodel.addElement(currShape);
                    mainGroup.addShape(currShape);
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

    private void draw(MouseEvent e) {
        try {
            Class c=Class.forName(shapeType.getClass().getName());
            currShape =(Shape)c.newInstance();
            commandManager.Execute(new MakeShapeCommand(currShape));
            currShape.setColor(drawingColor);
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
    }


    public void setCurrShape(Shape toDraw){
        shapeType = toDraw;
    }

    public void removeLastElement(){
        if (listmodel.size() > 0) {
            int last = listmodel.size() - 1;
            mainGroup.removeShape(listmodel.get(last));
            listmodel.removeElementAt(last);
            repaint();
        }
    }

    public void addElementToList(Shape shape){
        listmodel.addElement(shape);
        mainGroup.addShape(shape);
        repaint();
    }

    public void clear(){
        listmodel.clear();
        mainGroup.clear();
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

    public void selectShape(MouseEvent e){
        oldX = e.getX();
        oldY = e.getY();
        currentX = oldX;
        currentY = oldY;
        int unSelectedCounter = 0;

        for (int i = listmodel.size() - 1; i >= 0; i = i - 1) {
            Shape shape = listmodel.get(i);

            if (shape.getSubShapes().size()> 0 && shape.contain(currentX, currentY, shape.getSubShapes())){
                shape.setColor(Color.RED);
            }
            else if (shape.contain(currentX, currentY )) {
                shape.setColor(Color.RED);
            }
            else {
                unSelectedCounter++;
                continue;
            }

            if (selectedShapes.contains(shape)) {
                selectedShapes.remove(shape);
                if (shape.getSubShapes().size() > 0)
                    shape.setColor(Color.BLACK);
                else
                    shape.setColor(Color.BLACK);
                break;
            }

            selectedShapes.add(shape);

            break;
        }

        if (unSelectedCounter == listmodel.size() ) {
            System.out.println("unselect");
            for (Shape s : selectedShapes){
                if (s.getSubShapes().size() > 0) {
                    s.setColor(Color.BLACK);
                    continue;
                }
                s.setColor(Color.BLACK);
            }
            selectedShapes.clear();
        }
        repaint();
    }

    public void createGroup() {
        Group group = new Group();
        group.addShapes(selectedShapes);

        for (Shape s : selectedShapes){
            if(listmodel.contains(s)) {
                listmodel.removeElement(s);
                mainGroup.removeShape(s);
            }
        }

        listmodel.addElement(group);
        mainGroup.addShape(group);
        selectedShapes.clear();
        group.setColor(Color.BLACK);
        repaint();
    }
}
