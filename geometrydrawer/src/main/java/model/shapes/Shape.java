package model.shapes;

import model.strategies.ShapeContext;
import model.strategies.ShapeStrategy;
import model.visitors.Visitable;
import model.visitors.Visitor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape implements Visitable, Figure {

	private Point startPoint, endPoint;
	private Color currentColor = Color.BLACK;
	private transient ShapeContext shapeContext;
	private boolean filled = false;


	public abstract void draw(Graphics g);

	public abstract void fill(Graphics g);

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public void setColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public Color getColor() {
		return currentColor;
	}

	public boolean contain(Point point) {
		return false;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

    public void setStrategy(ShapeStrategy strategy){
        this.shapeContext = new ShapeContext(strategy);
    }

    public ShapeContext getStrategy(){
	    return this.shapeContext;
    }
}
