package model.shapes;

import model.visitors.Visitable;
import model.visitors.Visitor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Shape implements Visitable, Figure {

	private Point shapeStart,shapeEnd;
	private Point previousShapeStart, previousShapeEnd;
	private List<Ornament> ornaments = new ArrayList<>();
	private Color currentColor = Color.BLACK;
	private boolean filled = false;


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

	public boolean contain(Point point) {
		return false;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public void addOrnament(Ornament ornament){

		updateOrnament(ornament);
		ornaments.add(ornament);
	}

	public void updateOrnament(Ornament ornament){
		int width = abs(getShapeEnd().x - getShapeStart().x);
		int height = abs(getShapeEnd().y - getShapeStart().y);
		int offset = 10;

		switch (ornament.getPosition()){
			case "top":
				ornament.setShapeStart(new Point(getShapeStart().x + width/2, getShapeStart().y + offset));
				break;
			case "bottom":
				ornament.setShapeStart(new Point(getShapeStart().x + width/2, getShapeEnd().y + offset));
				break;
			case "left":
				ornament.setShapeStart(new Point(getShapeStart().x, getShapeStart().y + height/2 + offset));
				break;
			case "right":
				ornament.setShapeStart(new Point(getShapeEnd().x, getShapeStart().y + height/2 + offset));
				break;
		}
	}

	public List<Ornament> getOrnaments(){
		return ornaments;
	}
}
