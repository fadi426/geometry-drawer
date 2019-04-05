package model.strategies;

import model.shapes.Ornament;
import model.shapes.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static java.lang.Math.abs;

public class CircleStrategy implements ShapeStrategy {

    private Ellipse2D ellipse;

    @Override
    public void draw(Shape shape, Graphics g) {

        int width = abs(shape.getShapeEnd().x - shape.getShapeStart().x);
        int height = abs(shape.getShapeEnd().y - shape.getShapeStart().y);
        Graphics2D g2D = (Graphics2D) g;
        ellipse = new Ellipse2D.Double(shape.getShapeStart().x,shape.getShapeStart().y, width,height);
        g.setColor(shape.getColor());
        g2D.draw(ellipse);
    }

    @Override
    public String toString(Shape shape) {
        return "Circle: ("+ shape.getShapeStart()+","+ shape.getShapeEnd()+")"
                + " Radius: (" + (shape.getShapeEnd().x - shape.getShapeStart().x / 2) + ")";
    }

    @Override
    public Boolean contain(Point point) {
        return ellipse.contains(point.x, point.y);
    }
}
