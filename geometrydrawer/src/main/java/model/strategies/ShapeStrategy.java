package model.strategies;

import model.shapes.Shape;

import java.awt.*;

public interface ShapeStrategy {

    /**
     * Draw the shape
     * @param shape The shape to draw
     * @param g Graphics
     */
    void draw(Shape shape, Graphics g);

    /**
     * Represents the shape as a string
     * @param shape The shape that needs to be stringified
     * @return Returns a string of the object with its data
     */
    String toString(Shape shape);

    /**
     * Checks if a Point with x and y is in this shape
     * @param point The point to check
     * @return Returns True or False whether the point is in the shape or not.
     */
    Boolean contain(Point point);
}
