package model.strategies;

import model.shapes.Shape;

import java.awt.*;

public class ShapeContext {

    private ShapeStrategy shapeStrategy;

    public ShapeContext(ShapeStrategy shapeStrategy){
        this.shapeStrategy = shapeStrategy;
    }

    public void executeDrawStrategy(Shape shape, Graphics g){
        shapeStrategy.draw(shape, g);
    }

    public String executeToString(Shape shape){
        return shapeStrategy.toString(shape);
    }

    public Boolean executeContain(Point point){ return shapeStrategy.contain(point); }

    public ShapeStrategy getStrategy() {return shapeStrategy;}

//    public void setColor(Shape shape, Color color) { shapeStrategy.setColor(shape, color); }
}
