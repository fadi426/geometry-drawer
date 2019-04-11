package model.strategies;

import model.shapes.Shape;

import java.awt.*;

public class ShapeContext {

    private ShapeStrategy shapeStrategy;

    public ShapeContext(ShapeStrategy shapeStrategy) {
        this.shapeStrategy = shapeStrategy;
    }

    /**
     * Execute the draw function of the strategy that the context has.
     * @param shape The shape to draw
     * @param g Graphics
     */
    public void executeDrawStrategy(Shape shape, Graphics g) {
        shapeStrategy.draw(shape, g);
    }


    /**
     * Executes the ToString function of a shape strategy.
     * @param shape The shape to stringify
     * @return Returns a String with data from the shape.
     */
    public String executeToString(Shape shape) {
        return shapeStrategy.toString(shape);
    }

    /**
     * Executees the contain function of the strategy that the context has.
     * @param point The point to check
     * @return Returns True or False whether the point is in the shape or not.
     */
    public Boolean executeContain(Point point) {
        return shapeStrategy.contain(point);
    }
}
