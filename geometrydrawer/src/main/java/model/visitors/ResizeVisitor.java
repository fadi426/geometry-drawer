package model.visitors;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

import static java.lang.Math.abs;

public class ResizeVisitor implements Visitor {
    CanvasController canvas = SingletonCanvas.getInstance();

    @Override
    public void visit(Shape shape) {
        int width;
        int height;
        int xDifference;
        int yDifference;

        width = abs(shape.getShapeEnd().x - shape.getShapeStart().x);
        height = abs(shape.getShapeEnd().y - shape.getShapeStart().y);

        xDifference = canvas.endX - canvas.currentX;
        yDifference = canvas.endY - canvas.currentY;

        shape.setShapeEnd(new Point(shape.getShapeStart().x + width + xDifference , shape.getShapeStart().y + height + yDifference ));
    }
}
