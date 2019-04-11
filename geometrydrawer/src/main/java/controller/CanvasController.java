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

    private Shape shapeType;
    private Shape currentShape;
    private Color drawingColor = Color.BLACK;
    private CommandManager commandManager = SingletonCmdMng.getInstance();

    private DefaultListModel<Figure> listmodel = new DefaultListModel<Figure>();
    private Group mainGroup = new Group();
    private List<Figure> selectedShapes = new ArrayList<>();
    private List<Figure> flatEditableShapes = new ArrayList<>();
    private List<List<Point>> flatPointsEditableShapes = new ArrayList<>();

    private Mouse mouse = SingleMouse.getInstance();

    public CanvasController(Color bg) {
        this.setBackground(bg);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    /**
     * Method to draw the content of the new drawn shape (currentShape)
     * @param e is mouseEvent
     */
    public void draw(MouseEvent e) {
        try {
            Class c = Class.forName(shapeType.getClass().getName());
            currentShape = (Shape) c.newInstance();
            commandManager.Execute(new MakeShapeCommand(currentShape));
            currentShape.setColor(drawingColor);
        } catch (NullPointerException e3) {
            JOptionPane.showMessageDialog(getParent(), "No Shape Selected!");
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        if (currentShape != null) {
            currentShape.setStartPoint(e.getPoint());
        }
    }

    /**
     * Creates a flat map of an list of figures recursively, it will call itself when it finds a group
     * @param figures a list of figures to flatmap
     */
    public void flatMap(List<Figure> figures) {
        for (Figure f : figures) {
            if (f instanceof Group) {
                Group group = (Group) f;
                flatMap(group.getSubShapes());
            } else if (f instanceof Shape) {
                Shape shape = (Shape) f;
                flatEditableShapes.add(shape);
                List<Point> temp_point = new ArrayList<>();
                temp_point.add(shape.getStartPoint());
                temp_point.add(shape.getEndPoint());
                flatPointsEditableShapes.add(temp_point);
            }
        }
    }

    /**
     * Returns the selected figures from the canvas
     * @return A list of figures
     */
    public List<Figure> getSelectedShapes() {
        return selectedShapes;
    }

    /**
     * Adds a figure to the selected list
     * @param figure is a to be added figure
     */
    public void addToSelected(Figure figure){
        selectedShapes.add(figure);
    }

    /**
     * Removes a figure from the selected list
     * @param figure is a to be removed figure
     */
    public void removeFromSelected(Figure figure){
        selectedShapes.remove(figure);
    }

    /**
     * Sets the current shape type that you want to draw on the canvas
     * @param toDraw Shapetype to set
     */
    public void setCurrentShapeType(Shape toDraw) {
        shapeType = toDraw;
    }

    /**
     * Makes a List of figures from the figures in the canvas
     * @return A list of figures
     */
    public List<Figure> toList() {
        List<Figure> figures = new ArrayList<>();
        for (int i = 0; i < listmodel.size(); i++) {
            figures.add(listmodel.get(i));
        }
        return figures;
    }

    /**
     * Gives the shape object of the chosen shape from the buttons in the MainFrameController class
     * @return currentShape
     */
    public Shape getCurrentShape(){
        return currentShape;
    }

    /**
     * Sets the current shape type that you want to draw on the canvas
     * @param shape Shape to set
     */
    public void setCurrentShape(Shape shape){
        this.currentShape = shape;
    }

    /**
     * Returns the main group of the canvas
     * @return returns a Group object
     */
    public Group getMainGroup() {
        return mainGroup;
    }

    /**
     * Set the main group of the canvas
     * @param group the group to set
     */
    public void setMainGroup(Group group) {
        this.mainGroup = group;
    }

    /**
     * Removes the last element that was added from the canvas
     */
    public void removeLastElement() {
        if (listmodel.size() > 0) {
            int last = listmodel.size() - 1;
            mainGroup.removeFigure(listmodel.get(last));
            listmodel.removeElementAt(last);
            repaint();
        }
    }

    /**
     * Clear the selection in the canvas
     */
    public void clearSelect() {
        for (Figure figure : selectedShapes) {
            figure.setColor(Color.BLACK);
        }
        selectedShapes.clear();
    }

    /**
     * Add a figure to the canvas
     * @param figure figure to add
     */
    public void addElementToList(Figure figure) {
        listmodel.addElement(figure);
        mainGroup.addFigure(figure);
        repaint();
    }

    /**
     * Sets the current listmodel and maingroup to the figures list
     * @param figures is a target list of figures
     */
    public void setCanvasLists(List<Figure> figures) {
        listmodel.clear();
        mainGroup.clear();

        addElementsToList(figures);
        repaint();
    }

    /**
     * Gives the current flatEditableShapes list
     * @return flatEditableShapes
     */
    public List<Figure> getFlatEditableShapes() {
        return flatEditableShapes;
    }

    /**
     * ives the current flatPointsEditableShapes list
     * @return flatPointsEditableShapes
     */
    public List<List<Point>> getFlatPointsEditableShapes() {
        return flatPointsEditableShapes;
    }

    /**
     * Add multiple figures to the canvas
     * @param figures figures to add
     */
    public void addElementsToList(List<Figure> figures) {
        for (Figure figure : figures) {
            addElementToList(figure);
        }
    }

    /**
     * Removes a Figure from the canvas
     * @param figure figure to add
     */
    public void removeElementFromList(Figure figure) {
        listmodel.removeElement(figure);
        mainGroup.removeFigure(figure);
        repaint();
    }

    /**
     * Insert the figures that are loaded from a json file
     * @param figures figures to add
     */
    public void insertFromFile(List<Figure> figures) {
        listmodel.clear();
        for (Figure figure : figures) {
            listmodel.addElement(figure);
        }
        repaint();
    }

    /**
     * Clear the whole canvas
     */
    public void clear() {
        listmodel.clear();
        mainGroup.clear();
        repaint();
    }

    /**
     * Calls the Jcomponents object to draw the content of the listmodel
     * @param g is the Graphics object of Jcomponents
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
        for (int i = 0; i < listmodel.getSize(); i++) {
            listmodel.elementAt(i).draw(g2);
        }
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    /**
     * Select a shape, accepts a MouseEvent
     * @param e mouse position
     */
    public void selectShape(MouseEvent e) {
        mouse.setCurrentX(e.getX());
        mouse.setCurrentY(e.getY());
        Point currentPoint = new Point(mouse.getCurrentX(), mouse.getCurrentY());
        int unSelectedCounter = 0;

        for (int i = listmodel.size() - 1; i >= 0; i = i - 1) {
            Figure figure = listmodel.get(i);

            if (figure instanceof Group && ((Group) figure).contain(currentPoint, figure)) {
                if (selectedShapes.contains(figure))
                    commandManager.Execute(new UnselectCommand(figure));
                else
                    commandManager.Execute(new SelectCommand(figure));
                break;
            } else if (figure instanceof Shape && figure.contain(currentPoint)) {
                if (selectedShapes.contains(figure))
                    commandManager.Execute(new UnselectCommand(figure));
                else
                    commandManager.Execute(new SelectCommand(figure));
                break;
            } else {
                unSelectedCounter++;
            }
        }
        if (unSelectedCounter == listmodel.size())
            clearSelect();
        repaint();
    }

    /**
     * Create a group in the canvas from the selected shapes
     */
    public void createGroup() {
        if (selectedShapes.size() == 0)
            return;
        commandManager.Execute(new MakeGroupCommand(selectedShapes));
        selectedShapes.clear();
        repaint();
    }
}
