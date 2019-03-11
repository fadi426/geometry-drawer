package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape implements java.io.Serializable {

	private Point shapeStart,shapeEnd;
	private Color currentColor = Color.BLACK;
	private boolean filled = false;


	public void drawPoint(Graphics g,int x,int y){
		g.drawLine(x,y,x,y);
	}

	public abstract void draw(Graphics g);

	public abstract void fill(Graphics g);

	public Point getShapeStart() {
		return shapeStart;
	}

	public void setShapeStart(Point shapeStart) {
		this.shapeStart = shapeStart;
	}

	public Point getShapeEnd() {
		return shapeEnd;
	}

	public void setShapeEnd(Point shapeEnd) {
		this.shapeEnd = shapeEnd;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	protected Color getCurrentColor() {
		return currentColor;
	}

	public boolean contain(int x, int y) {
		return false;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

}
