package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.shapes.Ornament;
import model.singleObjects.SingletonCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements Command {

    private Figure figure;
    private List<Figure> ornaments;
    private CanvasController canvas;

    public SelectCommand(Figure figure) {
        this.figure = figure;
        this.canvas = SingletonCanvas.getInstance();
        ornaments = new ArrayList<>();
    }

    @Override
    public void Execute() {
        figure.setColor(Color.RED);
        canvas.addToSelected(figure);
    }

    @Override
    public void Undo() {
        figure.setColor(Color.BLACK);
        canvas.removeFromSelected(figure);
    }

    @Override
    public void Redo() {
        figure.setColor(Color.RED);
        canvas.addToSelected(figure);
    }
}
