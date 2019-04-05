package model.shapes;

import model.strategies.CircleStrategy;
import model.strategies.ShapeContext;
import model.visitors.Visitor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Circle extends Shape {

	ShapeContext shapeContext = new ShapeContext(new CircleStrategy());

	public Circle(){
		setShapeStart(new Point(0,0));
		setShapeEnd(new Point(0,0));
	}

	@Override
	public void draw(Graphics g) {
		shapeContext.executeDrawStrategy(this, g);
	}

	@Override
	public boolean contain(Point point) {
		return shapeContext.executeContain(point);
	}

	@Override
	public void fill(Graphics g) {

	}
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
