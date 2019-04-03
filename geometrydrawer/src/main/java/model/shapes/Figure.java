package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.util.List;

 interface Figure {

     void drawPoint(Graphics g, int x, int y);

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

     boolean contain(int x, int y);

     boolean contain(int x, int y, java.util.List<Shape> shapes);

     List<Shape> getSubShapes();

     boolean isFilled();

     void setFilled(boolean filled);

     void accept(Visitor v);
}
