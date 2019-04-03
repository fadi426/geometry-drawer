package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Rectangle extends Shape {

	ArrayList<Point> points;
	Rectangle2D rectangle;
	public Rectangle(){
		points = new ArrayList<Point>(4);
	}
	@Override
	public void draw(Graphics g) {
		int width = abs(getShapeEnd().x - getShapeStart().x);
		int height = abs(getShapeEnd().y - getShapeStart().y);
		Graphics2D g2D = (Graphics2D) g;
		rectangle = new Rectangle2D.Double(getShapeStart().x,getShapeStart().y, width,height);
		g.setColor(getColor());
		g2D.draw(rectangle);
	}

	@Override
	public void fill(Graphics g) {

	}

	@Override
	public boolean contain(int x, int y) {
		return rectangle.contains(x,y);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
