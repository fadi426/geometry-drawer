package model;

import controller.CanvasController;

public class MakeShapeCommand implements Command {
    private Shape shape;
    private CanvasController canvas;

    public MakeShapeCommand(Shape shape, CanvasController canvas)
    {
        this.shape = shape;
        this.canvas = canvas;
        System.out.println(canvas.listmodel.size());
    }

    public void Execute()
    {
        canvas.setCurrShape(shape);
    }

    public void Undo()
    {
        System.out.println("undo");
        canvas.removeLastElement();
    }

    public void Redo(){
        canvas.addElementToList(shape);
    }
}
