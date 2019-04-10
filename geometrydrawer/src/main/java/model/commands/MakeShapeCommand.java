package model.commands;

import controller.CanvasController;
import model.shapes.Shape;
import model.singleObjects.SingletonCanvas;

public class MakeShapeCommand implements Command {
    private Shape shape;
    private CanvasController canvas;

    public MakeShapeCommand(Shape shape) {
        this.shape = shape;
        this.canvas = SingletonCanvas.getInstance();
    }

    public void Execute() {
        canvas.setCurrShape(shape);
    }

    public void Undo() {
        canvas.removeLastElement();
    }

    public void Redo() {
        canvas.addElementToList(shape);
    }
}
