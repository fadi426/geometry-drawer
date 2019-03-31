package model.commands;

import controller.CanvasController;
import model.singleObjects.SingletonCanvas;
import model.shapes.Shape;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements Command {

    private CanvasController canvas;
    private List<Shape> shapes = new ArrayList<>();

    public ClearCommand(List<Shape> shapes){
        this.shapes.addAll(shapes);
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        canvas.clear();
    }

    @Override
    public void Undo() {
        canvas.addElementsToList(shapes);
    }

    @Override
    public void Redo() {
        canvas.clear();
    }
}
