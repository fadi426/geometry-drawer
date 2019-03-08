package model;
import java.awt.*;
import java.util.ArrayList;

public class Pentagon extends Shape {

	private java.awt.Polygon p = new java.awt.Polygon();
	private int sides = 5;
	private int radius;
	private ArrayList<Point> points;
	private Point hoi;
	private Line line ;

	public Pentagon() {
		points = new ArrayList<Point>(4);
		setShapeStart(new Point(0,0));
		setShapeEnd(new Point(0,0));
	}

	public Pentagon(Point start,Point end){
		setShapeStart(start);
		setShapeEnd(end);
		radius = Math.round((float)(Math.hypot(getShapeStart().x - getShapeEnd().x,
				getShapeStart().y - getShapeEnd().y)));
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
				hoi = new Point((int) (getShapeStart().x + radius * Math.cos(i * 2 * Math.PI / sides)),
						 (int) (getShapeStart().y + radius * Math.sin(i * 2 * Math.PI / sides)));
				points.add(hoi);
				if (i >= 1) {
					line = new Line(points.get(i - 1), points.get(i));
					line.setCurrentColor(getCurrentColor());
					line.draw(g);
				}
				if (i == sides - 1) {
					line = new Line(points.get(sides - 1), points.get(0));
					line.setCurrentColor(getCurrentColor());
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
	public String toString(){
		return "Pentagon: ("+ getShapeStart().x+","+getShapeStart().y+")"
				+ " Radius: " + radius;
	}

	@Override
	public void fill(Graphics g) {

	}

}
