package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;

public class Ornament implements Figure {

    private Point startPoint;
    private String text;
    private String position;
    private Figure parent;
    private Color currentColor = Color.BLACK;

    public Ornament(String text, String position, Figure parent) {
        this.position = position;
        this.text = text;
        this.parent = parent;
        updateOrnament();
    }

    @Override
    public void draw(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            updateOrnament();
            g.setColor(parent.getColor());
            g2.drawString(text, getStartPoint().x, getStartPoint().y);
        }
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
        return parent.getColor() != Color.BLACK;
    }

    @Override
    public void accept(Visitor v) {
    }

    /**
     * Updates the current position relatively to its parent Figure min/max starting/ending position
     */
    public void updateOrnament() {
        Point start = null, end = null;
        int width, height;
        int offset = 10;

        if (parent instanceof Group) {
            Group group = (Group) parent;
            List<Point> points = group.CalculateBoundary();
            start = points.get(0);
            end = points.get(1);
        } else if (parent instanceof Shape) {
            Shape shape = (Shape) parent;
            start = shape.getStartPoint();
            end = shape.getEndPoint();
        }


        width = abs(end.x - start.x);
        height = abs(end.y - start.y);
        switch (position) {
            case "top":
                setStartPoint(new Point(start.x + width / 2, start.y + offset));
                break;
            case "bottom":
                setStartPoint(new Point(start.x + width / 2, end.y + offset));
                break;
            case "left":
                setStartPoint(new Point(start.x, start.y + height / 2 + offset));
                break;
            case "right":
                setStartPoint(new Point(end.x, start.y + height / 2 + offset));
                break;
        }
    }

    /**
     * Gives back the current starting point of the current Ornament
     * @return the starting position of the ornament
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Changes the current starting position of the current ornament
     * @param startPoint is the target starting position that has to be changed to
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Gives back the parent (Group or Shape) of the current ornament
     * @return the parent of the ornament
     */
    public Figure getParent() {
        return parent;
    }

    /**
     * Changes the current parent (Group or Shape) of the current ornament.
     * @param figure is the target parent that has to be changed to
     */
    public void setParent(Figure figure) {
        this.parent = figure;
    }
}
