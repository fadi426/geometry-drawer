package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.List;

 public interface Figure {

     void draw(Graphics g);

     void fill(Graphics g);

     Point getShapeStart();

     void setShapeStart(Point shapeStart);

     Point getShapeEnd();

     void setShapeEnd(Point shapeEnd);

     Point getPreviousShapeEnd();

     Point getPreviousShapeStart();

     void setPreviousShapeEnd(Point previousShapeEnd);

     void setPreviousShapeStart(Point previousShapeStart);

     void setColor(Color currentColor);

     Color getColor();

     boolean contain(Point point);

     void addOrnament(Ornament ornament);

     void accept(Visitor v);
}
