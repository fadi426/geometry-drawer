package model.decorators;

import model.shapes.Figure;
import model.shapes.Shape;

import java.awt.*;

public class ShapeDecorator {
    protected Figure decoratedShape;

    public ShapeDecorator(Figure decoratedShape){
        this.decoratedShape = decoratedShape;
    }
}
