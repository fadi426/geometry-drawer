package model.strategies;

import model.shapes.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.abs;

public class CircleStrategy implements ShapeStrategy {

    private Ellipse2D ellipse;

    @Override
    public void draw(Shape shape, Graphics g) {

        int width = abs(shape.getEndPoint().x - shape.getStartPoint().x);
        int height = abs(shape.getEndPoint().y - shape.getStartPoint().y);
        Graphics2D g2D = (Graphics2D) g;
        ellipse = new Ellipse2D.Double(shape.getStartPoint().x, shape.getStartPoint().y, width, height);
        g.setColor(shape.getColor());
        g2D.draw(ellipse);
    }

    @Override
    public String toString(Shape shape) {
        return "Circle: (" + shape.getStartPoint().x + ", " + shape.getStartPoint().y + "): "
                + "(" + shape.getEndPoint().x + ", " + shape.getEndPoint().y + ")  "
                + " Radius: (" + (shape.getEndPoint().x - shape.getStartPoint().x / 2) + ")";
    }

    @Override
    public Boolean contain(Point point) {
        return ellipse.contains(point.x, point.y);
    }
}
