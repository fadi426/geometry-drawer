package model.strategies;

import model.shapes.Shape;

import java.awt.*;

public interface ShapeStrategy {
    public void draw(Shape shape, Graphics g);
    public String toString(Shape shape);
    public Boolean contain(Point point);
//    public void setColor(Shape shape, Color color);
}
