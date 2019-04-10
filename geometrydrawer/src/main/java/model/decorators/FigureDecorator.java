package model.decorators;

import model.shapes.Figure;

import java.awt.*;

public abstract class FigureDecorator implements Figure {
    protected Figure decoratedFigure;

    public FigureDecorator(Figure decoratedFigure) {
        this.decoratedFigure = decoratedFigure;
    }

    public void draw(Graphics g) {
        decoratedFigure.draw(g);
    }
}
