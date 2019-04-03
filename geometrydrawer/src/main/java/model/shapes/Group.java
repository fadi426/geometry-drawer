package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Group extends Shape {

    private List<Shape> subShapes = new ArrayList<>();

    public Group(){
        setShapeStart(new Point(0,0));
        setShapeEnd(new Point(0,0));
    }

    public void addShape(Shape shape){
        subShapes.add(shape);
    };

    public void addShapes(List<Shape> shapes){
        subShapes.addAll(shapes);
    }

    public void clear(){
        subShapes.clear();
    }

    @Override
    public void setColor(Color color){
        for (Shape s : subShapes) {
            if (s.getSubShapes().size() > 0) {
                s.setColor(color);
            }
            else {
                s.setColor(color);
            }
        }
    };

    public void removeShape(Shape shape){
        subShapes.remove(shape);
    };

    @Override
    public List<Shape> getSubShapes(){
        return subShapes;
    };

    public void shapesToString(){
        for (Shape s : subShapes){
            System.out.println(s);
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Shape s : subShapes) {
            s.draw(g);
            if (getOrnaments().size() > 0) {
                for (Ornament ornament : getOrnaments()) {
                    ornament.draw(g);
                }
            }
        }
    }

    @Override
    public boolean contain(int x, int y, List<Shape> subShapes) {
        for (Shape s: subShapes) {
            if (s.getSubShapes().size() > 0){
                 if (contain(x,y,s.getSubShapes()))
                     return true;
            }
            else {
                if (s.contain(x,y))
                    return true;
            }
        }
        return false;
    }

    public String toString(){
        return "Group: ("+ getShapeStart().x+","+getShapeStart().y+")";
    }

    @Override
    public void fill(Graphics g) {

    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public void CalculateBoundary(){
        Point start = new Point();
        Point end = new Point();

        start.x = getSubShapes().get(0).getShapeStart().x;
        start.y = getSubShapes().get(0).getShapeStart().y;

        for (Shape shape : getSubShapes()) {
            if (shape.getShapeEnd().x > end.x)
                end.x = shape.getShapeEnd().x;

            if (shape.getShapeEnd().y > end.y)
                end.y = shape.getShapeEnd().y;

            if (shape.getShapeStart().x < start.x)
                start.x = shape.getShapeStart().x;

            if (shape.getShapeStart().y < start.y)
                start.y = shape.getShapeStart().y;
        }

        setShapeStart(start);
        setShapeEnd(end);
    }

}
