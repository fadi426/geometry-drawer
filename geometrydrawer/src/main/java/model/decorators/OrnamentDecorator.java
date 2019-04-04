package model.decorators;

import model.shapes.Figure;
import model.shapes.Ornament;
import model.shapes.Shape;

import java.awt.*;

public class OrnamentDecorator extends ShapeDecorator {

    private Ornament ornament;

    public OrnamentDecorator(Figure decoratedShape, String value, String position) {
        super(decoratedShape);
        addOrnament(decoratedShape, value, position);
    }

    public void addOrnament(Figure figure, String value, String position) {
        ornament = new Ornament(value, position, figure);
        figure.addOrnament(ornament);
    }

    public void deleteOrnament(){
        decoratedShape.deleteOrnament(ornament);
    }
}
