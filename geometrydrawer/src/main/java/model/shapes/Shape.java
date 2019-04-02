package model.shapes;

import model.visitors.Visitable;
import model.visitors.Visitor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape implements Visitable {

	private Point shapeStart,shapeEnd;
	private Point previousShapeStart, previousShapeEnd;
	private Color currentColor = Color.BLACK;
	private boolean filled = false;
	
	public void drawPoint(Graphics g,int x,int y){
		g.setColor(currentColor);
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

	public Point getPreviousShapeEnd() { return previousShapeEnd; }

	public Point getPreviousShapeStart() { return previousShapeStart; }

	public void setPreviousShapeEnd(Point previousShapeEnd) { this.previousShapeEnd = previousShapeEnd; }

	public void setPreviousShapeStart(Point previousShapeStart) { this.previousShapeStart = previousShapeStart; }

	public void setColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public Color getColor() {
		return currentColor;
	}

	public boolean contain(int x, int y) {
		return false;
	}

	public boolean contain(int x, int y, List<Shape> shapes) {
		return false;
	}

	public List<Shape> getSubShapes(){
		return new ArrayList();
	};

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
