package model.visitors;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Ornament;
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
    public void visit(Figure figure) {

        xDifference = canvas.endX - canvas.currentX;
        yDifference = canvas.endY - canvas.currentY;

        resizeShape(figure);
    }

    private void resizeShape(Figure figure) {
        if (figure.getPreviousShapeEnd() != null) {
            figure.setShapeEnd(new Point(figure.getPreviousShapeEnd().x + xDifference, figure.getPreviousShapeEnd().y + yDifference));
//            for (Ornament ornament : figure.getOrnaments()) {
//                figure.updateOrnament(ornament);
//            }
        }
    }

}
