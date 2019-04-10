package model.strategies;

import model.shapes.Shape;

import java.awt.*;

public interface ShapeStrategy {
    void draw(Shape shape, Graphics g);

    String toString(Shape shape);

    Boolean contain(Point point);
}
