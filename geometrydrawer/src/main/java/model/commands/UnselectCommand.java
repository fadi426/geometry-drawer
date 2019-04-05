package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.List;

public class UnselectCommand implements Command {

    private Figure figure;
    private CanvasController canvas;

    public UnselectCommand(Figure figure){
        this.figure = figure;
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        for (Figure figure : canvas.selectedShapes){
            if (canvas.unSelectedCounter == canvas.listmodel.size() ) {
                figure.setColor(Color.BLACK);
                canvas.toDelete.addAll(canvas.selectedShapes);
            }
             if (moreThanOnce(canvas.selectedShapes, figure)) {
                canvas.toDelete.remove(figure);
                figure.setColor(Color.BLACK);
            }
        }
    }

    @Override
    public void Undo() {
        figure.setColor(Color.RED);
        canvas.selectedShapes.add(figure);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.BLACK);
        canvas.toDelete.add(figure);
    }

    public static boolean moreThanOnce(List<Figure> list, Figure searched)
    {
        int numCount = 0;
        for (Figure thisFig : list) {
            if (thisFig == searched) numCount++;
        }
        return numCount > 1;
    }
}
