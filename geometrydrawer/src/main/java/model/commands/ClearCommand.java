package model.commands;

import controller.CanvasController;
import model.shapes.Figure;
import model.singleObjects.SingletonCanvas;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements Command {

    private CanvasController canvas;
    private List<Figure> figures = new ArrayList<>();

    public ClearCommand(List<Figure> figures) {
        this.figures.addAll(figures);
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        canvas.clear();
    }

    @Override
    public void Undo() {
        canvas.addElementsToList(figures);
    }

    @Override
    public void Redo() {
        canvas.clear();
    }
}
