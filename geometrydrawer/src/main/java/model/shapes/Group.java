package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Group implements Figure {

    private List<Figure> subShapes = new ArrayList<>();

    public Group() {    }

    /**
     * Adds 1 figure to the subshapes of the current group
     * @param figure can be any kind of shape/group/ornament to be added to the group
     */
    public void addFigure(Figure figure) {
        subShapes.add(figure);
    }

    /**
     * * Adds a list of figures to the subshapes of the current group
     * @param figures is a list of figures to be added to the group
     */
    public void addFigures(List<Figure> figures) {
        subShapes.addAll(figures);
    }

    /**
     * Clears the subshapes list of the group
     */
    public void clear() {
        subShapes.clear();
    }

    @Override
    public Color getColor() {
        return subShapes.get(0).getColor();
    }

    @Override
    public void setColor(Color color) {
        for (Figure f : subShapes) {
            if (f instanceof Group) {
                Group group = (Group) f;
                group.setColor(color);
            } else if (f instanceof Shape) {
                Shape shape = (Shape) f;
                shape.setColor(color);
            }
        }
    }

    @Override
    public boolean contain(Point point) {
        return contain(point, this);
    }

    /**
     * Deletes a figure from the subshape list of the current group
     * @param figure is the to be deleted figure
     */
    public void removeFigure(Figure figure) {
        subShapes.remove(figure);
    }

    /**
     * Retrieves the list of subshapes from the current Group
     * @return the subshapes of the current group
     */
    public List<Figure> getSubShapes() {
        return subShapes;
    }

    @Override
    public void draw(Graphics g) {
        for (Figure f : subShapes) {
            f.draw(g);
        }
    }

    /**
     * Loop recursively trough the subshapes of the group to determine if the current
     * mouse position is inside one of the subshapes of the group
     * @param point is the current mouse position
     * @param figure is either a single shape or a group, if figure is a group recall the method with the subgroup of that figure
     * @return true or false depending if the current mouse position is inside a subshape of the current group.
     */
    public boolean contain(Point point, Figure figure) {
        if (figure instanceof Group) {
            Group group = (Group) figure;
            for (Figure f : group.getSubShapes()) {
                if (f instanceof Group) {
                    if (contain(point, f))
                        return true;
                } else {
                    if (f instanceof Shape) {
                        Shape shape = (Shape) f;
                        if (shape.contain(point))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void accept(Visitor v) {
        for (Figure f : subShapes) {
            if (f instanceof Group) {
                f.accept(v);
            } else if (f instanceof Ornament)
                continue;
            else {
                Shape shape = (Shape) f;
                v.visit(shape);
            }
        }
    }

    /**
     * Sets the min and max values outside of the canvas and calls the recursive method recursiveCalculateBoundary
     * @return 1 list of the absolute min and max starting/ending positions of the subshapes
     */
    public List<Point> CalculateBoundary() {
        Point start = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point end = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        List<Point> tempPoints = recursiveCalculateBoundary(this, start, end);
        return tempPoints;
    }

    /**
     * Loop recursively trough all the subshapes that are an instance of group and read/compare the positions of the single shapes
     * to determine the max/min x and y of the starting and end position of the subshapes.
     * @return a changing list of the min/max startpoint and endpoint
     */
    public List<Point> recursiveCalculateBoundary(Figure figure, Point start, Point end) {
        List<Point> boundary = new ArrayList<>();

        if (figure instanceof Group) {
            Group group = (Group) figure;
            for (Figure f : group.getSubShapes()) {
                if (f instanceof Ornament)
                    continue;

                boundary = recursiveCalculateBoundary(f, start, end);
            }
        }

        if (figure instanceof Shape) {
            Shape shape = (Shape) figure;

            if (shape.getEndPoint().x > end.x)
                end.x = shape.getEndPoint().x;

            if (shape.getEndPoint().y > end.y)
                end.y = shape.getEndPoint().y;

            if (shape.getStartPoint().x < start.x)
                start.x = shape.getStartPoint().x;

            if (shape.getStartPoint().y < start.y)
                start.y = shape.getStartPoint().y;

            boundary.add(start);
            boundary.add(end);
        }

        return boundary;
    }

    @Override
    public String toString() {
        List<Point> boundary = CalculateBoundary();
        return "Group: (" + boundary.get(0).x + ", " + boundary.get(0).y + ") : "
                + "(" + boundary.get(1).x + ", " + boundary.get(1).y + ")";
    }

}
