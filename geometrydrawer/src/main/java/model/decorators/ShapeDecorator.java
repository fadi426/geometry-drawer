package model.decorators;

import model.shapes.Shape;

import java.awt.*;

public class ShapeDecorator {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape){
        this.decoratedShape = decoratedShape;
    }
}
