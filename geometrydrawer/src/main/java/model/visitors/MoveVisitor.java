package model.visitors;

import controller.CanvasController;
import model.Mouse;
import model.shapes.Figure;
import model.shapes.Shape;
import model.singleObjects.SingleMouse;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

import static java.lang.Math.abs;

public class MoveVisitor implements Visitor {
    private CanvasController canvas = SingletonCanvas.getInstance();
    private Mouse mouse = SingleMouse.getInstance();
    private int xDifference;
    private int yDifference;

    @Override
    public void visit(Figure figure) {

        xDifference = mouse.getEndX() - mouse.getCurrentX();
        yDifference = mouse.getEndY() - mouse.getCurrentY();

        moveShape((Shape) figure);
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


        for (int i = 0; i < canvas.getFlatEditableShapes().size(); i++) {
            if (canvas.getFlatEditableShapes().get(i) == shape) {
                shapeStart = canvas.getFlatPointsEditableShapes().get(i).get(0);
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
