package model.shapes;

import model.visitors.Visitor;

import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Shape {

	private int radius;
	
	public Circle(){
		setShapeStart(new Point(0,0));
		setShapeEnd(new Point(0,0));
		radius=0;
	}
	public Circle(Point start,Point end){
		setShapeStart(start);
		setShapeEnd(end);
		radius = Math.round((float)(Math.hypot(getShapeStart().x - getShapeEnd().x,
				getShapeStart().y - getShapeEnd().y)));
	}

	@Override
	public void draw(Graphics g) {
		  int x0 = getShapeStart().x;
		  int y0 = getShapeStart().y;
		  radius = Math.round((float)(Math.hypot(getShapeStart().x - getShapeEnd().x,
					getShapeStart().y - getShapeEnd().y)));
		  
		  int x = radius;
		  int y = 0;
		  int radiusError = 1 - x;
		 
		  while(x >= y){
		    drawPoint(g,x + x0,y + y0);
		    drawPoint(g,y + x0,x + y0);
		    drawPoint(g,-x + x0,y + y0);
		    drawPoint(g,-y + x0,x + y0);
		    drawPoint(g,-x + x0,-y + y0);
		    drawPoint(g,-y + x0,-x + y0);
		    drawPoint(g,x + x0,-y + y0);
		    drawPoint(g,y + x0,-x + y0);
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
		if (x >= getShapeStart().x - radius && x <= (getShapeStart().x + radius) && y >= getShapeStart().y - radius
				&& y <= (getShapeStart().y + radius)) {
			return true;
		}
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
