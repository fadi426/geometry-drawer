package model.shapes;

import model.visitors.Visitor;

import java.awt.*;

 public interface Figure {

     void draw(Graphics g);

     void fill(Graphics g);

     void setColor(Color currentColor);

     Color getColor();

     boolean contain(Point point);

     void addOrnament(Ornament ornament);

     void deleteOrnament(Ornament ornament);

     void accept(Visitor v);
}