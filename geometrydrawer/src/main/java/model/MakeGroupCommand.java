package model;

import controller.CanvasController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MakeGroupCommand implements Command{

    private List<Shape> shapes = new ArrayList<>();
    private CanvasController canvas;
    private Group group;

    public MakeGroupCommand(List<Shape> shapes){
        this.shapes.addAll(shapes);
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        group = new Group();
        group.addShapes(shapes);
        group.setColor(Color.BLACK);

        RemoveShapes();

        canvas.addElementToList(group);
    }

    @Override
    public void Undo() {
        canvas.removeLastElement();

        for (Shape s : shapes) {
            canvas.addElementToList(s);
        }
    }

    @Override
    public void Redo() {
        RemoveShapes();

        canvas.addElementToList(group);

    }

    private void RemoveShapes(){
        for (Shape s : shapes) {
            if(canvas.listmodel.contains(s)) {
                canvas.listmodel.removeElement(s);
            }
        }
    }
}
