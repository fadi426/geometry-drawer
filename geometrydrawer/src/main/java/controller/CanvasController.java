package controller;


import model.Mouse;
import model.commands.*;
import model.decorators.OrnamentDecorator;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
import model.shapes.Shape;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCmdMng;
import model.strategies.ShapeContext;
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
    public DefaultListModel<Figure> listmodel = new DefaultListModel<Figure>();
    public static List<Figure> selectedShapes = new ArrayList<>();
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

    public List<Figure> toList(){
        List<Figure> figures = new ArrayList<>();
        for (int i = 0; i < listmodel.size(); i++)
        {
            figures.add(listmodel.get(i));
        }
        return figures;
    }

    public Group getMainGroup(){
        return mainGroup;
    }
    public void setMainGroup(Group group){this.mainGroup = group;}

    public void removeLastElement(){
        if (listmodel.size() > 0) {
            int last = listmodel.size() - 1;
            mainGroup.removeFigure(listmodel.get(last));
            listmodel.removeElementAt(last);
            repaint();
        }
    }

    public void clearSelect(){
        for (Figure figure : selectedShapes) {
            figure.setColor(Color.BLACK);
        }
        selectedShapes.clear();
    }

    public void addElementToList(Figure figure){
        listmodel.addElement(figure);
        mainGroup.addFigure(figure);
        repaint();
    }

    public void addElementsToList(List<Figure> figures){
        for (Figure figure : figures) {
            addElementToList(figure);
        }
    }

    public void insertFromFile(List<Figure> figures){
        for (Figure figure : figures){
            listmodel.addElement(figure);
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
        }

        if(currShape != null){
            currShape.draw(g);
        }
    }

    public void selectShape(MouseEvent e){
        currentX = e.getX();
        currentY = e.getY();
        Point currentPoint = new Point(currentX, currentY);
        int unSelectedCounter = 0;

        for (int i = listmodel.size() - 1; i >= 0; i = i - 1) {
            Figure figure = listmodel.get(i);

            if (figure instanceof Group && ((Group) figure).contain(currentPoint, figure))
                    commandManager.Execute(new SelectCommand(figure));
            else if (figure instanceof Shape && figure.contain(currentPoint) )
                    commandManager.Execute(new SelectCommand(figure));
            else {
                unSelectedCounter++;
                continue;
            }

            if (selectedShapes.contains(figure)) {
                selectedShapes.remove(figure);
                commandManager.Execute(new UnselectCommand(figure));
                break;
            }

            selectedShapes.add(figure);
            break;
        }

        if (unSelectedCounter == listmodel.size() ) {
            for (Figure figure : selectedShapes){
                commandManager.Execute(new UnselectCommand(figure));
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

    public void setPreviousPosition(Figure figure){
        if (figure instanceof Group) {
            Group group = (Group) figure;
            setPreviousGroupPositions(group.getSubShapes());
        }
        else {
            figure.setPreviousShapeStart(figure.getShapeStart());
            figure.setPreviousShapeEnd(figure.getShapeEnd());
        }

    }

    public void setPreviousGroupPositions(List<Figure> subShapes){
        for (Figure f : subShapes){
            if (f instanceof Group){
                Group group = (Group) f;
                setPreviousGroupPositions(group.getSubShapes());
            }
            else {
                f.setPreviousShapeStart(f.getShapeStart());
                f.setPreviousShapeEnd(f.getShapeEnd());
            }
        }
        return;
    }
}
