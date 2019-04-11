package model.shapes;

import model.strategies.CircleStrategy;
import model.visitors.Visitor;

import java.awt.*;

public class Circle extends Shape {

    public Circle() {
        setStartPoint(new Point(0, 0));
        setEndPoint(new Point(0, 0));
        setStrategy(new CircleStrategy());
    }

    @Override
    public void draw(Graphics g) {
        getStrategy().executeDrawStrategy(this, g);
    }

    @Override
    public boolean contain(Point point) {
        return getStrategy().executeContain(point);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String toString(){
        return getStrategy().executeToString(this);
    }
}
