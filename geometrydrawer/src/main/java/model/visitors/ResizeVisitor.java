package model.visitors;

import controller.CanvasController;
import model.Mouse;
import model.shapes.Figure;
import model.shapes.Shape;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

public class ResizeVisitor implements Visitor {
    private CanvasController canvas = SingletonCanvas.getInstance();
    private Mouse mouse = SingleMouse.getInstance();
    private int xDifference;
    private int yDifference;

    @Override
    public void visit(Figure figure) {

        xDifference = mouse.getEndX() - mouse.getCurrentX();
        yDifference = mouse.getEndY() - mouse.getCurrentY();

        resizeShape((Shape) figure);
    }

    /**
     * Resize the shape by resetting the end point of the given shape
     * @param shape The shape thats needs to be resized.
     */
    private void resizeShape(Shape shape) {
        Point shapeEnd = null;

        for (int i = 0; i < canvas.getFlatEditableShapes().size(); i++) {
            if (canvas.getFlatEditableShapes().get(i) == shape) {
                shapeEnd = canvas.getFlatPointsEditableShapes().get(i).get(1);
            }
        }

        if (shapeEnd != null) {
            shape.setEndPoint(new Point(shapeEnd.x + xDifference, shapeEnd.y + yDifference));
        }
    }
}
