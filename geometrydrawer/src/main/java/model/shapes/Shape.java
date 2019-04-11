package model.shapes;

import model.strategies.ShapeContext;
import model.strategies.ShapeStrategy;
import model.visitors.Visitable;
import model.visitors.Visitor;

import java.awt.*;

public abstract class Shape implements Visitable, Figure {

    private Point startPoint, endPoint;
    private Color currentColor = Color.BLACK;
    private transient ShapeContext shapeContext;

    @Override
    public abstract void draw(Graphics g);

    /**
     * Gives back the current starting point of the shape
     * @return the starting point of the shape
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Changes the starting point to the startPoint param
     * @param startPoint is the position of the mouse when pressed in the draw operation
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Gives back the current ending point of the shape
     * @return the ending point of the shape
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * Changes the ending point to the endPoint param
     * @param endPoint is the position of the mouse when dragged in the draw operation
     */
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Color getColor() {
        return currentColor;
    }

    @Override
    public void setColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    @Override
    public boolean contain(Point point) {
        return false;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * Gives back the ShapeContext of the current shape
     * @return the shapeContext
     */
    public ShapeContext getStrategy() {
        return this.shapeContext;
    }

    /**
     * Changes the current shapeContext to the target shapeContext
     * @param strategy is a strategy for a specific child of shape
     */
    public void setStrategy(ShapeStrategy strategy) {
        this.shapeContext = new ShapeContext(strategy);
    }
}
