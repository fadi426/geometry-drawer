package model.commands;

import controller.CanvasController;
import model.shapes.Circle;
import model.singleObjects.SingletonCanvas;
import model.shapes.Group;
import model.shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MakeGroupCommand implements Command{

    private List<model.shapes.Shape> shapes = new ArrayList<>();
    private CanvasController canvas;
    private Group group;

    public MakeGroupCommand(List<model.shapes.Shape> shapes){
        this.shapes.addAll(shapes);
        this.canvas = SingletonCanvas.getInstance();
    }

    @Override
    public void Execute() {
        group = new Group();
        group.addShapes(shapes);
        group.setColor(Color.BLACK);
        CalculateBoundary(group);

        RemoveShapes();

        canvas.addElementToList(group);
    }

    @Override
    public void Undo() {
        canvas.removeLastElement();

        for (model.shapes.Shape s : shapes) {
            canvas.addElementToList(s);
        }
    }

    @Override
    public void Redo() {
        RemoveShapes();

        canvas.addElementToList(group);

    }

    private void RemoveShapes(){
        List<Shape> mainGroupShapes = canvas.getMainGroup().getSubShapes();
        List<Shape> listmodel = canvas.toList();

        for (Shape s : shapes) {
            if(listmodel.contains(s) && mainGroupShapes.contains(s)) {
                canvas.listmodel.removeElement(s);
                canvas.mainGroup.removeShape(s);
            }
        }
    }

    private void CalculateBoundary(Group group){
        Point start = new Point();
        Point end = new Point();

        start.x = group.getSubShapes().get(0).getShapeStart().x;
        start.y = group.getSubShapes().get(0).getShapeStart().y;

        for (Shape shape : group.getSubShapes()) {
                if (shape.getShapeEnd().x > end.x)
                    end.x = shape.getShapeEnd().x;

                if (shape.getShapeEnd().y > end.y)
                    end.y = shape.getShapeEnd().y;

                if (shape.getShapeStart().x < start.x)
                    start.x = shape.getShapeStart().x;

                if (shape.getShapeStart().y < start.y)
                    start.y = shape.getShapeStart().y;
        }

        group.setShapeStart(start);
        group.setShapeEnd(end);
    }
}
