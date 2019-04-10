package model.shapes;

import model.visitors.Visitor;

import java.awt.*;

public interface Figure {

    void draw(Graphics g);

    Color getColor();

    void setColor(Color currentColor);

    boolean contain(Point point);

    void accept(Visitor v);
}
