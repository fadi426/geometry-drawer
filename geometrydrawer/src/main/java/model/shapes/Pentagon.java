package model.shapes;
import model.visitors.Visitor;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pentagon extends Shape {

	private java.awt.Polygon p = new java.awt.Polygon();
	private int sides = 5;
	private int radius;
	private ArrayList<Point> points;
	private Point point;

	public Pentagon() {
		points = new ArrayList<Point>(4);
		setShapeStart(new Point(0,0));
		setShapeEnd(new Point(0,0));
	}

	public Pentagon(Point start,Point end){
		setShapeStart(start);
		setShapeEnd(end);
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public boolean contain(int x, int y) {
		int xMin = getShapeStart().x;
		int xMax = getShapeStart().x;
		int yMin = getShapeStart().y;
		int yMax = getShapeStart().y;

		for (int i = 0; i < sides; i++) {
			point = new Point((int) (getShapeStart().x + radius * Math.cos(i * 2 * Math.PI / sides)),
					(int) (getShapeStart().y + radius * Math.sin(i * 2 * Math.PI / sides)));
			points.add(point);
		}
		for(int i = 0; i < points.size(); i++){
			if (points.get(i).x < xMin){
				xMin = points.get(i).x;
			}
			if (points.get(i).x > xMax){
				xMax = points.get(i).x;
			}
			if (points.get(i).y < yMin){
				yMin = points.get(i).y;
			}
			if (points.get(i).y > yMax){
				yMax = points.get(i).y;
			}
		}
		if (x >= xMin && x <= xMax && y >= yMin
				&& y <= yMax) {
			points.clear();
			return true;
		}
		points.clear();
		return false;
	}

	@Override
	public void fill(Graphics g) {

	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
