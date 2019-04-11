package model.visitors;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

import static java.lang.Math.abs;

public class MoveVisitor implements Visitor {
    CanvasController canvas = SingletonCanvas.getInstance();
    int xDifference;
    int yDifference;

    @Override
    public void visit(Shape shape) {

        xDifference = canvas.endX - canvas.currentX;
        yDifference = canvas.endY - canvas.currentY;

        moveShape(shape);
    }


    /**
     * Moves the given shape to a different spot by changing the position of the
     * start and end point relative to the difference of the mouse position
     * @param shape The shape to move
     */
    public void moveShape(Shape shape) {
        int width;
        int height;
        Point shapeStart = null;


        for (int i = 0; i < canvas.flatEditableShapes.size(); i++) {
            if (canvas.flatEditableShapes.get(i) == shape) {
                shapeStart = canvas.flatPointsEditableShapes.get(i).get(0);
            }
        }

        width = abs(shape.getEndPoint().x - shape.getStartPoint().x);
        height = abs(shape.getEndPoint().y - shape.getStartPoint().y);

        if (shapeStart != null) {
            shape.setStartPoint(new Point(shapeStart.x + xDifference, shapeStart.y + yDifference));
            shape.setEndPoint(new Point(shape.getStartPoint().x + width, shape.getStartPoint().y + height));
        }
    }
}
