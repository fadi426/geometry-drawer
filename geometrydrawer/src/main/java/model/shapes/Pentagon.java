package model.shapes;
import model.visitors.Visitor;

import java.awt.*;
import java.util.ArrayList;

public class Pentagon extends Shape {

	private java.awt.Polygon p = new java.awt.Polygon();
	private int sides = 5;
	private int radius;
	private ArrayList<Point> points;
	private Point point;
	private Line line ;

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
		points.clear();
		radius = Math.round((float)(Math.hypot(getShapeStart().x - getShapeEnd().x,
				getShapeStart().y - getShapeEnd().y)));

		int x = radius;
		int y = 0;
		int radiusError = 1 - x;
		while(x >= y){
			for (int i = 0; i < sides; i++) {
				point = new Point((int) (getShapeStart().x + radius * Math.cos(i * 2 * Math.PI / sides)),
						 (int) (getShapeStart().y + radius * Math.sin(i * 2 * Math.PI / sides)));
				points.add(point);
				if (i >= 1) {
					line = new Line(points.get(i - 1), points.get(i));
					line.setColor(getColor());
					line.draw(g);
				}
				if (i == sides - 1) {
					line = new Line(points.get(sides - 1), points.get(0));
					line.setColor(getColor());
					line.draw(g);
				}
			}

			points.clear();
			y++;
			if (radiusError<0){
				radiusError += 2 * y + 1;
			}
			else{
				x--;
				radiusError += 2 * (y - x) + 1;
			}
		}
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
