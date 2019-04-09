package model.decorators;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;
import model.visitors.Visitor;

import java.awt.*;

public class OrnamentDecorator extends FigureDecorator {

    private Ornament ornament;
    private CanvasController canvas;
    private String text;
    private String position;

    public OrnamentDecorator(Figure decoratedShape, String text, String position) {
        super(decoratedShape);
        this.canvas = SingletonCanvas.getInstance();
        this.text = text;
        this.position = position;
        addOrnament();
    }

    @Override
    public void draw(Graphics g){
        decoratedFigure.draw(g);
    }

    @Override
    public void fill(Graphics g) {

    }

    @Override
    public void setColor(Color currentColor) {

    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean contain(Point point) {
        return false;
    }

    @Override
    public void accept(Visitor v) {

    }

    public void addOrnament() {
        ornament = new Ornament(text, position, decoratedFigure);
        this.ornament = ornament;
        canvas.addElementToList(ornament);
    }

    public Figure getOrnament(){
        return ornament;
    }

}
