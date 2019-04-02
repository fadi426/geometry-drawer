package model.visitors;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;

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

    private void resizeShape(Shape shape) {
        if (shape.getSubShapes().size() > 0){
            for (Shape s: shape.getSubShapes()) {
                resizeShape(s);
            }
        }
        else {
            if (shape.getPreviousShapeEnd() != null)
                shape.setShapeEnd(new Point(shape.getPreviousShapeEnd().x + xDifference,shape.getPreviousShapeEnd().y + yDifference));
        }
    }

}
