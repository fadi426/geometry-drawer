package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.singleObjects.SingletonCanvas;

import java.awt.*;

public class UnselectCommand implements Command {

    private Figure figure;
    private CanvasController canvas;

    public UnselectCommand(Figure figure){
        this.figure = figure;
        canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        figure.setColor(Color.BLACK);
        canvas.selectedShapes.remove(figure);
    }

    @Override
    public void Undo() {
        figure.setColor(Color.RED);
        canvas.selectedShapes.add(figure);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.BLACK);
        canvas.selectedShapes.remove(figure);
    }
}
