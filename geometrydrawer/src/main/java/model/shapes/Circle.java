package model.shapes;

import model.visitors.Visitor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Circle extends Shape {

	Ellipse2D ellipse;

	public Circle(){
		setShapeStart(new Point(0,0));
		setShapeEnd(new Point(0,0));
	}

	@Override
	public void draw(Graphics g) {
		int width = abs(getShapeEnd().x - getShapeStart().x);
		int height = abs(getShapeEnd().y - getShapeStart().y);
		Graphics2D g2D = (Graphics2D) g;
		ellipse = new Ellipse2D.Double(getShapeStart().x,getShapeStart().y, width,height);
		g.setColor(getColor());
		g2D.draw(ellipse);

		if (getOrnaments().size() > 0) {
			for (Ornament ornament : getOrnaments()) {
				ornament.draw(g);
			}
		}
	}

	@Override
	public boolean contain(int x, int y) {
		return ellipse.contains(x,y);
	}

	@Override
	public void fill(Graphics g) {

	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
