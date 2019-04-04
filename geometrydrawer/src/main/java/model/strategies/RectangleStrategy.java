package model.strategies;

import model.shapes.Ornament;
import model.shapes.Shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static java.lang.Math.abs;

public class RectangleStrategy implements ShapeStrategy {

    Rectangle2D rectangle;

    @Override
    public void draw(Shape shape, Graphics g) {
        int width = abs(shape.getShapeEnd().x - shape.getShapeStart().x);
        int height = abs(shape.getShapeEnd().y - shape.getShapeStart().y);
        Graphics2D g2D = (Graphics2D) g;
        rectangle = new Rectangle2D.Double(shape.getShapeStart().x,shape.getShapeStart().y, width,height);
        g.setColor(shape.getColor());
        g2D.draw(rectangle);


        if (shape.getOrnaments().size() > 0) {
            for (Ornament ornament : shape.getOrnaments()) {
                ornament.draw(g);
            }
        }
    }

    @Override
    public String toString(Shape shape) {
        return "Rectangle: (" + shape.getShapeStart() + ")-"
                + "(" + shape.getShapeEnd() + ")";
    }

    @Override
    public Boolean contain(Point point) {
        return rectangle.contains(point.x, point.y);
    }
}
