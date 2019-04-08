package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Group implements Figure {

    private List<Figure> subShapes = new ArrayList<>();

    public Group(){}

    public void addFigure(Figure figure){
        subShapes.add(figure);
    };

    public void addFigures(List<Figure> figures){
        subShapes.addAll(figures);
    }

    public void clear(){
        subShapes.clear();
    }

    @Override
    public void setColor(Color color){
        for (Figure f : subShapes) {
            if (f instanceof Group) {
                Group group = (Group) f;
                group.setColor(color);
            }
            else if (f instanceof Shape){
                Shape shape = (Shape) f;
                shape.setColor(color);
            }
        }
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean contain(Point point) {
        return contain(point, this);
    }

    public void removeFigure(Figure figure){
        subShapes.remove(figure);
    };

    public List<Figure> getSubShapes(){
        return subShapes;
    };

    @Override
    public void draw(Graphics g) {
        for (Figure f : subShapes) {
            f.draw(g);
        }
    }

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
    public void fill(Graphics g) {

    }

    public void accept(Visitor v) {
        for (Figure f: subShapes) {
            if (f instanceof Group) {
                f.accept(v);
            }
        else{
            Shape shape = (Shape) f;
            v.visit(shape);
            }
        }
    }


    public List<Point> CalculateBoundary(){
        Point start = new Point(1000,1000);
        Point end = new Point(-1000, -1000);
        List<Point> tempPoints = recursiveCBoundary(this, start, end);
        return tempPoints;
    }

    public List<Point> recursiveCBoundary(Figure figure, Point start, Point end) {
        List<Point> boundary = new ArrayList<>();

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

            return boundary;
        }

        Group group = (Group) figure;
        for (Figure f : group.getSubShapes()){
            boundary =  recursiveCBoundary(f, start, end);
        }

        return boundary;
    }
//    public String toString(Shape shape) {
//        return "Group: (" + shape.getStartPoint() + ")-"
//                + "(" + shape.getEndPoint() + ")";
//    }

}
