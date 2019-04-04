package controller;


import model.Mouse;
import model.commands.*;
import model.decorators.OrnamentDecorator;
import model.shapes.Group;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCmdMng;
import model.visitors.MoveVisitor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static java.lang.Math.abs;

public class CanvasController extends JPanel {

    public Shape shapeType;
    public Shape currShape;
    private Color drawingColor = Color.BLACK;
    private CommandManager commandManager = SingletonCmdMng.getInstance();

    public Group mainGroup = new Group();
    public DefaultListModel<Shape> listmodel = new DefaultListModel<Shape>();
    public static List<Shape> selectedShapes = new ArrayList<>();
    public List<Point> editableShapes = new ArrayList<>();

    public int currentX;
    public int currentY;
    public int endX;
    public int endY;

    Mouse mouse = SingleMouse.getInstance();

    public CanvasController(Color bg){
        this.setBackground(bg);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    public void draw(MouseEvent e) {
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

    public List<Shape> getSelectedShapes(){
        return selectedShapes;
    }


    public void setCurrShape(Shape toDraw){
        shapeType = toDraw;
    }

    public List<Shape> toList(){
        List<Shape> shapes = new ArrayList<>();
        for (int i = 0; i < listmodel.size(); i++)
        {
            shapes.add(listmodel.get(i));
        }
        return shapes;
    }

    public Group getMainGroup(){
        return mainGroup;
    }
    public void setMainGroup(Shape group){this.mainGroup = (Group) group;}

    public void removeLastElement(){
        if (listmodel.size() > 0) {
            int last = listmodel.size() - 1;
            mainGroup.removeShape(listmodel.get(last));
            listmodel.removeElementAt(last);
            repaint();
        }
    }

    public void clearSelect(){
        for (Shape shape : selectedShapes) {
            shape.setColor(Color.BLACK);
        }
        selectedShapes.clear();
    }

    public void addElementToList(Shape shape){
        listmodel.addElement(shape);
        mainGroup.addShape(shape);
        repaint();
    }

    public void addElementsToList(List<Shape> shapes){
        for (Shape s : shapes) {
            addElementToList(s);
        }
    }

    public void insertFromFile(List<Shape> shapes){
        for (Shape s : shapes){
            listmodel.addElement(s);
        }
        repaint();
    }

    public void clear(){
        listmodel.clear();
        mainGroup.clear();
        repaint();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
        for(int i = 0;i < listmodel.getSize();i++){
            listmodel.elementAt(i).draw(g2);
            if(listmodel.elementAt(i).isFilled()){
                listmodel.elementAt(i).fill(g2);
            }
        }

        if(currShape != null){
            currShape.draw(g);
        }
    }

    public void selectShape(MouseEvent e){
        currentX = e.getX();
        currentY = e.getY();
        int unSelectedCounter = 0;

        for (int i = listmodel.size() - 1; i >= 0; i = i - 1) {
            Shape shape = listmodel.get(i);

            if (shape.getSubShapes().size()> 0 && shape.contain(currentX, currentY, shape.getSubShapes())){
                commandManager.Execute(new SelectCommand(shape));
            }
            else if (shape.contain(currentX, currentY )) {
                commandManager.Execute(new SelectCommand(shape));
            }
            else {
                unSelectedCounter++;
                continue;
            }

            if (selectedShapes.contains(shape)) {
                selectedShapes.remove(shape);
                commandManager.Execute(new UnselectCommand(shape));
                break;
            }

            selectedShapes.add(shape);

            break;
        }

        if (unSelectedCounter == listmodel.size() ) {
            for (Shape s : selectedShapes){
                commandManager.Execute(new UnselectCommand(s));
            }
            selectedShapes.clear();
        }
        repaint();
    }

    public void createGroup() {
        commandManager.Execute(new MakeGroupCommand(selectedShapes));
        selectedShapes.clear();
        repaint();
    }

    public void setPreviousPosition(Shape shape){
        if (shape.getSubShapes().size() > 0)
            setPreviousGroupPositions(shape.getSubShapes());
        else {
            shape.setPreviousShapeStart(shape.getShapeStart());
            shape.setPreviousShapeEnd(shape.getShapeEnd());
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.drawString("This is gona be awesome",70,20);
        }
    }

    public void setPreviousGroupPositions(List<Shape> subShapes){
        for (Shape s : subShapes){
            if (s.getSubShapes().size() > 0){
                setPreviousGroupPositions(s.getSubShapes());
            }
            else {
                s.setPreviousShapeStart(s.getShapeStart());
                s.setPreviousShapeEnd(s.getShapeEnd());
            }
        }
        return;
    }
}
