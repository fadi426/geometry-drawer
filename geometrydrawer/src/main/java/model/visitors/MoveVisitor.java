package model.visitors;

import controller.CanvasController;
import model.commands.CommandManager;
import model.commands.MoveCommand;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
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
    public void visit(Figure figure) {

        xDifference = canvas.endX - canvas.currentX;
        yDifference = canvas.endY - canvas.currentY;

        moveShape(figure);
    }



    public void moveShape(Figure figure) {
        int width;
        int height;

        width = abs(figure.getShapeEnd().x - figure.getShapeStart().x);
        height = abs(figure.getShapeEnd().y - figure.getShapeStart().y);

        if (figure.getPreviousShapeStart() != null) {
            figure.setShapeStart(new Point(figure.getPreviousShapeStart().x + xDifference, figure.getPreviousShapeStart().y + yDifference));
            figure.setShapeEnd(new Point(figure.getShapeStart().x + width, figure.getShapeStart().y + height));
        }

    }
}
