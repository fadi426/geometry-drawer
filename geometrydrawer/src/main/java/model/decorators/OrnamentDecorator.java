package model.decorators;

import model.shapes.Ornament;
import model.shapes.Shape;

import java.awt.*;

public class OrnamentDecorator extends ShapeDecorator {

    public OrnamentDecorator(Shape decoratedShape, String value, String position) {
        super(decoratedShape);
        addOrnament(decoratedShape, value, position);
    }

    public void addOrnament(Shape shape, String value, String position) {
        Ornament ornament = new Ornament(value, position);
        shape.addOrnament(ornament);
    }
}
