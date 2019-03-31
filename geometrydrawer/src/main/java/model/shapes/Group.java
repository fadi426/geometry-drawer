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
    public Group(Point start,Point end){
        setShapeStart(start);
        setShapeEnd(end);
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
    public List getSubShapes(){
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
}
