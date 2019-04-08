package model.shapes;

import model.strategies.RectangleStrategy;
import model.strategies.ShapeContext;
import model.strategies.ShapeStrategy;
import model.visitors.Visitor;

import java.awt.*;

import static java.lang.Math.abs;

public class Rectangle extends Shape {

	public Rectangle(){
		setStartPoint(new Point(0,0));
		setEndPoint(new Point(0,0));
		setStrategy(new RectangleStrategy());
	}
	@Override
	public void draw(Graphics g) {
		getStrategy().executeDrawStrategy(this, g);
	}

	@Override
	public void fill(Graphics g) {

	}

	@Override
	public boolean contain(Point point) {
		return getStrategy().executeContain(point);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
