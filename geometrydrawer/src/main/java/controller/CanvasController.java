package controller;

import model.Mouse;
import model.commands.*;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Shape;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCmdMng;

import java.awt.*;
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
    public DefaultListModel<Figure> listmodel = new DefaultListModel<Figure>();
    public List<Figure> selectedShapes = new ArrayList<>();
    public List<Figure> flatEditableShapes = new ArrayList<>();
    public List<List<Point>> flatPointsEditableShapes = new ArrayList<List<Point>>();

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
            currShape.setStartPoint(e.getPoint());
        }
    }

    public void flatMap(List<Figure> figures){
        for (Figure f : figures){
            if (f instanceof Group){
                Group group = (Group) f;
                flatMap(group.getSubShapes());
            }
            else {
                Shape shape = (Shape) f;
                flatEditableShapes.add(shape);
                List<Point> temp_point = new ArrayList<>();
                temp_point.add(shape.getStartPoint());
                temp_point.add(shape.getEndPoint());
                flatPointsEditableShapes.add(temp_point);
            }
        }
    }

    public List<Figure> getSelectedShapes(){
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

    public void removeElementFromList(Figure figure){
        listmodel.removeElement(figure);
        mainGroup.removeFigure(figure);
        repaint();
    }

    public void removeElementsFromList(List<Figure> figures){
        for (Figure figure : figures) {
            removeElementFromList(figure);
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

            if (figure instanceof Group && ((Group) figure).contain(currentPoint, figure)) {
                if (selectedShapes.contains(figure))
                    commandManager.Execute(new UnselectCommand(figure));
                else
                    commandManager.Execute(new SelectCommand(figure));
                break;
            }
            else if (figure instanceof Shape && figure.contain(currentPoint)){
                if (selectedShapes.contains(figure))
                    commandManager.Execute(new UnselectCommand(figure));
                else
                    commandManager.Execute(new SelectCommand(figure));
                break;
            }
            else {
                unSelectedCounter++;
            }
        }
        if (unSelectedCounter == listmodel.size())
            clearSelect();
        repaint();
    }

    public void createGroup() {
        commandManager.Execute(new MakeGroupCommand(selectedShapes));
        selectedShapes.clear();
        repaint();
    }
}
