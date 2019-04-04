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

    public Group(){
        setShapeStart(new Point(0,0));
        setShapeEnd(new Point(0,0));
    }

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

    public String toString(){
        return "Group: ("+ getShapeStart().x+","+getShapeStart().y+")";
    }

    @Override
    public void fill(Graphics g) {

    }

    @Override
    public Point getShapeStart() {
        return null;
    }

    @Override
    public void setShapeStart(Point shapeStart) {

    }

    @Override
    public Point getShapeEnd() {
        return null;
    }

    @Override
    public void setShapeEnd(Point shapeEnd) {

    }

    @Override
    public Point getPreviousShapeEnd() {
        return null;
    }

    @Override
    public Point getPreviousShapeStart() {
        return null;
    }

    @Override
    public void setPreviousShapeEnd(Point previousShapeEnd) {

    }

    @Override
    public void setPreviousShapeStart(Point previousShapeStart) {

    }

    public void accept(Visitor v) {
        for (Figure f: subShapes) {
            v.visit(f);
//            for (Ornament ornament : f.getOrnaments()){
//                f.updateOrnament(ornament);
//            }
        }
//        this.CalculateBoundary();
    }

    public void CalculateBoundary(){
        Point start = new Point();
        Point end = new Point();

        start.x = getSubShapes().get(0).getShapeStart().x;
        start.y = getSubShapes().get(0).getShapeStart().y;

        for (Figure figure : getSubShapes()) {
            if (figure.getShapeEnd().x > end.x)
                end.x = figure.getShapeEnd().x;

            if (figure.getShapeEnd().y > end.y)
                end.y = figure.getShapeEnd().y;

            if (figure.getShapeStart().x < start.x)
                start.x = figure.getShapeStart().x;

            if (figure.getShapeStart().y < start.y)
                start.y = figure.getShapeStart().y;
        }

        setShapeStart(start);
        setShapeEnd(end);
    }

    public String toString(Shape shape) {
        return "Group: (" + shape.getShapeStart() + ")-"
                + "(" + shape.getShapeEnd() + ")";
    }

}
