package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Group implements Figure {

    private List<Figure> subShapes = new ArrayList<>();
    private List<Ornament> ornaments = new ArrayList<>();
    private Color currentColor = Color.BLACK;

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
            else {
                f.setColor(color);
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

    @Override
    public void addOrnament(Ornament ornament) {
        ornaments.add(ornament);
    }

    @Override
    public void deleteOrnament(Ornament ornament) {
        ornaments.remove(ornament);
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
            if (ornaments.size() > 0) {
                for (Ornament ornament : ornaments) {
                    ornament.draw(g);
                }
            }
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
                accept(v);
            }
        else{
            Shape shape = (Shape) f;
            v.visit(shape);
            }
        }
    }


        public List<Point> CalculateBoundary(){
        Shape shape = null;
        Group group = null;
        Point start = new Point();
        Point end = new Point();
        List<Point> tempPoints = new ArrayList<>();

//        Shape shape = (Shape) subShapes.get(0);
//
//        start.x = shape.getShapeStart().x;
//        start.y = shape.getShapeStart().y;

        for (Figure figure : subShapes) {
            if (figure instanceof Shape){
                shape = (Shape) figure;
                tempPoints = hoi(shape, start, end);
                start = tempPoints.get(0);
                end = tempPoints.get(1);
        }
            else {
                group = (Group) figure;
                tempPoints = multipleHois(group, start, end);
                start = tempPoints.get(0);
                end = tempPoints.get(1);
            }

        }

        List<Point> boundary = new ArrayList<>();
        boundary.add(start);
        boundary.add(end);

        return boundary;
    }

    public List<Point> multipleHois(Group group, Point start, Point end) {
        for (Figure figure : group.getSubShapes()) {
            if (figure instanceof Group) {
                Group nextGroup = (Group) figure;
                multipleHois(nextGroup, start, end);
            } else {
                Shape shape = (Shape) figure;
                hoi(shape, start, end);
            }
        }


        List<Point> boundary = new ArrayList<>();
        boundary.add(start);
        boundary.add(end);

        return boundary;
    }

    public List<Point> hoi(Shape shape, Point start, Point end){

        if (shape.getShapeEnd().x > end.x)
            end.x = shape.getShapeEnd().x;

        if (shape.getShapeEnd().y > end.y)
            end.y = shape.getShapeEnd().y;

        if (shape.getShapeStart().x < start.x)
            start.x = shape.getShapeStart().x;

        if (shape.getShapeStart().y < start.y)
            start.y = shape.getShapeStart().y;

        List<Point> boundary = new ArrayList<>();
        boundary.add(start);
        boundary.add(end);

        return boundary;
    }
//    public String toString(Shape shape) {
//        return "Group: (" + shape.getShapeStart() + ")-"
//                + "(" + shape.getShapeEnd() + ")";
//    }

}
