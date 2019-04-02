package model.visitors;

import controller.CanvasController;
import model.commands.CommandManager;
import model.commands.MoveCommand;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;
import model.singleObjects.SingletonCmdMng;

import java.awt.*;
import java.util.List;

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



    public void moveShape(Shape shape) {
        if (shape.getSubShapes().size() > 0){
            for (Shape s: shape.getSubShapes()) {
                moveShape(s);
            }
        }
        else {
            int width;
            int height;

            width = abs(shape.getShapeEnd().x - shape.getShapeStart().x);
            height = abs(shape.getShapeEnd().y - shape.getShapeStart().y);

            if (shape.getPreviousShapeStart() != null) {
                shape.setShapeStart(new Point(shape.getPreviousShapeStart().x + xDifference, shape.getPreviousShapeStart().y + yDifference));
                shape.setShapeEnd(new Point(shape.getShapeStart().x + width, shape.getShapeStart().y + height));
            }
        }
    }
}
