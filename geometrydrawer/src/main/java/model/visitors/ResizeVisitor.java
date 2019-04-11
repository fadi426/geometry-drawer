package model.visitors;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

public class ResizeVisitor implements Visitor {
    CanvasController canvas = SingletonCanvas.getInstance();
    int xDifference;
    int yDifference;

    @Override
    public void visit(Shape shape) {

        xDifference = canvas.endX - canvas.currentX;
        yDifference = canvas.endY - canvas.currentY;

        resizeShape(shape);
    }

    /**
     * Resize the shape by resetting the end point of the given shape
     * @param shape The shape thats needs to be resized.
     */
    private void resizeShape(Shape shape) {
        Point shapeEnd = null;

        for (int i = 0; i < canvas.flatEditableShapes.size(); i++) {
            if (canvas.flatEditableShapes.get(i) == shape) {
                shapeEnd = canvas.flatPointsEditableShapes.get(i).get(1);
            }
        }

        if (shapeEnd != null) {
            shape.setEndPoint(new Point(shapeEnd.x + xDifference, shapeEnd.y + yDifference));
        }
    }
}
