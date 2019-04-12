package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UnselectCommand implements Command {

    private Figure figure;
    private CanvasController canvas;
    private List<Figure> ornaments;

    public UnselectCommand(Figure figure) {
        this.figure = figure;
        canvas = SingletonCanvas.getInstance();
        ornaments = new ArrayList<>();
    }

    @Override
    public void Execute() {
        figure.setColor(Color.BLACK);
        canvas.removeFromSelected(figure);
    }

    @Override
    public void Undo() {
        figure.setColor(Color.RED);
        canvas.addToSelected(figure);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.BLACK);
        canvas.addToSelected(figure);
    }
}
