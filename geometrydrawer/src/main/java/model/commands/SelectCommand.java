package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Group;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

public class SelectCommand implements Command {

    private Figure figure;
    private CanvasController canvas;

    public SelectCommand(Figure figure){
        this.figure = figure;
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        Point currentPoint = new Point(canvas.currentX, canvas.currentY);

        if (figure instanceof Group && ((Group) figure).contain(currentPoint, figure))
            figure.setColor(Color.RED);
        else if (figure instanceof Shape && figure.contain(currentPoint) )
            figure.setColor(Color.RED);
        else {
            canvas.unSelectedCounter++;
            return;
        }
        canvas.selectedShapes.add(figure);
    }

    @Override
    public void Undo() {
        figure.setColor(Color.BLACK);
        canvas.toDelete.add(figure);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.RED);
        canvas.selectedShapes.add(figure);
    }
}
