package model;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Rectangle extends Shape {

	ArrayList<Point> points;

	public Rectangle(){
		points = new ArrayList<Point>(4);
	}
	@Override
	public void draw(Graphics g) {
		points.clear();
		
		points.add(getShapeStart());
		points.add(new Point(getShapeStart().x,getShapeEnd().y));
		points.add(getShapeEnd());
		points.add(new Point(getShapeEnd().x,getShapeStart().y));
		
		Line line = new Line(points.get(0),points.get(1));
		line.setCurrentColor(getCurrentColor());
		line.draw(g);
		Line line2 = new Line(points.get(3),points.get(2));
		line2.setCurrentColor(getCurrentColor());
		line2.draw(g);
		Line line3 = new Line(points.get(0),points.get(3));
		line3.setCurrentColor(getCurrentColor());
		line3.draw(g);
		Line line4 = new Line(points.get(1),points.get(2));
		line4.setCurrentColor(getCurrentColor());
		line4.draw(g);
	}

	@Override
	public void fill(Graphics g) {

	}

	@Override
	public String toString(){
		return "Rectangle: (" + points.get(0).x + "," + points.get(0).y + ")-"
				+ "(" + points.get(3).x + "," + points.get(3).y + ")";
	}

}
